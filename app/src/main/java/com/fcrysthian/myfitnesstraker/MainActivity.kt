package com.fcrysthian.myfitnesstraker

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvMain: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calcs = mutableListOf<Calc>()
        calcs.add(
            Calc(
                id = 1,
                drawableId = R.drawable.ic_baseline_wb_sunny_24,
                textStringId = R.string.imc,
                color = Color.GREEN
            )
        )
        calcs.add(
            Calc(
                id = 2,
                drawableId = R.drawable.ic_baseline_water_drop_24,
                textStringId = R.string.tmb,
                color = Color.YELLOW
            )
        )

        val adapter = MainAdapter(calcs) { id ->
            when (id) {
                1 -> {
                    val intent = Intent(this@MainActivity, ImcActivity::class.java)
                    startActivity(intent)
                }
                2 -> {
                    val intent = Intent(this@MainActivity, TmbActivity::class.java)
                    startActivity(intent)
                }
            }
            Log.i("Teste", "clicou $id!!")
        }

        rvMain = findViewById(R.id.rv_main)
        rvMain.adapter = adapter
        rvMain.layoutManager = GridLayoutManager(this, 2)

    }

    private inner class MainAdapter(
        private val calcs: List<Calc>,
//        private val onItemClickListener: OnItemClickListener
        private val onItemClickListener: (Int) -> Unit,
    ) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

        // 1 - Qual é o layout XML da celula especifica (item)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.recycler_main, parent, false)
            return MainViewHolder(view)
        }

        // 2 - disparado toda vez houver uma rolagem na tela e for necessario trocar o conteudo
        // da celula
        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemCurrent = calcs[position]
            holder.bind(itemCurrent)
        }

        // 3 - informar quantas celulas essa listagem terá
        override fun getItemCount(): Int {
            return calcs.size
        }

        // é a classe da celula em si!!!
        private inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(item: Calc) {
                val img: ImageView = itemView.findViewById(R.id.item_img_icon)
                val name: TextView = itemView.findViewById(R.id.item_txt_name)
                val container: LinearLayout = itemView.findViewById(R.id.item_container_imc)

                img.setImageResource(item.drawableId)
                name.setText(item.textStringId)
                container.setBackgroundColor(item.color)

                container.setOnClickListener {
                    // aqui ele é uma ref. function
                    onItemClickListener.invoke(item.id)

                    // aqui ele é uma ref. interface
                    // onItemClickListener.onClick(item.id)
                }
            }
        }

    }
}