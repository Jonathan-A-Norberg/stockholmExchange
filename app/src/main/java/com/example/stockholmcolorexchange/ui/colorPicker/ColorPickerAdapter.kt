package com.example.stockholmcolorexchange.ui.colorPicker

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.repository.data.ColorPickerData
import com.example.stockholmcolorexchange.R


class ColorPickerAdapter(
    private val onClicked: (ColorPickerData) -> Unit,
) : RecyclerView.Adapter<ColorPickerAdapter.ColorPickerClassViewHolder>() {

    var list: List<ColorPickerData> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorPickerClassViewHolder {
        return ColorPickerClassViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.picker_view, parent, false) as CardView)
    }

    override fun onBindViewHolder(holder: ColorPickerClassViewHolder, position: Int) {
        val data = list[position]
        val cardView = (holder.itemView as CardView)
        val pickerText = cardView.findViewById<TextView>(R.id.picker_hex_text)
        val pickerName = cardView.findViewById<TextView>(R.id.picker_name)
        pickerText.text = data.colorName
        pickerName.text = data.name
        cardView.setCardBackgroundColor(data.color)
        cardView.setOnClickListener {
            onClicked(data.copy())
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    fun setItems(list: List<ColorPickerData>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ColorPickerClassViewHolder(itemView: CardView) : RecyclerView.ViewHolder(itemView)



}

