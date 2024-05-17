package com.example.easy_tiffin.ui.ui.home.fragments_user_order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.easy_tiffin.databinding.FragmentDogBinding

class order_preparing : Fragment() {
    private var _binding: FragmentDogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDogBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
}