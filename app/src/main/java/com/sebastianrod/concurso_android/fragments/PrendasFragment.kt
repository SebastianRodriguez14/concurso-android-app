package com.sebastianrod.concurso_android.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.sebastianrod.concurso_android.R
import com.sebastianrod.concurso_android.databinding.FragmentPrendasBinding
import com.sebastianrod.concurso_android.fragments.adapter.PrendaAdapter
import com.sebastianrod.concurso_android.model.Prenda
import com.sebastianrod.concurso_android.retrofit.ApiService
import com.sebastianrod.concurso_android.retrofit.RetrofitClient
import com.sebastianrod.concurso_android.utils.ForFragments
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PrendasFragment : Fragment() {

    private lateinit var binding:FragmentPrendasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPrendasBinding.inflate(layoutInflater)

        val gridLayoutManager = GridLayoutManager(binding.root.context, 3)
        gridLayoutManager.widthMode
        binding.recyclerViewPrendas.layoutManager = gridLayoutManager

        apiGetPrendas()

        binding.prendasRegistrar.setOnClickListener{
            ForFragments.replaceInFragment(PrendasRegisterFragment() ,fragmentManager)
        }



        return binding.root
    }

    private fun initPrendas(prendas: List<Prenda>){
        binding.recyclerViewPrendas.adapter = PrendaAdapter(prendas)
    }

    private fun apiGetPrendas() {

        val apiService:ApiService = RetrofitClient.getRetrofit().create(ApiService::class.java)

        val resultPrendas:Call<List<Prenda>> = apiService.getPrendas()

        resultPrendas.enqueue(object : Callback<List<Prenda>> {
            override fun onResponse(call: Call<List<Prenda>>, response: Response<List<Prenda>>) {
                val listPrendas = response.body()

                if (listPrendas != null) {
                    println(listPrendas)
                    initPrendas(listPrendas)
                }
            }
            override fun onFailure(call: Call<List<Prenda>>, t: Throwable) {
                println("Error: getPrendas() failure")

            }
        })

    }

}