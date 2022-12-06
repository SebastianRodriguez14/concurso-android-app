package com.sebastianrod.concurso_android.retrofit

import com.sebastianrod.concurso_android.model.Marca
import com.sebastianrod.concurso_android.model.Prenda
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("prenda/all")
    fun getPrendas():Call<List<Prenda>>

    @POST("prenda/add")
    fun postPrenda(@Body prenda:Prenda): Call<Prenda>

    @GET("marca/all")
    fun getMarcas():Call<List<Marca>>

}