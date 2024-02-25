package ru.ac.uniyar.tyumentsev.unitconverter.ui.converter

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import ru.ac.uniyar.tyumentsev.unitconverter.R
import ru.ac.uniyar.tyumentsev.unitconverter.databinding.ConverterFragmentBinding

class ConverterFragment : Fragment() {

    private lateinit var viewModel: ConverterViewModel

    private lateinit var binding: ConverterFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.converter_fragment, container, false
        )
        viewModel = ViewModelProvider(this)[ConverterViewModel::class.java]

        val converterPickerAdapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            viewModel.converters.map { it.name })

        binding.converterPicker.adapter = converterPickerAdapter

        binding.converterPicker.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    viewModel.changeConverter(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        binding.firstNumberUnit.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    viewModel.setFirstNumberUnit(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        binding.secondNumberUnit.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    viewModel.setSecondNumberUnit(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        viewModel.converter.observe(viewLifecycleOwner) {
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                viewModel.getUnitNames()
            )
            binding.firstNumberUnit.adapter = adapter
            binding.secondNumberUnit.adapter = adapter
            binding.secondNumberUnit.setSelection(1)
        }

        viewModel.firstNumber.observe(viewLifecycleOwner) {
            binding.firstNumber.removeTextChangedListener(firstNumberWatcher)
            binding.firstNumber.setText(it.toString())
            binding.firstNumber.addTextChangedListener(firstNumberWatcher)
        }

        viewModel.secondNumber.observe(viewLifecycleOwner) {
            binding.secondNumber.removeTextChangedListener(secondNumberWatcher)
            binding.secondNumber.setText(it.toString())
            binding.secondNumber.addTextChangedListener(secondNumberWatcher)
        }

        binding.firstNumber.addTextChangedListener(firstNumberWatcher)
        binding.secondNumber.addTextChangedListener(secondNumberWatcher)

        return binding.root
    }

    private val firstNumberWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val newValue = s?.toString()?.toDoubleOrNull() ?: 0.0
            Log.i("ConverterFragment", "first new value ${newValue}")
            viewModel.calculateSecondNumber(newValue)
        }
    }

    private val secondNumberWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val newValue = s?.toString()?.toDoubleOrNull() ?: 0.0
            Log.i("ConverterFragment", "second new value ${newValue}}")
            viewModel.calculateFirstNumber(newValue)
        }
    }
}
