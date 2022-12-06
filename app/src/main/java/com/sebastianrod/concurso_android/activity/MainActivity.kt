package com.sebastianrod.concurso_android.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.sebastianrod.concurso_android.R
import com.sebastianrod.concurso_android.databinding.ActivityMainBinding
import com.sebastianrod.concurso_android.fragments.PrendasFragment
import com.sebastianrod.concurso_android.utils.ForFragments

class MainActivity : AppCompatActivity() {


    private lateinit var binding:ActivityMainBinding

    private val ID_PRENDAS = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.bottomNavigationMain.add(MeowBottomNavigation.Model(ID_PRENDAS, R.drawable.ic_percha))

        binding.bottomNavigationMain.setOnClickMenuListener {
            when(it.id){
                ID_PRENDAS -> ForFragments.replaceFragment(supportFragmentManager, R.id.frame_container_main, PrendasFragment())
            }
        }

        binding.bottomNavigationMain.show(ID_PRENDAS)
        ForFragments.replaceFragment(supportFragmentManager, R.id.frame_container_main, PrendasFragment())

    }



}