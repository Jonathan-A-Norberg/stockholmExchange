package com.example.stockholmcolorexchange.ui.colorPickerList

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.network.ext.collectFlow
import com.example.network.utils.getErrorMessageRes
import com.example.stockholmcolorexchange.R
import com.example.stockholmcolorexchange.databinding.FragmentColorPickerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ColorPickerFragment : Fragment(R.layout.fragment_color_picker) {

    private lateinit var binding: FragmentColorPickerBinding
    private val viewModel: ColorPickerViewModel by viewModels()

    private lateinit var adapter: ColorPickerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentColorPickerBinding.bind(view)

        adapter = ColorPickerAdapter {
            if (findNavController().currentDestination?.id == R.id.colorListFragment) {

                val directions = ColorPickerFragmentDirections.actionColorListToColorDetails(
                    it
                )
                findNavController().navigate(directions)
            }
        }

        binding.pickerRecyclerView.adapter = adapter

        binding.tryAgain.setOnClickListener {
            viewModel.tryAgainClicked()
        }

        collectFlow(viewModel.state) {
            it.handleState()
        }
    }

    private fun ColorPickerState.handleState() {
        binding.progressBar.isVisible = loading
        if (error != null) {
            binding.errorText.text = requireContext().getString(error.getErrorMessageRes())
            binding.errorText.isVisible = true
            binding.tryAgain.isVisible = true
            binding.pickerRecyclerView.isVisible = false
        } else {
            binding.errorText.isVisible = false
            binding.tryAgain.isVisible = false
            binding.pickerRecyclerView.isVisible = true
        }
        adapter.setItems(colorPickerList)


    }


}