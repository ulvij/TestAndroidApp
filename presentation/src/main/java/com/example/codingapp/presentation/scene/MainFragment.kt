package com.example.codingapp.presentation.scene

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.codingapp.presentation.R
import com.example.codingapp.presentation.base.BaseFragment
import com.example.codingapp.presentation.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainFragment : BaseFragment<MainState, MainEffect, MainViewModel, FragmentMainBinding>() {

    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainBinding
        get() = FragmentMainBinding::inflate

    override val viewModel: MainViewModel by viewModels()

    private var selectedEditMode: EditMode? = null

    override val bindViews: FragmentMainBinding.() -> Unit = {

        imageEditMaxPrice.setOnClickListener {
            textInputLabel.text = getString(R.string.label_set_max_price)
            linearInput.isVisible = true
            selectedEditMode = EditMode.MAX_RATE
        }

        imageEditMinPrice.setOnClickListener {
            textInputLabel.text = getString(R.string.label_set_min_price)
            linearInput.isVisible = true
            selectedEditMode = EditMode.MIN_RATE
        }

        buttonUpdate.setOnClickListener {
            val price = textInput.text.toString().toFloatOrNull() ?: return@setOnClickListener
            val editMode = selectedEditMode ?: return@setOnClickListener
            when (editMode) {
                EditMode.MIN_RATE -> viewModel.setMinRange(price)
                EditMode.MAX_RATE -> viewModel.setMaxRange(price)
            }
        }

        viewModel.minRate.observe(viewLifecycleOwner) {
            textMinRate.text = it.toString()
        }

        viewModel.maxRate.observe(viewLifecycleOwner) {
            textMaxRate.text = it.toString()
        }
    }


    override fun observeState(state: MainState) {
        when (state) {
            is MainState.ShowBtcPrice -> {
                binding.textBtcPrice.text = state.price.toString()
            }
        }
    }

    override fun observeEffect(effect: MainEffect) {
        when (effect) {
            MainEffect.UpdatedRates -> {
                binding.linearInput.isVisible = false
                binding.textInputLabel.text = ""
                binding.textInput.text.clear()

            }
        }
    }

}

enum class EditMode {
    MIN_RATE, MAX_RATE
}