package com.fcrysthian.myfitnesstraker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes

class ImcActivity : AppCompatActivity() {

    private lateinit var editWeight: EditText
    private lateinit var editHeight: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)

        editWeight = findViewById(R.id.edit_imc_weight)
        editHeight = findViewById(R.id.edit_imc_height)

        val btnImcSend: Button = findViewById(R.id.btn_imc_send)

        btnImcSend.setOnClickListener {

            if (!validate()){ //Failure
                Toast.makeText(this, R.string.fields_message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
           //success
            val weight = editWeight.text.toString().toInt()
            val height = editHeight.text.toString().toInt()

            val result = calculateImc(weight, height)
            Log.d("IMC", "Resultado: $result")

            val imcResponseId =  imcResponse(result)
            Toast.makeText(this, imcResponseId, Toast.LENGTH_SHORT).show()
        }
    }

    private fun validate():Boolean{
        //nao pode inserir valor nulos
        //nao pode inserir valor 0 para weight e height
        return  editWeight.text.toString().isNotEmpty()&&
                editHeight.text.toString().isNotEmpty()&&
                !editWeight.text.startsWith("0")&&
                !editHeight.text.startsWith("0")
    }

    private fun calculateImc(weight: Int, height: Int): Double{
        return weight / ((height / 100.0) * (height / 100.0))
    }

    @StringRes
    private fun imcResponse(imc: Double):Int{
        return when {
            imc < 15.0 -> R.string.imc_severely_low_weight
            imc < 16.0 -> R.string.imc_very_low_weight
            imc < 18.5 -> R.string.imc_low_weight
            imc < 25.0 -> R.string.normal
            imc < 30.0 -> R.string.imc_high_weight
            imc < 35.0 -> R.string.imc_so_high_weight
            imc < 40.0 -> R.string.imc_severely_high_weight
            else -> R.string.imc_extreme_weight
        }
    }

}