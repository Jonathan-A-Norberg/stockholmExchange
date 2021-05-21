package com.example.stockholmcolorexchange.ui.colorPicker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.example.network.ext.collectFlow
import com.example.stockholmcolorexchange.R
import com.example.stockholmcolorexchange.databinding.FragmentColorPickerBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ColorPickerFragment : Fragment(R.layout.fragment_color_picker) {

    private lateinit var binding: FragmentColorPickerBinding
    private val viewModel: ColorPickerViewModel by viewModels()

    private lateinit var adapter: ColorPickerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentColorPickerBinding.bind(view)

        adapter = ColorPickerAdapter {
            viewModel.onColorPickerClicked(it)
        }

        binding.pickerRecyclerView.adapter = adapter

        collectFlow(viewModel.state){
            it.handleState()
            adapter.setItems(it.colorPickerList)
        }
    }

    private fun ColorPickerState.handleState(){
        Timber.e(this.colorPickerList.toString())

    }



}