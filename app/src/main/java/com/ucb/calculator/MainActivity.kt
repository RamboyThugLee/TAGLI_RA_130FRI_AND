package com.ucb.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val number1 = findViewById<EditText>(R.id.etNumber1)
        val number2 = findViewById<EditText>(R.id.etNumber2)
        val result = findViewById<TextView>(R.id.tvResult)

        val addBtn = findViewById<Button>(R.id.btnAdd)
        val subBtn = findViewById<Button>(R.id.btnSubtract)
        val mulBtn = findViewById<Button>(R.id.btnMultiply)
        val divBtn = findViewById<Button>(R.id.btnDivide)

        addBtn.setOnClickListener {
            result.text = performOperation(number1, number2) { a, b -> a + b }
        }

        subBtn.setOnClickListener {
            result.text = performOperation(number1, number2) { a, b -> a - b }
        }

        mulBtn.setOnClickListener {
            result.text = performOperation(number1, number2) { a, b -> a * b }
        }

        divBtn.setOnClickListener {
            result.text = if (number2.text.toString().toDoubleOrNull() == 0.0) {
                "Error: Division by Zero"
            } else {
                performOperation(number1, number2) { a, b -> a / b }
            }
        }
    }

    private fun performOperation(et1: EditText, et2: EditText, operation: (Double, Double) -> Double): String {
        val num1 = et1.text.toString().toDoubleOrNull()
        val num2 = et2.text.toString().toDoubleOrNull()

        return if (num1 != null && num2 != null) {
            "%.2f".format(operation(num1, num2))
        } else {
            "Invalid Input"
        }
    }
}