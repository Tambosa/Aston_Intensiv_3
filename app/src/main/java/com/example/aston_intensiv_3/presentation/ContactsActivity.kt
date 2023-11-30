package com.example.aston_intensiv_3.presentation

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aston_intensiv_3.R
import com.example.aston_intensiv_3.Utils
import com.example.aston_intensiv_3.databinding.ActivityContactsBinding
import com.example.aston_intensiv_3.databinding.DialogContactBinding
import com.example.aston_intensiv_3.domain.AddContactItemSingleton
import com.example.aston_intensiv_3.domain.ContactEntity
import com.example.aston_intensiv_3.domain.ContactsRecyclerItem
import com.example.aston_intensiv_3.presentation.adapters.ContactsCompositeAdapter
import com.example.aston_intensiv_3.showLongToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactsBinding
    private lateinit var dialogBinding: DialogContactBinding
    private lateinit var viewModel: ContactsViewModel
    private val contactAdapter = ContactsCompositeAdapter(
        onContactItemClick = { item -> updateContact(item) },
        onContactDeleteClick = { item -> deleteContact(item) },
        onContactItemLongClick = { item -> onContactItemLongClick(item) },
        onAddContactClick = { addContact() }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ContactsViewModel::class.java]
        initViewModel()
        initRecycler()
    }

    private fun initViewModel() {
        viewModel.state.observe(this) { state ->
            val data = mutableListOf<ContactsRecyclerItem>().apply {
                addAll(state)
                add(AddContactItemSingleton)
            }
            renderRecyclerData(data.toList())
            renderFabs(state.map { it as ContactEntity })
        }
        viewModel.getState()
    }

    private fun renderRecyclerData(newList: List<ContactsRecyclerItem>) {
        val oldList = contactAdapter.items ?: listOf()
        val diff = Utils.getStandardDiff(oldList, newList)
        contactAdapter.items = newList
        diff.dispatchUpdatesTo(contactAdapter)
    }

    private fun renderFabs(state: List<ContactEntity>) {
        var isSelection = false
        state.forEach {
            if (it.isSelected) {
                isSelection = true
            }
        }
        binding.btnDeleteAll.visibility = if (isSelection) View.VISIBLE else View.GONE
        binding.btnUndoSelection.visibility = if (isSelection) View.VISIBLE else View.GONE
        binding.btnDeleteAll.setOnClickListener { viewModel.deleteAllSelected() }
        binding.btnUndoSelection.setOnClickListener { viewModel.undoAllSelected() }
    }


    private fun initRecycler() {
        binding.contactRecyclerView.apply {
            adapter = contactAdapter
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false
            )
        }
    }

    private fun deleteContact(contact: ContactEntity) {
        viewModel.deleteContact(contact)
    }

    private fun updateContact(contact: ContactEntity) {
        showContactDialog(contact) { c -> viewModel.updateContact(c) }
    }

    private fun addContact() {
        showContactDialog(ContactEntity(-1, "", "")) { c -> viewModel.addContact(c) }
    }

    private fun showContactDialog(contact: ContactEntity, onSave: (ContactEntity) -> Unit) {
        dialogBinding = DialogContactBinding.inflate(layoutInflater)
        val dialog = Dialog(this, R.style.Theme_Dialog)
        configureDialog(dialog, dialogBinding)
        initDialog(dialog, contact, onSave)
        dialog.show()
    }

    private fun configureDialog(dialog: Dialog, dialogBinding: DialogContactBinding) {
        dialog.apply {
            window?.requestFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            setCancelable(false)
            setContentView(dialogBinding.root)
        }
    }

    private fun initDialog(dialog: Dialog, contact: ContactEntity, onSave: (ContactEntity) -> Unit) {
        initDialogViews(contact)
        initDialogButtons(dialog, contact, onSave)
    }

    private fun initDialogViews(contact: ContactEntity) {
        dialogBinding.contactEditName.setText(contact.name)
        dialogBinding.contactEditSurname.setText(contact.surname)
        dialogBinding.contactEditNumber.setText(contact.phoneNumber.toString())
    }

    private fun initDialogButtons(
        dialog: Dialog,
        contact: ContactEntity,
        onSave: (ContactEntity) -> Unit
    ) {
        dialogBinding.contactBtnConfirm.setOnClickListener {
            try {
                onSave(
                    ContactEntity(
                        contact.id,
                        dialogBinding.contactEditName.text.toString(),
                        dialogBinding.contactEditSurname.text.toString(),
                        dialogBinding.contactEditNumber.text.toString().toInt(),
                        contact.isSelected
                    )
                )
                dialog.dismiss()
            } catch (e: Exception) {
                showLongToast("Phone number must ONLY contain digits")
            }
        }
        dialogBinding.contactBtnCancel.setOnClickListener { dialog.dismiss() }
    }

    private fun onContactItemLongClick(item: ContactEntity): Boolean {
        viewModel.setContactSelected(item)
        return !item.isSelected
    }
}