package org.d3if4105.noteapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import org.d3if4105.noteapp.R
import org.d3if4105.noteapp.databinding.FragmentHomeBinding
import org.d3if4105.noteapp.ui.adapter.NotesAdapter
import org.d3if4105.noteapp.viewModel.NotesViewModel

class HomeFragment : Fragment() {

    lateinit var binding:FragmentHomeBinding
    val viewModel : NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container , false)

        setHasOptionsMenu(true)

        viewModel.getNotes().observe(viewLifecycleOwner, { notesList ->
            binding.recylerAll.layoutManager=GridLayoutManager(requireContext(), 1)
            binding.recylerAll.adapter = NotesAdapter(requireContext(), notesList)
        })

        binding.filterHigh.setOnClickListener {
            viewModel.getHighNotes().observe(viewLifecycleOwner, { notesList ->
                binding.recylerAll.layoutManager=GridLayoutManager(requireContext(), 1)
                binding.recylerAll.adapter = NotesAdapter(requireContext(), notesList)
            })
        }

        binding.allNotes.setOnClickListener {
            viewModel.getNotes().observe(viewLifecycleOwner, { notesList ->
                binding.recylerAll.layoutManager=GridLayoutManager(requireContext(), 1)
                binding.recylerAll.adapter = NotesAdapter(requireContext(), notesList)
            })
        }

        binding.filterLow.setOnClickListener {
            viewModel.getlowNotes().observe(viewLifecycleOwner, { notesList ->
                binding.recylerAll.layoutManager=GridLayoutManager(requireContext(), 1)
                binding.recylerAll.adapter = NotesAdapter(requireContext(), notesList)
            })
        }
        binding.filterMedium.setOnClickListener {
            viewModel.getMediumNotes().observe(viewLifecycleOwner, { notesList ->
                binding.recylerAll.layoutManager=GridLayoutManager(requireContext(), 1)
                binding.recylerAll.adapter = NotesAdapter(requireContext(), notesList)
            })
        }

        binding.btnAddNoted.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_createNotesFragment)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_about) {
            findNavController().navigate(
                R.id.action_homeFragment_to_aboutFragment)
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}