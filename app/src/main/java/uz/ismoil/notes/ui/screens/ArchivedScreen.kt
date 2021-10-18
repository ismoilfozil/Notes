package uz.ismoil.notes.ui.screens

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.ismoil.notes.R
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.databinding.ScreenArchiveBinding
import uz.ismoil.notes.ui.adapters.NoteAdapter
import uz.ismoil.notes.utils.visibleOrGone
import uz.ismoil.notes.viewmodels.ArchivedScreenViewModel
import uz.ismoil.notes.viewmodels.impl.ArchivedScreenViewModelImpl

@AndroidEntryPoint
class ArchivedScreen : Fragment(R.layout.screen_archive) {

    private val viewModel:ArchivedScreenViewModel by viewModels<ArchivedScreenViewModelImpl>()
    private val binding by viewBinding(ScreenArchiveBinding::bind)
    private lateinit var adapter: NoteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getArchiveNotes().observe(viewLifecycleOwner, getArchiveNotesObserver)

        loadViews()
    }

    private fun loadViews() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.mToolBar)

        adapter = NoteAdapter()
        binding.list.adapter = adapter
        binding.list.layoutManager = GridLayoutManager(context, 2)
    }

    private val getArchiveNotesObserver = Observer<List<NoteEntity>>{ list ->
        val placeholder = binding.noteListPlaceholder
        if (list.isEmpty()) {
            placeholder.visibleOrGone(true)
        } else {
            adapter.submitList(list)
            placeholder.visibleOrGone(false)
        }
    }

}