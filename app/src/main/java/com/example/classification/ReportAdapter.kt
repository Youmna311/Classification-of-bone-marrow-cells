package com.example.classification

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.classification.gpdao.Patientclass

class ReportAdapter (var list : ArrayList<Patientclass>? , var ccontext:Context): RecyclerView.Adapter<ReportAdapter.Reportviwholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Reportviwholder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.listmodel, parent, false)
        return Reportviwholder(view)

    }

    override fun onBindViewHolder(holder: Reportviwholder, position: Int) {

        val item = list!!.get(position)
        holder.pname.setText(item.patientname)
        holder.pstate.setText(item.patientstate)

        if (onedititemcliclklistener != null) {
            holder.cardView.setOnClickListener {
                onedititemcliclklistener?.oneditclick(item)


            }
        }

    }

    override fun getItemCount(): Int {

        if (list?.size == null)
            return 0
        else
            return list!!.size
    }

    class Reportviwholder(val view: View) : RecyclerView.ViewHolder(view) {
        val pname: TextView = view.findViewById(R.id.patientname)
        val pstate: TextView = view.findViewById(R.id.patientstate)
        val cardView: CardView = view.findViewById(R.id.itemcardview)

    }

    var onedititemcliclklistener: Onedititemcliclklistener? = null

    public interface Onedititemcliclklistener {
        fun oneditclick(report: Patientclass)
    }

    // constructor
    init {
        this.list = list
        this.ccontext = ccontext


    }
}