package com.example.stockholmcolorexchange.ui.colorPickerList

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
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
        val pickerName = cardView.findViewById<TextView>(R.id.picker_name)
        val index = cardView.findViewById<TextView>(R.id.index)
        pickerName.text = data.name
        index.text = data.tradesToday.toString()
        index.text = holder.itemView.context.getString(R.string.index, data.tradesToday)
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

