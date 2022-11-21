package com.sebastianrod.concurso_android.fragments.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sebastianrod.concurso_android.R
import com.sebastianrod.concurso_android.model.Prenda

class PrendaViewHolder(val view:View) :RecyclerView.ViewHolder(view) {


    val pr_image = view.findViewById<ImageView>(R.id.prenda_item_image)
    val pr_price = view.findViewById<TextView>(R.id.prenda_item_price)

    fun render(prenda:Prenda){

        Glide.with(view.context).load(prenda.url_imagen).into(pr_image)
        pr_price.text = "$ %.2f".format(prenda.precio)
    }

}