package com.example.stockholmcolorexchange.ui.colorPickerDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.network.ext.collectFlow
import com.example.stockholmcolorexchange.R
import com.example.stockholmcolorexchange.databinding.FragmentColorDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

const val MAX_RISK = 5

@AndroidEntryPoint
class ColorPickerDetailsFragment : Fragment(R.layout.fragment_color_details) {

    private lateinit var binding: FragmentColorDetailsBinding
    private val viewModel: ColorPickerDetailsViewModel by viewModels()

    private val args: ColorPickerDetailsFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentColorDetailsBinding.bind(view)

        viewModel.insertColorPickerData(args.colorPickerData)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        collectFlow(viewModel.state) {
            it.handleState()
        }
    }

    private fun ColorPickerDetailsState.handleState() {
        colorPickerData?.let {
            binding.name.text = colorPickerData.name
            binding.hex.text = colorPickerData.colorHexName
            binding.progressBar.progress = ((colorPickerData.risk.toFloat() / MAX_RISK.toFloat()) * 100).toInt()
            binding.progressText.text = requireContext().getString(R.string.risk, colorPickerData.risk, MAX_RISK)
            binding.cardDetailsView.setCardBackgroundColor(colorPickerData.color)

        }
    }


}