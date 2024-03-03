package ru.ac.uniyar.tyumentsev.unitconverter.fragment

import android.icu.text.DecimalFormat
import android.icu.text.NumberFormat
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.ac.uniyar.tyumentsev.unitconverter.R
import ru.ac.uniyar.tyumentsev.unitconverter.database.AppDatabase
import ru.ac.uniyar.tyumentsev.unitconverter.databinding.ConverterFragmentBinding
import ru.ac.uniyar.tyumentsev.unitconverter.repository.ConverterRepository
import ru.ac.uniyar.tyumentsev.unitconverter.viewmodel.ConverterViewModel
import ru.ac.uniyar.tyumentsev.unitconverter.viewmodel.ConverterViewModelFactory


class ConverterFragment : Fragment() {

    private lateinit var viewModel: ConverterViewModel

    private lateinit var binding: ConverterFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.converter_fragment, container, false
        )
        val application = requireNotNull(this.activity).application
        val dao = AppDatabase.getDatabase(application).converterDao()
        val viewModelFactory = ConverterViewModelFactory(ConverterRepository(dao))
        viewModel = ViewModelProvider(this, viewModelFactory)[ConverterViewModel::class.java]

        viewModel.converters.observe(viewLifecycleOwner) {
            val converterPickerAdapter = viewModel.converters.value?.let { converter ->
                ArrayAdapter(requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    converter.map { it.name })
            }

            binding.converterPicker.adapter = converterPickerAdapter

        }

        viewModel.unitsForSelectedConverter.observe(viewLifecycleOwner) {
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                viewModel.getUnitNames()
            )
            binding.firstNumberUnit.adapter = adapter
            binding.firstNumberUnit.setSelection(viewModel.firstNumberUnitPosition.value ?: 0)
            binding.secondNumberUnit.adapter = adapter
            binding.secondNumberUnit.setSelection(viewModel.secondNumberUnitPosition.value ?: 1)
        }

        binding.converterPicker.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    viewModel.setConverter(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        viewModel.firstNumberUnitPosition.observe(viewLifecycleOwner) {
            binding.firstNumberUnit.setSelection(it)
        }

        viewModel.secondNumberUnitPosition.observe(viewLifecycleOwner) {
            binding.secondNumberUnit.setSelection(it)
        }

        viewModel.converterPosition.observe(viewLifecycleOwner) {
            binding.converterPicker.setSelection(it)
        }

        binding.firstNumberUnit.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    Log.i("first unit", position.toString())
                    viewModel.setFirstNumberUnit(
                        position,
                        binding.firstNumber.text.toString().toDoubleOrNull() ?: 0.0
                    )

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        binding.secondNumberUnit.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    Log.i("second unit", position.toString())
                    viewModel.setSecondNumberUnit(
                        position,
                        binding.firstNumber.text.toString().toDoubleOrNull() ?: 0.0
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        viewModel.firstNumber.observe(viewLifecycleOwner) {
            binding.firstNumber.removeTextChangedListener(firstNumberWatcher)
            if (it != 0.0) {
                val nf: NumberFormat = DecimalFormat("##.###")
                binding.firstNumber.setText(nf.format(it))
            } else {
                binding.firstNumber.setText("")
            }
            binding.firstNumber.addTextChangedListener(firstNumberWatcher)
        }

        viewModel.secondNumber.observe(viewLifecycleOwner) {
            binding.secondNumber.removeTextChangedListener(secondNumberWatcher)
            if (it != 0.0) {
                val nf: NumberFormat = DecimalFormat("##.###")
                binding.secondNumber.setText(nf.format(it))
            } else {
                binding.secondNumber.setText("")
            }
            binding.secondNumber.addTextChangedListener(secondNumberWatcher)
        }

        binding.firstNumber.addTextChangedListener(firstNumberWatcher)
        binding.secondNumber.addTextChangedListener(secondNumberWatcher)

        binding.actionConverterFragmentToNewConverterFragment.setOnClickListener {
            findNavController().navigate(R.id.action_converterFragment_to_newConverterFragment)
        }

        return binding.root
    }

    private val firstNumberWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val newValue = s?.toString()?.toDoubleOrNull() ?: 0.0
            viewModel.calculateSecondNumber(newValue)
        }
    }

    private val secondNumberWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val newValue = s?.toString()?.toDoubleOrNull() ?: 0.0
            viewModel.calculateFirstNumber(newValue)
        }
    }
}
