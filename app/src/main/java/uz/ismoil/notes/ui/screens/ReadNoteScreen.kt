package uz.ismoil.notes.ui.screens

import android.annotation.SuppressLint
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
import uz.ismoil.notes.databinding.ScreenReadNoteBinding
import uz.ismoil.notes.viewmodels.ReadNoteScreenViewModel
import uz.ismoil.notes.viewmodels.impl.ReadNoteScreenViewModelImpl

class ReadNoteScreen : Fragment(R.layout.screen_read_note) {


    private val binding by viewBinding(ScreenReadNoteBinding::bind)
    private val viewModel: ReadNoteScreenViewModel by viewModels<ReadNoteScreenViewModelImpl>()
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private val args: ReadNoteScreenArgs by navArgs()
    private var toolbar: Toolbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.openEditScreenLiveData.observe(this, openEditScreenObserver)

        loadViews()
    }

    private fun loadViews() {
        val data = args.noteEntity
//        toolbar = requireActivity().findViewById(R.id.mToolBar)
//        toolbar?.setBackgroundResource(data.color)
        binding.root.setBackgroundResource(data.color)

        binding.titleText.text = data.title
        binding.contentText.text = data.text

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.read_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit_note -> {
                viewModel.openEditScreen(args.noteEntity)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private val openEditScreenObserver = Observer<NoteEntity>{
        navController.navigate(ReadNoteScreenDirections.actionReadNoteScreenToEditNoteScreen(it))
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        toolbar?.setBackgroundColor(resources.getColor(R.color.white))
//        toolbar = null
    }
}