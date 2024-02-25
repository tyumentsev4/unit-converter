package ru.ac.uniyar.tyumentsev.unitconverter.ui.converter

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import ru.ac.uniyar.tyumentsev.unitconverter.R
import ru.ac.uniyar.tyumentsev.unitconverter.databinding.ConverterFragmentBinding

class ConverterFragment : Fragment() {

    private lateinit var viewModel: ConverterViewModel

    private lateinit var binding: ConverterFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.converter_fragment,
            container,
            false
        )
        viewModel = ViewModelProvider(this)[ConverterViewModel::class.java]

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            viewModel.converters.map { it.name }
        )

        binding.converterPicker.adapter = adapter

        binding.converterPicker.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.changeConverter(position)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        return binding.root
    }
}
