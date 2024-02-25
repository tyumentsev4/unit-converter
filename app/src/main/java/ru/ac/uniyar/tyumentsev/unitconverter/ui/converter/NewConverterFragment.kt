package ru.ac.uniyar.tyumentsev.unitconverter.ui.converter

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ru.ac.uniyar.tyumentsev.unitconverter.R
import ru.ac.uniyar.tyumentsev.unitconverter.databinding.NewConverterFragmentBinding

class NewConverterFragment : Fragment() {

    private lateinit var viewModel: NewConverterViewModel

    private lateinit var binding: NewConverterFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
        inflater,
        R.layout.new_converter_fragment,
        container,
        false
        )
        viewModel = ViewModelProvider(this)[NewConverterViewModel::class.java]
        return binding.root
    }
}
