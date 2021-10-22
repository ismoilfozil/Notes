package uz.ismoil.notes.ui.screens

import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import uz.ismoil.notes.R
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.databinding.ScreenArchiveBinding
import uz.ismoil.notes.ui.adapters.NoteAdapter
import uz.ismoil.notes.utils.visibleOrGone
import uz.ismoil.notes.viewmodels.ArchivedScreenViewModel
import uz.ismoil.notes.viewmodels.impl.ArchivedScreenViewModelImpl

@AndroidEntryPoint
class ArchivedScreen : Fragment(R.layout.screen_archive), SearchView.OnQueryTextListener {

    private val viewModel: ArchivedScreenViewModel by viewModels<ArchivedScreenViewModelImpl>()
    private val binding by viewBinding(ScreenArchiveBinding::bind)
    private lateinit var adapter: NoteAdapter
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.archive_toolbar_menu, menu)

        val search = menu.findItem(R.id.searchNote)
        val searchView = search?.actionView as? androidx.appcompat.widget.SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getArchiveNotes().observe(viewLifecycleOwner, getArchiveNotesObserver)
        viewModel.backLiveData.observe(viewLifecycleOwner, backObserver)
        loadViews()
    }

    private fun loadViews() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.mToolBar)

        adapter = NoteAdapter()
        binding.list.adapter = adapter
        if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.list.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        } else {
            binding.list.layoutManager = StaggeredGridLayoutManager(4, RecyclerView.VERTICAL)
        }

        adapter.setOnItemClickListener {
            navController.navigate(ArchivedScreenDirections.actionArchivedScreenToReadArchiveScreen(it))
        }

        adapter.setOnItemLongClickListener { view, data ->
            showMenu(view, data)
        }

    }


    private val backObserver = Observer<Unit>{
        navController.navigateUp()
    }

    private val getArchiveNotesObserver = Observer<List<NoteEntity>> { list ->
        val placeholder = binding.noteListPlaceholder
        if (list.isEmpty()) {
            adapter.submitList(list)
            placeholder.visibleOrGone(true)
        } else {
            adapter.submitList(list)
            placeholder.visibleOrGone(false)
        }
    }

    private fun showMenu(v: View, data: NoteEntity) {
        val popupMenu = PopupMenu(context, v)
        popupMenu.menuInflater.inflate(R.menu.archive_popup_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {
                    viewModel.delete(data)
                    return@setOnMenuItemClickListener true
                }
                R.id.unarchive -> {
                    Timber.tag("TTT").d("$data")
                    viewModel.unarchive(data)
                    return@setOnMenuItemClickListener true
                }

                else -> return@setOnMenuItemClickListener false
            }
        }
        popupMenu.show()
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchDatabase(query)
        }
        return true
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"

        viewModel.searchDatabase(searchQuery).observe(this, { list ->
            adapter.submitList(list)
        })
    }

}