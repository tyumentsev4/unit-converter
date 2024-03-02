package ru.ac.uniyar.tyumentsev.unitconverter

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.ac.uniyar.tyumentsev.unitconverter.viewmodel.ConverterViewModel
import ru.ac.uniyar.tyumentsev.unitconverter.viewmodel.ConverterViewModelFactory


class MainActivity : AppCompatActivity() {
    private val viewModel: ConverterViewModel by viewModels {
        ConverterViewModelFactory((application as ConverterApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
