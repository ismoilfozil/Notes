package uz.ismoil.notes.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.ismoil.notes.R
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.databinding.ScreenReadTrashBinding
import uz.ismoil.notes.viewmodels.ArchivedScreenViewModel
import uz.ismoil.notes.viewmodels.impl.ArchivedScreenViewModelImpl

@AndroidEntryPoint
class ReadArchiveScreen : Fragment(R.layout.screen_read_trash){

    private val viewModel:ArchivedScreenViewModel by viewModels<ArchivedScreenViewModelImpl>()
    private val binding by viewBinding(ScreenReadTrashBinding::bind)
    private val args:ReadArchiveScreenArgs by navArgs()
    private val navController by lazy (LazyThreadSafetyMode.NONE){ findNavController() }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.backLiveData.observe(this, backLiveDataObserver)
        loadViews()
    }

    private fun loadViews() {
        val data = args.note
        binding.titleText.text =  data.title
        binding.contentText.text =  data.text
        binding.date.text = data.timestamp

        binding.btnBack.setOnClickListener {
            navController.navigateUp()
        }

        binding.btnMore.setOnClickListener { view ->
            showMenu(view, data)
        }

    }

    private fun showMenu(v:View, data: NoteEntity) {
        val popupMenu = PopupMenu(context, v)
        val inflater = popupMenu.menuInflater.inflate(R.menu.archive_popup_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.delete -> {
                    viewModel.delete(data)
                    return@setOnMenuItemClickListener  true
                }
                R.id.unarchive -> {
                    viewModel.unarchive(data)
                    return@setOnMenuItemClickListener  true
                }

                else -> return@setOnMenuItemClickListener false
            }
        }
        popupMenu.show()
    }



    private val backLiveDataObserver = Observer<Unit>{
        navController.navigateUp()
    }
}