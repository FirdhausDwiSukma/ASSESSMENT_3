package org.d3if4105.noteapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.d3if4105.noteapp.R
import org.d3if4105.noteapp.databinding.FragmentAboutBinding
import org.d3if4105.noteapp.network.ApiStatus
import org.d3if4105.noteapp.ui.adapter.AboutAdapter
import org.d3if4105.noteapp.viewModel.AboutViewModel

class AboutFragment :  Fragment() {
    private lateinit var binding : FragmentAboutBinding
    private lateinit var aboutAdapter: AboutAdapter

    private val viewModel: AboutViewModel by lazy {
        ViewModelProvider(this)[AboutViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(layoutInflater, container, false)

        tipsView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()

    }

    @SuppressLint("Recycle")
    private fun tipsView() {

        aboutAdapter = AboutAdapter()
        with(binding.rvAboutItem) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = aboutAdapter
        }
    }

    private fun observeData(){
        viewModel.getTipsData().observe(viewLifecycleOwner) {
            aboutAdapter.setListTipsData(it)
        }
        viewModel.getStatus().observe(viewLifecycleOwner) {
            updateProgress(it)
        }
        viewModel.scheduleUpdater(requireActivity().application)
    }

    private fun updateProgress(status: ApiStatus) {
        when (status) {
            ApiStatus.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            ApiStatus.SUCCESS -> {
                binding.progressBar.visibility = View.GONE
            }
            ApiStatus.FAILED -> {
                binding.progressBar.visibility = View.GONE
                binding.errorConnection.visibility = View.VISIBLE
            }
        }
    }
}