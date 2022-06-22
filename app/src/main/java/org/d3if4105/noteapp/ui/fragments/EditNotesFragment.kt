package org.d3if4105.noteapp.ui.fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.d3if4105.noteapp.R
import org.d3if4105.noteapp.databinding.FragmentEditNotesBinding
import org.d3if4105.noteapp.model.Notes
import org.d3if4105.noteapp.viewModel.NotesViewModel
import java.util.*

class EditNotesFragment : Fragment() {

    val notesOld by navArgs<EditNotesFragmentArgs>()
    lateinit var binding:FragmentEditNotesBinding
    var priority: String = "1"
    val viewModel : NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditNotesBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)

        binding.editTitle.setText(notesOld.data.title)
        binding.editSubtitle.setText(notesOld.data.subtitle)
        binding.editNotes.setText(notesOld.data.notes)

        when(notesOld.data.priority)
        {
            "1"->{
                priority = "1"
                binding.pGreen.setImageResource(R.drawable.ic_baseline_done_24)
                binding.pBlue.setImageResource(0)
                binding.pYellow.setImageResource(0)
            }
            "2"->{
                priority = "2"
                binding.pYellow.setImageResource(R.drawable.ic_baseline_done_24)
                binding.pBlue.setImageResource(0)
                binding.pGreen.setImageResource(0)
            }
            "3"->{
                priority = "3"
                binding.pBlue.setImageResource(R.drawable.ic_baseline_done_24)
                binding.pGreen.setImageResource(0)
                binding.pYellow.setImageResource(0)
            }
        }

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

        binding.btnEditSavedNoted.setOnClickListener {
            updateNotes(it)
        }

        return binding.root
    }

    private fun updateNotes(it: View?){

        val title = binding.editTitle.text.toString()
        val subTitle = binding.editSubtitle.text.toString()
        val notes = binding.editNotes.text.toString()

        val d = Date()
        val notesDate: CharSequence = DateFormat.format("MMMM d, yyyy ", d.time)

        val data= Notes(notesOld.data.id, title= title, subtitle = subTitle, notes = notes, date = notesDate.toString(), priority)
        Log.e("@@@@@", "updateNotes: Title : $title Subtitle : $subTitle notes : $notes" )
        
        viewModel.updateNotes(data)

        Toast.makeText(requireContext(), "Notes Update Successfully", Toast.LENGTH_SHORT).show()

        Navigation.findNavController(it!!).navigate(R.id.action_editNotesFragment_to_homeFragment)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete)
        {
            val bottomSheet: BottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
            bottomSheet.setContentView(R.layout.dialog_delete)

            val textviewYes = bottomSheet.findViewById<TextView>(R.id.dialog_yes)
            val textviewNo = bottomSheet.findViewById<TextView>(R.id.dialog_no)

            textviewYes?.setOnClickListener {
                viewModel.deleteNotes(notesOld.data.id!!)
                findNavController().popBackStack()
                bottomSheet.dismiss()
            }

            textviewNo?.setOnClickListener {
                bottomSheet.dismiss()
            }
            bottomSheet.show()
        }
        return super.onOptionsItemSelected(item)
    }
}