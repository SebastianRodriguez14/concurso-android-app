package com.sebastianrod.concurso_android.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.Image
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.sebastianrod.concurso_android.R
import com.sebastianrod.concurso_android.databinding.FragmentPrendasRegisterBinding
import com.sebastianrod.concurso_android.model.Marca
import com.sebastianrod.concurso_android.model.Prenda
import com.sebastianrod.concurso_android.retrofit.ApiService
import com.sebastianrod.concurso_android.retrofit.RetrofitClient
import com.sebastianrod.concurso_android.utils.ArrayForClass
import com.sebastianrod.concurso_android.utils.ForFragments
import com.sebastianrod.concurso_android.utils.ForValidations
import com.sebastianrod.concurso_android.utils.ObjectsSelected
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
        apiGetMarcas()
        binding.registerBotonRegistrar.setOnClickListener {
            if (checkInputsCompleted()){
                apiPostPrendas()
                ForFragments.replaceInFragment(PrendasFragment(), fragmentManager)
            }
        }

        binding.registerMarca.setOnClickListener{
            openDialogBrand()
        }

        binding.registerBack.setOnClickListener {
            ForFragments.replaceInFragment(PrendasFragment(), fragmentManager)
        }

        binding.registerBotonCancelar.setOnClickListener {
            ForFragments.replaceInFragment(PrendasFragment(), fragmentManager)
        }

        binding.registerUrlImagen.addTextChangedListener {
            Glide.with(this).load(it.toString()).into(binding.registerImagen)
        }
        binding.registerImagen.setOnClickListener {
            openDialogImage()
        }

        return binding.root
    }

    private fun openDialogBrand(){
        if (ArrayForClass.marcas != null){
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Selecciona una marca")
                .setIcon(R.drawable.ic_marca)

            val opciones = arrayOfNulls<CharSequence>(ArrayForClass.marcas!!.size)
            var ind = 0
            for (marca in ArrayForClass.marcas!!) {
                opciones[ind] = marca.nombre
                ind++
            }


            builder.setItems(opciones, DialogInterface.OnClickListener { dialog, which ->
                binding.registerMarca.setText(opciones[which])
            })

            val alertDialog = builder.create()
            alertDialog.show()
        } else {
            Toast.makeText(context, "Cargando las marcas...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openDialogImage(){

        val dialogImageBinding = layoutInflater.inflate(R.layout.fragment_image_dialog, null)
        val dialogImage = context?.let { Dialog(it) }
        dialogImage!!.setContentView(dialogImageBinding)
        dialogImage.setCancelable(true)
        dialogImage.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        Glide.with(this).load(binding.registerUrlImagen.text.toString()).into(dialogImageBinding.findViewById(R.id.image_dialog))
        dialogImage.show()
    }

    private fun searchMarcaByName(name:String):Marca?{

        return ArrayForClass.marcas!!.find { m -> m.nombre == name }

    }

    private fun checkInputsCompleted():Boolean{

        val checks = arrayOf(
            ForValidations.valInput(binding.registerNombre, binding.registerNombreError, ForValidations::valOnlyText),
            ForValidations.valInput(binding.registerMarca, binding.registerMarcaError, ForValidations::valOnlyText),
            ForValidations.valInput(binding.registerStock, binding.registerStockError, ForValidations::valNumber),
            ForValidations.valInput(binding.registerPrecio, binding.registerPrecioError, ForValidations::valDecimal),
            ForValidations.valInput(binding.registerUrlImagen, binding.registerUrlImagenError, ForValidations::valAllTypeText),
            ForValidations.valInput(binding.registerUrlTienda, binding.registerUrlTiendaError, ForValidations::valAllTypeText)
        )

        return !checks.contains(true)
    }

    private fun apiPostPrendas(){

        val apiService: ApiService = RetrofitClient.getRetrofit().create(ApiService::class.java)
        val marca = searchMarcaByName(binding.registerMarca.text.toString()) ?: return

        val prenda = Prenda(0, binding.registerNombre.text.toString(),
            ForValidations.removeBlanks(binding.registerPrecio.text.toString()).toDouble(),
            ForValidations.removeBlanks(binding.registerStock.text.toString()).toInt(),
            ForValidations.removeBlanks(binding.registerUrlTienda.text.toString()),
            ForValidations.removeBlanks(binding.registerUrlImagen.text.toString()),
            marca
            )



        val resultPrenda: Call<Prenda> = apiService.postPrenda(prenda)

        resultPrenda.enqueue(object : Callback<Prenda> {
            override fun onResponse(call: Call<Prenda>, response: Response<Prenda>) {
                println(response.body())
                if(response.body() != null) {
                    Toast.makeText(context!!.applicationContext, "Prenda registrada ✅", Toast.LENGTH_SHORT).show()
                    ArrayForClass.prendas!!.add(response.body()!!)
                }
            }

            override fun onFailure(call: Call<Prenda>, t: Throwable) {
                println("Error: postUser() failure.")
            }
        } )

    }

    private fun apiGetMarcas() {

        val apiService:ApiService = RetrofitClient.getRetrofit().create(ApiService::class.java)

        val resultMarcas:Call<List<Marca>> = apiService.getMarcas()

        resultMarcas.enqueue(object : Callback<List<Marca>> {
            override fun onResponse(call: Call<List<Marca>>, response: Response<List<Marca>>) {
                val listMarcas = response.body()

                if (listMarcas != null) {
                    ArrayForClass.marcas = listMarcas.toMutableList()
                }
            }
            override fun onFailure(call: Call<List<Marca>>, t: Throwable) {
                println("Error: getMarcas() failure")
                apiGetMarcas()
            }
        })

    }


}