package com.example.fitnesstrakcer

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class IMCActivity : AppCompatActivity() {

    private lateinit var editWeight: EditText
    private lateinit var editHeight: EditText
    private lateinit var sendImcButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_imc)

        editWeight = findViewById(R.id.editWeight)
        editHeight = findViewById(R.id.editHeight)
        sendImcButton = findViewById(R.id.sendImcButton)

        sendImcButton.setOnClickListener { handleValidate() }
    }

    private fun handleValidate() {
        val editWeightValue = editWeight.text.toString()
        val editHeightValue = editHeight.text.toString()

        val hasValidated = editWeightValue.isNotEmpty() && editHeight.text.toString()
            .isNotEmpty() && !editWeightValue.startsWith("0") && !editHeightValue.startsWith("0")

        when (hasValidated) {
            true -> caculateIMC(editWeightValue.toInt(), editHeightValue.toInt())
            false -> Toast.makeText(this, "Por favor, revise os dados", Toast.LENGTH_SHORT).show()
        }
    }


    private fun caculateIMC(weight: Int, height: Int) {
        val result = weight / ((height / 100.0) * (height / 100.0))

        val inputService = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputService.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        AlertDialog.Builder(this).setTitle(getString(R.string.imc_response, result))
            .setMessage(imcResponse((result)))
            .setNegativeButton("Salvar") { dialog, _ ->
                // salvar
                dialog.dismiss()
            }
            .setPositiveButton("Obrigado") { dialog, _ ->
                dialog.dismiss()
            }.show()

        return
    }

    private fun imcResponse(imc: Double): String {
        return getString(
            when {
                imc < 15.0 -> R.string.imc_severely_low_weight
                imc < 16.0 -> R.string.imc_very_low_weight
                imc < 18.5 -> R.string.imc_low_weight
                imc < 25.0 -> R.string.normal
                imc < 30.0 -> R.string.imc_high_weight
                imc < 35.0 -> R.string.imc_so_high_weight
                imc < 40.0 -> R.string.imc_severely_high_weight

                else -> R.string.imc_extreme_weight
            }
        )
    }
}