package com.example.stockholmcolorexchange.ui.colorPickerList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.repository.data.ColorPickerData
import com.example.stockholmcolorexchange.R
import com.example.stockholmcolorexchange.databinding.PickerViewBinding

class ColorPickerListAdapter(private val onClicked: (ColorPickerData) -> Unit) :
    ListAdapter<ColorPickerData, ColorPickerListAdapter.ViewHolder>(Companion) {

    class ViewHolder(val binding: PickerViewBinding) : RecyclerView.ViewHolder(binding.root)

    companion object : DiffUtil.ItemCallback<ColorPickerData>() {
        override fun areItemsTheSame(oldItem: ColorPickerData, newItem: ColorPickerData): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(
            oldItem: ColorPickerData,
            newItem: ColorPickerData
        ): Boolean = oldItem.colorHexName == newItem.colorHexName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PickerViewBinding.inflate(
            layoutInflater,
            parent,
            false
        )

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.binding.pickerName.text = data.name
        holder.binding.index.text = data.tradePrice.toString()
        holder.binding.index.text =
            holder.itemView.context.getString(R.string.index, data.tradePrice)
        holder.binding.root.setCardBackgroundColor(data.color)
        holder.binding.root.setOnClickListener {
            onClicked(data.copy())
        }

    }
}

