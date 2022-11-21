package com.sebastianrod.concurso_android.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.sebastianrod.concurso_android.R

class ForFragments {

    companion object{
        // Method to replace a fragment from an activity
        fun replaceFragment(fragmentManager: FragmentManager, container:Int, fragment: Fragment){
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(container, fragment)
            fragmentTransaction.commit()
        }

        // Method to replace a fragment from another fragment
        fun replaceInFragment(fragment: Fragment, fragmentManager:FragmentManager?) {
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_container_main, fragment)?.commit()
        }
    }

}