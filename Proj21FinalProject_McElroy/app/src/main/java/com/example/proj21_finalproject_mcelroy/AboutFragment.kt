package com.example.proj21_finalproject_mcelroy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize UI elements and set text/image resources
        val imageViewMj = view.findViewById<ImageView>(R.id.image_mj)
        imageViewMj.setImageResource(R.drawable.mj_image)

        // Handle any other logic specific to this fragment
    }
}