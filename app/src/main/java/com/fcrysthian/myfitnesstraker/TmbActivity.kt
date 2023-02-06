package com.fcrysthian.myfitnesstraker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.fcrysthian.myfitnesstraker.model.Calc

class TmbActivity : AppCompatActivity() {

    private lateinit var lifestyle: AutoCompleteTextView
    private lateinit var editWeight: EditText
    private lateinit var editHeight: EditText
    private lateinit var editAge: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tmb)

        lifestyle = findViewById(R.id.auto_lifestyle)
        editWeight = findViewById(R.id.edit_tmb_weight)
        editHeight = findViewById(R.id.edit_tmb_height)
        editAge = findViewById(R.id.edit_tmb_age)

        //Caixa de opções
        val items = resources.getStringArray(R.array.tmb_lifestyle)
        lifestyle.setText(items.first())
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        lifestyle.setAdapter(adapter)

        val btnTmbSend: Button = findViewById(R.id.btn_tmb_send)

        btnTmbSend.setOnClickListener{
            if (!validate()) { //Failure
                Toast.makeText(this, R.string.fields_message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }//Success

            val weight = editWeight.text.toString().toInt()
            val height = editHeight.text.toString().toInt()
            val age = editAge.text.toString().toInt()

            val result = calculateTmb(weight, height, age)
            val response = tmbRequest(result)
            Log.d("TMB", "Resultado: $response")

            AlertDialog.Builder(this)
                .setMessage(getString(R.string.tmb_response, response))
                .setPositiveButton(android.R.string.ok) { dialog, which -> }
                .setNegativeButton(R.string.save){dialog, which ->
                    Thread{
                        val app = application as App
                        val dao = app.db.calcDao()
                        dao.insert(Calc(type = "tmb", res = response))

                        runOnUiThread {
                            openListActivity()
                        }

                    }.start()
                }
                .create()
                .show()

            val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }

    }

    private fun tmbRequest(tmb: Double): Double {
        //recuperar a lista de item
        val items = resources.getStringArray(R.array.tmb_lifestyle)

        //verificar qual texto foi selecionado
        return when{
            lifestyle.text.toString() == items[0] -> tmb * 1.2
            lifestyle.text.toString() == items[1] -> tmb * 1.375
            lifestyle.text.toString() == items[2] -> tmb * 1.55
            lifestyle.text.toString() == items[3] -> tmb * 1.725
            lifestyle.text.toString() == items[4] -> tmb * 1.9
            else -> 0.0
        }

    }

    private fun calculateTmb(weight: Int, height: Int, age: Int): Double {
        return 66 + (13.8 * weight) + (5 * height) - (6.8 * age)
    }

    private fun validate(): Boolean {
        //nao pode inserir valor nulos
        //nao pode inserir valor 0 para weight e height
        return editWeight.text.toString().isNotEmpty() &&
                editHeight.text.toString().isNotEmpty() &&
                editAge.text.toString().isNotEmpty() &&
                !editWeight.text.startsWith("0") &&
                !editHeight.text.startsWith("0") &&
                !editAge.text.startsWith("0")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_search){
            finish()
            openListActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openListActivity() {
        val intent = Intent(this, ListCalcActivity::class.java)
        intent.putExtra("type", "tmb")
        startActivity(intent)
    }

}