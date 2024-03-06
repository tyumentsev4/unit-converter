package ru.ac.uniyar.tyumentsev.unitconverter.fragment

import android.icu.text.DecimalFormat
import android.icu.text.NumberFormat
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
import ru.ac.uniyar.tyumentsev.unitconverter.dto.UnitRow
import ru.ac.uniyar.tyumentsev.unitconverter.repository.ConverterRepository
import ru.ac.uniyar.tyumentsev.unitconverter.viewmodel.NewConverterViewModel
import ru.ac.uniyar.tyumentsev.unitconverter.viewmodel.NewConverterViewModelFactory


class NewConverterFragment : Fragment() {

    private lateinit var viewModel: NewConverterViewModel

    private lateinit var binding: NewConverterFragmentBinding

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        val rowContainer = binding.rowContainer
        if (savedInstanceState != null) {
            val unitRows = savedInstanceState.getParcelableArrayList<UnitRow>("unitRows")
            rowContainer.removeAllViews()
            if (unitRows != null) {
                for (value in unitRows) {
                    val view =
                        layoutInflater.inflate(R.layout.unit_row_fragment, rowContainer, false)
                    val removeRowButton =
                        view.findViewById<FloatingActionButton>(R.id.removeRowButton)
                    this.writeRowUnit(view, value)
                    removeRowButton.setOnClickListener {
                        if (rowContainer.childCount > 2) {
                            rowContainer.removeView(view)
                        }
                    }
                    rowContainer.addView(view, rowContainer.childCount)
                }
            }
        }
    }

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
                binding.converterNameEdit.error =
                    context?.resources?.getString(R.string.empty_field_error_message)
                return@setOnClickListener
            }
            rowContainer.forEach { unit ->
                run {
                    val numberField = unit.findViewById<EditText>(R.id.editNumber)
                    val number = numberField.text.toString().toDoubleOrNull()
                    val unitNameField = unit.findViewById<EditText>(R.id.editUnit)
                    val unitName = unitNameField.text.toString().trim()
                    if (number == null) {
                        unitNameField.error =
                            context?.resources?.getString(R.string.empty_field_error_message)
                        return@setOnClickListener
                    }
                    if (unitName.isEmpty()) {
                        unitNameField.error =
                            context?.resources?.getString(R.string.empty_field_error_message)
                        return@setOnClickListener
                    }
                    units = units.plus(UnitDto(name = unitName, value = 1 / number))
                }
            }
            viewModel.createConverter(converterName, units)
            findNavController().navigate(R.id.action_newConverterFragment_to_converterFragment)
        }

        return binding.root
    }

    private fun writeRowUnit(view: View, value: UnitRow?) {
        val numberField = view.findViewById<EditText>(R.id.editNumber)
        val unitNameField = view.findViewById<EditText>(R.id.editUnit)
        if (value?.value != null && value.value != 0.0) {
            val nf: NumberFormat = DecimalFormat("##.###")
            numberField.setText(nf.format(value.value))
        } else {
            numberField.setText("")
        }
        unitNameField.setText(value?.name)
    }

    private fun readUnitRows(): List<UnitRow> {
        var units = emptyList<UnitRow>()
        binding.rowContainer.forEach {
            val numberField = it.findViewById<EditText>(R.id.editNumber)
            val number = numberField.text.toString().toDoubleOrNull()
            val unitNameField = it.findViewById<EditText>(R.id.editUnit)
            val unitName = unitNameField.text.toString().trim()
            units = units.plus(UnitRow(name = unitName, value = number))
        }
        return units
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val unitRows = this.readUnitRows()
        outState.putParcelableArrayList("unitRows", ArrayList(unitRows))
    }
}
