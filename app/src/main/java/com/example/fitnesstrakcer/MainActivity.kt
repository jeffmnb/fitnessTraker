package com.example.fitnesstrakcer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvOptionList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val optionsList = mutableListOf<OptionList>(
            OptionList(1, R.string.txt_imc1, R.drawable.imc_icon),
            OptionList(2, R.string.txt_imc2, R.drawable.history_icon)
        )

        rvOptionList = findViewById(R.id.rVOptionsList)
        rvOptionList.adapter = RvOptionListAdapter(optionsList) { item ->
            when (item) {
                1 -> {
                    val intentNavigate = Intent(this, IMCActivity::class.java)
                    startActivity(intentNavigate)
                }

                else -> Toast.makeText(this, "Abrir tela X", Toast.LENGTH_SHORT).show()
            }
        }
        rvOptionList.layoutManager = GridLayoutManager(this, 2)
    }

    private class RvOptionListAdapter(
        val optionsList: List<OptionList>,
        val handleClickItem: (Int) -> Unit
    ) :
        RecyclerView.Adapter<RvOptionListAdapter.RvOptionListHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvOptionListHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.main_item, parent, false)
            return RvOptionListHolder(view)
        }

        override fun getItemCount(): Int {
            return optionsList.size
        }

        override fun onBindViewHolder(holder: RvOptionListHolder, position: Int) {
            val currentItem = optionsList[position]
            return holder.bind(currentItem)
        }

        private inner class RvOptionListHolder(val viewItem: View) :
            RecyclerView.ViewHolder(viewItem) {
            fun bind(item: OptionList) {

                val textItem = viewItem.findViewById<TextView>(R.id.text_item)
                textItem.setText(item.textItem)

                val imageItem = viewItem.findViewById<ImageView>(R.id.image_item)
                imageItem.setImageResource(item.imageItem)

                val btnItem = viewItem.findViewById<LinearLayout>(R.id.btn_item)
                btnItem.setOnClickListener {
                    handleClickItem.invoke(item.idItem)
                }
            }
        }
    }
}
