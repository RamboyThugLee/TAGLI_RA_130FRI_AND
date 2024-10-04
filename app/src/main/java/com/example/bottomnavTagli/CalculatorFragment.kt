package com.example.bottomnavTagli

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bottomnavTagli.R

class CalculatorFragment : Fragment() {

    private lateinit var inputOne: EditText
    private lateinit var inputTwo: EditText
    private lateinit var resultView: TextView
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_calculator, container, false)

        inputOne = rootView.findViewById(R.id.etNumber1)
        inputTwo = rootView.findViewById(R.id.etNumber2)
        resultView = rootView.findViewById(R.id.tvResult)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        sharedViewModel.calculatorResult.observe(viewLifecycleOwner) { result ->
            result?.let {
                resultView.text = String.format("Result: %.2f", it)
            }
        }

        rootView.findViewById<Button>(R.id.btnAdd).setOnClickListener { performCalculation("+") }
        rootView.findViewById<Button>(R.id.btnSubtract).setOnClickListener { performCalculation("-") }
        rootView.findViewById<Button>(R.id.btnMultiply).setOnClickListener { performCalculation("*") }
        rootView.findViewById<Button>(R.id.btnDivide).setOnClickListener { performCalculation("/") }

        return rootView
    }

    private fun performCalculation(operator: String) {
        val firstValue = inputOne.text.toString().toDoubleOrNull()
        val secondValue = inputTwo.text.toString().toDoubleOrNull()

        if (firstValue != null && secondValue != null) {
            val calculationResult = when (operator) {
                "+" -> firstValue + secondValue
                "-" -> firstValue - secondValue
                "*" -> firstValue * secondValue
                "/" -> if (secondValue != 0.0) firstValue / secondValue else null
                else -> null
            }

            calculationResult?.let {
                sharedViewModel.calculatorResult.value = it
                resultView.text = String.format("Result: %.2f", it)
            } ?: run {
                resultView.text = "Error: Cannot divide by zero"
            }
        } else {
            resultView.text = "Error: Please enter valid numbers"
        }
    }
}