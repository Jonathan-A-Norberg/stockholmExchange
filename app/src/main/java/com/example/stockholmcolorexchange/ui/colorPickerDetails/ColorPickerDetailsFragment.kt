package com.example.stockholmcolorexchange.ui.colorPickerDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.network.ext.collectFlow
import com.example.repository.data.ColorPickerData
import com.example.stockholmcolorexchange.R
import com.example.stockholmcolorexchange.databinding.FragmentColorDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

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
            binding.tradeValue.text = getString(R.string.index, colorPickerData.tradePrice)
            animateProgressBar(colorPickerData)
            binding.progressText.text =
                requireContext().getString(R.string.risk, colorPickerData.risk, MAX_RISK)
            binding.tradesToday.text =
                requireContext().getString(R.string.trades_today, colorPickerData.numTrades)
            binding.cardDetailsView.setCardBackgroundColor(colorPickerData.color)

        }
    }

    private fun animateProgressBar(colorPickerData: ColorPickerData) {
        var animationTime = 0f

        lifecycleScope.launch {

            val max = (colorPickerData.risk.toFloat() / MAX_RISK.toFloat()) * 100
            val halfMark = max / 2

            while (animationTime <= max) {
                delay(2L)
                binding.progressBar.progress = animationTime.toInt()
                val float = animationTime / max
                animationTime += if (animationTime <= halfMark) {
                    float + 0.1f
                } else {
                    abs(1 - float) + 0.1f
                }
            }
            binding.progressBar.progress = max.toInt()
        }
    }


}