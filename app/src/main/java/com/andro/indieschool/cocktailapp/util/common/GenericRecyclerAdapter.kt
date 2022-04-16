package com.andro.indieschool.cocktailapp.util.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class GenericRecyclerAdapter<T : Any>(
    @LayoutRes val holderResId: Int,
    private val onBind: (T, View) -> Unit,
    private val listener: (T, pos: Int, View) -> Unit = { _, _, _ -> kotlin.run { } }
) : RecyclerView.Adapter<GenericRecyclerAdapter.GenericViewHolder<T>>() {

    private val listData = mutableListOf<T>()
    private val diffCallback by lazy { DiffCallback() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<T> {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(holderResId, parent, false)
        return GenericViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GenericViewHolder<T>, position: Int) {
        holder.bindView(
            item = listData[holder.adapterPosition],
            onBind = onBind,
            listener = listener
        )
    }

    override fun getItemCount(): Int = listData.size

    fun setData(data: List<T>) {
        calculateDiff(data)
    }

    fun addData(newData: List<T>) {
        val list = ArrayList(this.listData)
        list.addAll(newData)
        calculateDiff(list)
    }

    fun clearData() { calculateDiff(emptyList()) }

    private fun calculateDiff(newData: List<T>) {
        diffCallback.setList(listData, newData)
        val result = DiffUtil.calculateDiff(diffCallback)
        with(listData) {
            clear()
            addAll(newData)
        }
        result.dispatchUpdatesTo(this)
    }

    class GenericViewHolder<T : Any>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(
            item: T,
            onBind: (T, View) -> Unit,
            listener: (T, pos: Int, View) -> Unit
        ) {
            with(itemView) {
                onBind.invoke(item, this)
                setOnClickListener { listener.invoke(item, adapterPosition, this) }
            }
        }
    }

}