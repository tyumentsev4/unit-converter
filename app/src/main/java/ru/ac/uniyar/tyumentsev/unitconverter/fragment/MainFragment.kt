package ru.ac.uniyar.tyumentsev.unitconverter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.ac.uniyar.tyumentsev.unitconverter.R
import ru.ac.uniyar.tyumentsev.unitconverter.database.AppDatabase
import ru.ac.uniyar.tyumentsev.unitconverter.databinding.MainFragmentBinding

class MainFragment: Fragment() {

    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.main_fragment, container, false
        )
        val application = requireNotNull(this.activity).application
        AppDatabase.getDatabase(application)

        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_converterFragment)
        }
        return binding.root
    }
}
