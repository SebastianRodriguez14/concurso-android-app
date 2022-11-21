package com.sebastianrod.concurso_android.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.sebastianrod.concurso_android.R
import com.sebastianrod.concurso_android.databinding.FragmentPrendasRegisterBinding
import com.sebastianrod.concurso_android.model.Marca
import com.sebastianrod.concurso_android.model.Prenda
import com.sebastianrod.concurso_android.retrofit.ApiService
import com.sebastianrod.concurso_android.retrofit.RetrofitClient
import com.sebastianrod.concurso_android.utils.ForFragments
import com.sebastianrod.concurso_android.utils.ForValidations
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PrendasRegisterFragment : Fragment() {

    private lateinit var binding: FragmentPrendasRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPrendasRegisterBinding.inflate(layoutInflater)

        binding.registerBack.setOnClickListener {
            ForFragments.replaceInFragment(PrendasFragment(), fragmentManager)
        }

        binding.registerBotonRegistrar.setOnClickListener {
            if (checkInputsCompleted()){
                apiPostPrendas()
            }
        }

        return binding.root
    }


    private fun checkInputsCompleted():Boolean{

        val checks = arrayOf(
            ForValidations.valInput(binding.registerNombre, binding.registerNombreError, ForValidations::valOnlyText),
            ForValidations.valInput(binding.registerMarca, binding.registerMarcaError, ForValidations::valOnlyText),
            ForValidations.valInput(binding.registerStock, binding.registerStockError, ForValidations::valNumber),
            validatePrice(),
            ForValidations.valInput(binding.registerUrlImagen, binding.registerUrlImagenError, ForValidations::valAllTypeText),
            ForValidations.valInput(binding.registerUrlTienda, binding.registerUrlTiendaError, ForValidations::valAllTypeText)
        )

        return !checks.contains(true)

    }

    private fun validatePrice() : Boolean{
        return try{

            val price = binding.registerPrecio.text.toString().toDouble()
            binding.registerPrecioError.visibility = View.INVISIBLE
            false
        } catch (e:Exception){
            binding.registerPrecioError.text = "Solo se permiten decimales"
            binding.registerPrecioError.visibility = View.VISIBLE
            true
        }
    }

    private fun apiPostPrendas(){

        val apiService: ApiService = RetrofitClient.getRetrofit().create(ApiService::class.java)

        val prenda = Prenda(0, binding.registerNombre.text.toString(),
            binding.registerPrecio.text.toString().toDouble(),
            binding.registerStock.text.toString().toInt(),
            binding.registerUrlTienda.text.toString(),
            binding.registerUrlImagen.text.toString(),
            Marca(1, "Gucci")
            )


        val resultPrenda: Call<Prenda> = apiService.postPrenda(prenda)

        resultPrenda.enqueue(object : Callback<Prenda> {
            override fun onResponse(call: Call<Prenda>, response: Response<Prenda>) {
                println(response.body())
            }

            override fun onFailure(call: Call<Prenda>, t: Throwable) {
                println("Error: postUser() failure.")
            }
        } )

    }

}