package org.d3if4105.noteapp.ui.fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import org.d3if4105.noteapp.R
import org.d3if4105.noteapp.databinding.FragmentCreateNotesBinding
import org.d3if4105.noteapp.model.Notes
import org.d3if4105.noteapp.viewModel.NotesViewModel
import java.util.*


class CreateNotesFragment : Fragment() {

    lateinit var binding:FragmentCreateNotesBinding
    var priority: String = "1"
    val viewModel : NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentCreateNotesBinding.inflate(layoutInflater, container, false)

        binding.pGreen.setImageResource(R.drawable.ic_baseline_done_24)

        binding.pGreen.setOnClickListener {
            priority = "1"
            binding.pGreen.setImageResource(R.drawable.ic_baseline_done_24)
            binding.pBlue.setImageResource(0)
            binding.pYellow.setImageResource(0)
        }
        binding.pYellow.setOnClickListener {
            priority = "2"
            binding.pYellow.setImageResource(R.drawable.ic_baseline_done_24)
            binding.pBlue.setImageResource(0)
            binding.pGreen.setImageResource(0)
        }
        binding.pBlue.setOnClickListener {
            priority = "3"
            binding.pBlue.setImageResource(R.drawable.ic_baseline_done_24)
            binding.pGreen.setImageResource(0)
            binding.pYellow.setImageResource(0)
        }

        binding.btnSavedNoted.setOnClickListener{
            createNotes(it)
        }

        return binding.root
    }

    private fun createNotes(it: View?){
        val title = binding.editTitle.text.toString()
        val subTitle = binding.editSubtitle.text.toString()
        val notes = binding.editNotes.text.toString()

        val d = Date()
        val notesDate: CharSequence = DateFormat.format("MMMM d, yyyy ", d.time)

        val data=Notes(null, title= title, subtitle = subTitle, notes = notes, date = notesDate.toString(), priority)
        viewModel.addNotes(data)

        Toast.makeText(requireContext(), "Notes Created Successfully", Toast.LENGTH_SHORT).show()

        Navigation.findNavController(it!!).navigate(R.id.action_createNotesFragment_to_homeFragment)
    }

}