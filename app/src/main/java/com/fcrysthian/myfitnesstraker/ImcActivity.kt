package com.fcrysthian.myfitnesstraker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

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

            if(validate()){//Success
                Toast.makeText(this, "Sucesso", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validate():Boolean{
        //nao pode inserir valor nulos
        //nao pode inserir valor 0 para weight e height
        return editWeight.text.toString().isNotEmpty()&&
                editHeight.text.toString().isNotEmpty()&&
                !editWeight.text.startsWith("0")&&
                !editHeight.text.startsWith("0")
    }
}