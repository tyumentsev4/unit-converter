package ru.ac.uniyar.tyumentsev.unitconverter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.forEach
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.ac.uniyar.tyumentsev.unitconverter.R
import ru.ac.uniyar.tyumentsev.unitconverter.database.AppDatabase
import ru.ac.uniyar.tyumentsev.unitconverter.databinding.NewConverterFragmentBinding
import ru.ac.uniyar.tyumentsev.unitconverter.dto.UnitDto
import ru.ac.uniyar.tyumentsev.unitconverter.repository.ConverterRepository
import ru.ac.uniyar.tyumentsev.unitconverter.viewmodel.NewConverterViewModel
import ru.ac.uniyar.tyumentsev.unitconverter.viewmodel.NewConverterViewModelFactory


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
        val application = requireNotNull(this.activity).application
        val dao = AppDatabase.getDatabase(application).converterDao()
        val viewModelFactory = NewConverterViewModelFactory(ConverterRepository(dao))
        viewModel = ViewModelProvider(this, viewModelFactory)[NewConverterViewModel::class.java]
        val rowContainer = binding.rowContainer

        binding.addRowButton.setOnClickListener {
            val rowView = layoutInflater.inflate(R.layout.unit_row_fragment, rowContainer, false)
            val removeRowButton = rowView.findViewById<FloatingActionButton>(R.id.removeRowButton)
            removeRowButton.setOnClickListener {
                if (rowContainer.childCount > 2) {
                    rowContainer.removeView(rowView)
                }
            }
            rowContainer.addView(rowView, rowContainer.childCount)
        }

        binding.doneButton.setOnClickListener {
            var units = listOf<UnitDto>()
            val converterName = binding.converterNameEdit.text.toString().trim()
            if (converterName.isEmpty()) {
                binding.converterNameEdit.error = context?.resources?.getString(R.string.empty_field_error_message)
                return@setOnClickListener
            }
            rowContainer.forEach { unit ->
                run {
                    val numberField = unit.findViewById<EditText>(R.id.editNumber)
                    val number = numberField.text.toString().toDoubleOrNull()
                    val unitNameField = unit.findViewById<EditText>(R.id.editUnit)
                    val unitName = unitNameField.text.toString().trim()
                    if (number == null) {
                        unitNameField.error = context?.resources?.getString(R.string.empty_field_error_message)
                        return@setOnClickListener
                    }
                    if (unitName.isEmpty()) {
                        unitNameField.error = context?.resources?.getString(R.string.empty_field_error_message)
                        return@setOnClickListener
                    }
                    units = units.plus(UnitDto(name = unitName, value = 1/number))
                }
            }
            viewModel.createConverter(converterName, units)
            findNavController().navigate(R.id.action_newConverterFragment_to_converterFragment)
        }

        return binding.root
    }
}
