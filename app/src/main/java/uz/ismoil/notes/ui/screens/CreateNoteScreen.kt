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
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.ldralighieri.corbind.widget.textChanges
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.viewmodels.MainScreenViewModel
import uz.ismoil.notes.viewmodels.impl.MainScreenViewModelImpl
import java.io.InputStream
import java.lang.Exception


@AndroidEntryPoint
class CreateNoteScreen : Fragment(R.layout.screen_create_note) {

    private val binding by viewBinding(ScreenCreateNoteBinding::bind)

    private val viewModel: CreateNoteScreenViewModel by viewModels<CreateNoteScreenViewModelImpl>()
    private val mainViewModel: MainScreenViewModel by viewModels<MainScreenViewModelImpl>()

    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private lateinit var time:String
    private var isPinned = false

//    private val launch =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//            if (it.resultCode == Activity.RESULT_OK) {
//
//            }
//        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.backLiveData.observe(viewLifecycleOwner, backObserver)
        loadViews()
    }

    private fun loadViews() {

        val date = Date()
        time = DateFormat.format("MMM d, HH:mm", date.time).toString()

        binding.date.text = time



//        binding.btnPalette.setOnClickListener {
//            val colorBottomSheet = ColorBottomSheet()
//            colorBottomSheet.show(childFragmentManager, "COLOR")
//        }

        binding.btnBack.setOnClickListener {
            saveNote(NoteState.ACTIVE)
        }

        binding.btnMenu.setOnClickListener { view ->
            showMenu(view)
        }


        binding.btnPin.setOnClickListener {
            isPinned = !isPinned
            val image = it as ImageView
            if (isPinned) image.setImageResource(R.drawable.ic_push_pin)
            else image.setImageResource(R.drawable.ic_push_unpin)
        }

        lifecycleScope.launch {
            binding.titleText.textChanges {
                if (binding.titleText.text.toString()
                        .isEmpty() && binding.contentText.text.toString().isEmpty()
                ) {
                    binding.btnBack.setImageResource(R.drawable.ic_arrow_back)
                } else {
                    binding.btnBack.setImageResource(R.drawable.ic_check)
                }
            }

            binding.contentText.textChanges {
                if (binding.titleText.text.toString()
                        .isEmpty() && binding.contentText.text.toString().isEmpty()
                ) {
                    binding.btnBack.setImageResource(R.drawable.ic_arrow_back)
                } else {
                    binding.btnBack.setImageResource(R.drawable.ic_check)
                }
            }
        }
    }


    private fun showMenu(v: View) {
        val popupMenu = PopupMenu(context, v)
        popupMenu.menuInflater.inflate(R.menu.main_popup_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.deleteNote -> {
                    saveNote(NoteState.TRASH)
                    return@setOnMenuItemClickListener true
                }
                R.id.archiveNote -> {
                    saveNote(NoteState.ARCHIVED)
                    return@setOnMenuItemClickListener true
                }

                else -> return@setOnMenuItemClickListener false
            }
        }
        popupMenu.show()
    }


    private fun saveNote(state: NoteState) {
        val title = binding.titleText.text.toString()
        val content = binding.contentText.text.toString()
        if (title.isEmpty() && content.isEmpty()) {
            navController.navigateUp()
        } else {
            viewModel.addNote(title, content, time ,R.color.background1, state, isPinned)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }


    private val backObserver = Observer<Unit> {
        navController.navigateUp()
    }
}


