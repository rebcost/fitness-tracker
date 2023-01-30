package com.fcrysthian.myfitnesstraker

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvMain: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainItems = mutableListOf<MainItem>()
        mainItems.add(
            MainItem(
                id = 1,
                drawableId = R.drawable.ic_baseline_wb_sunny_24,
                textStringId = R.string.imc,
                color = Color.GREEN
            )
        )
        mainItems.add(
            MainItem(
                id = 2,
                drawableId = R.drawable.ic_baseline_water_drop_24,
                textStringId = R.string.tmb,
                color = Color.YELLOW
            )
        )

        mainItems.add(
            MainItem(
                id = 3,
                drawableId = R.drawable.ic_baseline_water_drop_24,
                textStringId = R.string.tmb,
                color = Color.DKGRAY
            )
        )

        mainItems.add(
            MainItem(
                id = 4,
                drawableId = R.drawable.ic_baseline_water_drop_24,
                textStringId = R.string.tmb,
                color = Color.MAGENTA
            )
        )

        val adapter = MainAdapter(mainItems)
        rvMain = findViewById(R.id.rv_main)
        rvMain.adapter = adapter
        rvMain.layoutManager = GridLayoutManager(this,2 )

    }

    private inner class MainAdapter(private val mainItem: List<MainItem>) : RecyclerView.Adapter<MainViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.recycler_main, parent, false)
            return MainViewHolder(view)
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemCurrent = mainItem[position]
            holder.bind(itemCurrent)
        }

        override fun getItemCount(): Int {
            return mainItem.size
        }

    }

    private class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: MainItem){
            val img: ImageView = itemView.findViewById(R.id.item_img_icon)
            val name: TextView = itemView.findViewById(R.id.item_txt_name)
            val container: LinearLayout = itemView.findViewById(R.id.item_container_imc)

            img.setImageResource(item.drawableId)
            name.setText(item.textStringId)
            container.setBackgroundColor(item.color)

        }
    }
}