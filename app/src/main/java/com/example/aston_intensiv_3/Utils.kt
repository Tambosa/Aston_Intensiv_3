package com.example.aston_intensiv_3

import androidx.recyclerview.widget.DiffUtil

object Utils {
    fun <T : Any> getStandardDiff(oldList: List<T>, newList: List<T>) =
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize() = oldList.size

            override fun getNewListSize() = newList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                oldList[oldItemPosition]::class == newList[newItemPosition]::class
                        && oldList[oldItemPosition] == newList[newItemPosition]


            override fun areContentsTheSame(
                oldItemPosition: Int, newItemPosition: Int
            ) = oldList[oldItemPosition] == newList[newItemPosition]
        })
}
