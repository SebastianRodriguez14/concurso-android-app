package com.sebastianrod.concurso_android.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.sebastianrod.concurso_android.R
import com.sebastianrod.concurso_android.databinding.FragmentImageDialogBinding
import com.sebastianrod.concurso_android.utils.ObjectsSelected

class ImageDialogFragment : DialogFragment() {

    private lateinit var binding:FragmentImageDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentImageDialogBinding.inflate(layoutInflater)

        return binding.root
    }

}