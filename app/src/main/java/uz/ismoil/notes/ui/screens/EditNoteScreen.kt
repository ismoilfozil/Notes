package uz.ismoil.notes.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.ldralighieri.corbind.widget.textChanges
import timber.log.Timber
import uz.ismoil.notes.R
import uz.ismoil.notes.data.entities.NoteState
import uz.ismoil.notes.databinding.ScreenEditNoteBinding
import uz.ismoil.notes.viewmodels.EditNoteScreenViewModel
import uz.ismoil.notes.viewmodels.impl.EditNoteScreenViewModelImpl

@AndroidEntryPoint
class EditNoteScreen : Fragment(R.layout.screen_edit_note) {

    private val binding by viewBinding(ScreenEditNoteBinding::bind)

    private val viewModel: EditNoteScreenViewModel by viewModels<EditNoteScreenViewModelImpl>()
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private val args: EditNoteScreenArgs by navArgs()
//    private var toolbar: Toolbar? = null

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.backLiveData.observe(this, backObserver)
        loadViews()
    }

    private fun loadViews() {
        Timber.tag("TTT").d("LoadViews")
//        toolbar = requireActivity().findViewById(R.id.mToolBar)
//        toolbar?.setBackgroundColor(resources.getColor(R.color.background1))
        binding.titleText.setText(args.noteEntity.title)
        binding.contentText.setText(args.noteEntity.text)
        binding.date.text = args.noteEntity.timestamp

        binding.btnBack.setOnClickListener {
            saveNote(NoteState.ACTIVE)
        }


        binding.btnMenu.setOnClickListener {
            showMenu(it)
        }

        lifecycleScope.launch {
            binding.titleText.textChanges {
                binding.btnBack.setImageResource(R.drawable.ic_check)
            }

            binding.contentText.textChanges {
                binding.btnBack.setImageResource(R.drawable.ic_check)
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

//
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.create_menu, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.save -> {
//                saveNote()
//                true
//            }
//
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
//
    private fun saveNote(state:NoteState) {
        val note = args.noteEntity
        val title = binding.titleText.text.toString()
        val content = binding.contentText.text.toString()
        viewModel.updateNote(note.id, title, content, args.noteEntity.timestamp, R.color.background1, state)
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        toolbar?.setBackgroundColor(resources.getColor(R.color.white))
//        toolbar = null
    }


    private val backObserver = Observer<Unit> {
        Timber.tag("TTT").d("Navigate up")
        navController.navigateUp()
    }
}


