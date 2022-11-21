package com.sebastianrod.concurso_android.fragments.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sebastianrod.concurso_android.R
import com.sebastianrod.concurso_android.model.Prenda

class PrendaAdapter(private val prendasList: List<Prenda>) : RecyclerView.Adapter<PrendaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrendaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PrendaViewHolder(layoutInflater.inflate(R.layout.prendas_item, parent, false))
    }

    override fun getItemCount(): Int = prendasList.size

    override fun onBindViewHolder(holder: PrendaViewHolder, position: Int) {
        val item = prendasList[position]
        holder.render(item)
    }

}