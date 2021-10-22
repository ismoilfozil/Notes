package uz.ismoil.notes.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
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
import uz.ismoil.notes.viewmodels.MainScreenViewModel
import uz.ismoil.notes.viewmodels.ReadNoteScreenViewModel
import uz.ismoil.notes.viewmodels.impl.MainScreenViewModelImpl
import uz.ismoil.notes.viewmodels.impl.ReadNoteScreenViewModelImpl

@AndroidEntryPoint
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
        viewModel.backLiveData.observe(this, backObserver)

        loadViews()
    }

    private fun loadViews()  = binding.apply{
        val data = args.noteEntity
//        toolbar = requireActivity().findViewById(R.id.mToolBar)
//        toolbar?.setBackgroundResource(data.color)
        root.setBackgroundResource(data.color)
        date.text = data.timestamp
        titleText.text = data.title
        contentText.text = data.text

        btnBack.setOnClickListener {
            navController.navigateUp()
        }

        btnMore.setOnClickListener {
            showMenu(it, data)
        }

        btnEdit.setOnClickListener {
            viewModel.openEditScreen(data)
        }

    }

    private val backObserver = Observer<Unit>{
        navController.navigateUp()
    }

    private fun showMenu(v:View, data:NoteEntity) {
        val popupMenu = PopupMenu(context, v)
        val inflater = popupMenu.menuInflater.inflate(R.menu.main_popup_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.deleteNote -> {
                    viewModel.delete(data)
                    return@setOnMenuItemClickListener  true
                }
                R.id.archiveNote -> {
                    viewModel.archive(data)
                    return@setOnMenuItemClickListener  true
                }

                else -> return@setOnMenuItemClickListener false
            }
        }
        popupMenu.show()
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