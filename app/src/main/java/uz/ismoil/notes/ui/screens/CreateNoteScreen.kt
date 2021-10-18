package uz.ismoil.notes.ui.screens

import android.R.attr
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.ismoil.notes.R
import uz.ismoil.notes.data.entities.NoteState
import uz.ismoil.notes.databinding.ScreenCreateNoteBinding
import uz.ismoil.notes.ui.dialogs.ColorBottomSheet
import uz.ismoil.notes.viewmodels.CreateNoteScreenViewModel
import uz.ismoil.notes.viewmodels.impl.CreateNoteScreenViewModelImpl
import java.util.*
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import java.sql.Date
import android.graphics.BitmapFactory

import android.R.attr.data
import android.net.Uri
import java.io.InputStream
import java.lang.Exception


@AndroidEntryPoint
class CreateNoteScreen : Fragment(R.layout.screen_create_note) {

    private val binding by viewBinding(ScreenCreateNoteBinding::bind)

    private val viewModel: CreateNoteScreenViewModel by viewModels<CreateNoteScreenViewModelImpl>()
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private var time:String? = null
    private val launch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){

        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.backLiveData.observe(viewLifecycleOwner, backObserver)
        loadViews()
    }

    private fun loadViews() {

        val date = Date()
        time = DateFormat.format("MMM d, yyy", date.time).toString()

        binding.date.text = time


        binding.btnPalette.setOnClickListener {
            val colorBottomSheet = ColorBottomSheet()
            colorBottomSheet.show(childFragmentManager, "COLOR")
        }

        binding.btnBack.setOnClickListener {
            saveNote()
        }
    }



    private fun saveNote() {
        val title = binding.titleText.text.toString()
        val content = binding.contentText.text.toString()
        val state = NoteState.ACTIVE
        viewModel.addNote(title, content, R.color.background1, state)
    }


    override fun onDestroyView() {
        super.onDestroyView()
   }


    private val backObserver = Observer<Unit> {
        navController.navigateUp()
    }
}


