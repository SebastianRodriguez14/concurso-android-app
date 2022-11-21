package com.sebastianrod.concurso_android.model

data class Prenda (val prenda_id: Int, val nombre:String, val precio:Double,
                   val stock:Int, val url_tienda:String, val url_imagen:String,
                   val marca:Marca){
}