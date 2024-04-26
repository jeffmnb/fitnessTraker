package com.example.fitnesstrakcer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesstrakcer.model.ImcCalc
import java.text.SimpleDateFormat
import java.util.Locale

class IMCRegisters : AppCompatActivity() {

    lateinit var rvRegisters: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc_registers)

        val app = application as App
        val imcDao = app.db.imcCalcDao()

        rvRegisters = findViewById(R.id.rvRegisters)

        val params = intent?.extras?.getString("type") ?: "imc"


        fun handleDeleteRegister(imc: ImcCalc) {
            Thread {
                imcDao.delete(imc)
            }.start()
        }

        Thread {
            val response = imcDao.getRegisterByType(params)

            rvRegisters.adapter = RvRegisterAdapter(response) { item ->

                AlertDialog.Builder(this)
                    .setTitle("Deseja remover esse resultado?")
                    .setMessage("Ao remover, não será possível visualizar novamente.")
                    .setPositiveButton("Remover") { dialog, _ ->
                        // excluir
                        handleDeleteRegister(item)
                    }
                    .setNegativeButton("Manter") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()


            }
            rvRegisters.layoutManager = LinearLayoutManager(this)
        }.start()
    }
}

private class RvRegisterAdapter(
    val registersList: List<ImcCalc>,
    val handlePressItem: (ImcCalc) -> Unit
) :
    RecyclerView.Adapter<RvRegisterAdapter.RvRegisterHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvRegisterHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.register_item, parent, false)
        return RvRegisterHolder(view)
    }

    override fun getItemCount(): Int {
        return registersList.size
    }

    override fun onBindViewHolder(holder: RvRegisterHolder, position: Int) {
        val itemCurrent = registersList[position]
        holder.bind(itemCurrent)
    }


    inner class RvRegisterHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: ImcCalc) {

            val titleRegister = view.findViewById<TextView>(R.id.titleRegister)
            titleRegister.text = item.message

            val valueRegiser = view.findViewById<TextView>(R.id.valueRegister)
            valueRegiser.text = item.result.toString()

            val dateRegister = view.findViewById<TextView>(R.id.dateRegister)
            val dateFormatted = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR"))
            dateRegister.text = dateFormatted.format(item.createdDate).toString()

            val itemCardRegister = view.findViewById<LinearLayout>(R.id.ItemCardRegister)
            itemCardRegister.setOnLongClickListener {
                handlePressItem.invoke(item)
                true
            }
        }
    }


}