package com.fcrysthian.myfitnesstraker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fcrysthian.myfitnesstraker.model.Calc
import java.text.SimpleDateFormat
import java.util.*

class ListCalcActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_calc)

        val result = mutableListOf<Calc>()
        val adapter = ListCalcAdapter(result)
        rv = findViewById(R.id.rv_list)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        val type = intent?.extras?.getString("type") ?: throw IllegalStateException("Type not found")

        Thread{
            val app = application as App
            val dao = app.db.calcDao()
            val response = dao.getRegisterByType(type)
            runOnUiThread {
                result.addAll(response)
                adapter.notifyDataSetChanged()
            }
        }.start()

    }

    private inner class ListCalcAdapter(
        private val listCalc: List<Calc>,
    ) : RecyclerView.Adapter<ListCalcAdapter.ListCalcViewHolder>() {

        // 1 - Qual é o layout XML da celula especifica (item)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCalcViewHolder {
            val view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false)
            return ListCalcViewHolder(view)
        }

        // 2 - disparado toda vez houver uma rolagem na tela e for necessario trocar o conteudo
        // da celula
        override fun onBindViewHolder(holder: ListCalcViewHolder, position: Int) {
            val itemCurrent = listCalc[position]
            holder.bind(itemCurrent)
        }

        // 3 - informar quantas celulas essa listagem terá
        override fun getItemCount(): Int {
            return listCalc.size
        }

        // é a classe da celula em si!!!
        private inner class ListCalcViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(item: Calc) {
                val tv = itemView as TextView
                val sdf = SimpleDateFormat("dd/mm/yyyy HH:mm", Locale("pt", "BR"))
                val date = sdf.format(item.createdDate)
                val res = item.res
                tv.text = getString(R.string.list_response, res, date)
            }
        }

    }
}