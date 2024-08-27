package com.fps69.abworkmanager.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.fps69.abworkmanager.R
import com.fps69.abworkmanager.databinding.FragmentWorksBinding

class WorksFragment : Fragment() {

    private lateinit var binding:FragmentWorksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding=  FragmentWorksBinding.inflate(layoutInflater)

        binding.tv2.setOnClickListener {
            findNavController().navigate(R.id.action_worksFragment_to_assignWorkFragment)
        }

        return binding.root
    }


}