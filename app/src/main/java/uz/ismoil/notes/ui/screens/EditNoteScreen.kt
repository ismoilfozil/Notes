package uz.ismoil.notes.ui.screens

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import uz.ismoil.notes.R
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.data.entities.NoteState
import uz.ismoil.notes.databinding.ScreenCreateNoteBinding
import uz.ismoil.notes.databinding.ScreenEditNoteBinding
import uz.ismoil.notes.viewmodels.CreateNoteScreenViewModel
import uz.ismoil.notes.viewmodels.EditNoteScreenViewModel
import uz.ismoil.notes.viewmodels.impl.CreateNoteScreenViewModelImpl
import uz.ismoil.notes.viewmodels.impl.EditNoteScreenViewModelImpl

@AndroidEntryPoint
class EditNoteScreen : Fragment(R.layout.screen_edit_note) {

    private val binding by viewBinding(ScreenEditNoteBinding::bind)

    private val viewModel: EditNoteScreenViewModel by viewModels<EditNoteScreenViewModelImpl>()
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private val args:EditNoteScreenArgs by navArgs()
    private var toolbar: Toolbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.backLiveData.observe(viewLifecycleOwner, backObserver)
        loadViews()
    }

    private fun loadViews() {
        Timber.tag("TTT").d("LoadViews")
//        toolbar = requireActivity().findViewById(R.id.mToolBar)
//        toolbar?.setBackgroundColor(resources.getColor(R.color.background1))
        binding.titleText.setText(args.noteEntity.title)
        binding.contentText.setText(args.noteEntity.text)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.create_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.save ->{
                saveNote()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveNote() {
        val note = args.noteEntity
        val title = binding.titleText.text.toString()
        val content = binding.contentText.text.toString()
        val state = NoteState.ACTIVE
        viewModel.updateNote(note.id, title, content, R.color.background1, state)
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        toolbar?.setBackgroundColor(resources.getColor(R.color.white))
//        toolbar = null
    }


    private val backObserver = Observer<Unit> {
        navController.navigateUp()
    }
}


