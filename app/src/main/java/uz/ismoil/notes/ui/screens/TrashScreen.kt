package uz.ismoil.notes.ui.screens

import android.os.Bundle
import android.view.*
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
import uz.ismoil.notes.databinding.ScreenTrashBinding
import uz.ismoil.notes.ui.adapters.NoteAdapter
import uz.ismoil.notes.utils.visibleOrGone
import uz.ismoil.notes.viewmodels.ArchivedScreenViewModel
import uz.ismoil.notes.viewmodels.TrashScreenViewModel
import uz.ismoil.notes.viewmodels.impl.ArchivedScreenViewModelImpl
import uz.ismoil.notes.viewmodels.impl.TrashScreenViewModelImpl

@AndroidEntryPoint
class TrashScreen : Fragment(R.layout.screen_trash) {

    private val viewModel: TrashScreenViewModel by viewModels<TrashScreenViewModelImpl>()
    private val binding by viewBinding(ScreenTrashBinding::bind)
    private lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getTrashNotes().observe(viewLifecycleOwner, getTrashNotesObserver)

        loadViews()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.trash_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun loadViews() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.mToolBar)

        adapter = NoteAdapter()
        binding.list.adapter = adapter
        binding.list.layoutManager = GridLayoutManager(context, 2)
    }

    private val getTrashNotesObserver = Observer<List<NoteEntity>>{ list ->
        val placeholder = binding.noteListPlaceholder
        if (list.isEmpty()) {
            placeholder.visibleOrGone(true)
        } else {
            adapter.submitList(list)
            placeholder.visibleOrGone(false)
        }
    }
}