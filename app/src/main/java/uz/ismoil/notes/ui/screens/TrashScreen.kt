package uz.ismoil.notes.ui.screens

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
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
import uz.ismoil.notes.R
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.databinding.ScreenTrashBinding
import uz.ismoil.notes.ui.adapters.NoteAdapter
import uz.ismoil.notes.utils.visibleOrGone
import uz.ismoil.notes.viewmodels.TrashScreenViewModel
import uz.ismoil.notes.viewmodels.impl.TrashScreenViewModelImpl

@AndroidEntryPoint
class TrashScreen : Fragment(R.layout.screen_trash) , SearchView.OnQueryTextListener {

    private val viewModel: TrashScreenViewModel by viewModels<TrashScreenViewModelImpl>()
    private val binding by viewBinding(ScreenTrashBinding::bind)
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

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.backLiveData.observe(this, backLiveDataObserver)
        viewModel.getTrashNotes().observe(viewLifecycleOwner, getTrashNotesObserver)

        loadViews()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.trash_toolbar_menu, menu)

        val search = menu.findItem(R.id.searchNote)
        val searchView = search?.actionView as? androidx.appcompat.widget.SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.clear_notes -> {
                if (adapter.currentList.isNotEmpty()) { showDialog() }
                true
            }


            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.apply {
            setMessage("Are you want to delete all notes?")
            setPositiveButton("Yes") { dialog, _ ->
                viewModel.deleteAll()
                dialog.dismiss()
            }
            setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
        }

        alertDialog.show()
    }

    private val backLiveDataObserver = Observer<Unit> {
        findNavController().navigateUp()
    }


    private fun loadViews() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.mToolBar)

        adapter = NoteAdapter()
        binding.list.adapter = adapter

        if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            binding.list.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        }else{
            binding.list.layoutManager = StaggeredGridLayoutManager(4, RecyclerView.VERTICAL)
        }


        adapter.setOnItemClickListener {
            navController.navigate(TrashScreenDirections.actionTrashScreenToReadTrashScreen(it))
        }

        adapter.setOnItemLongClickListener { view, data ->
            showMenu(view, data)
        }

    }


    private fun showMenu(v:View, data:NoteEntity) {
        val popupMenu = PopupMenu(context, v)
        popupMenu.menuInflater.inflate(R.menu.trash_popup_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.delete -> {
                    viewModel.delete(data)
                    return@setOnMenuItemClickListener  true
                }
                R.id.restore -> {
                    viewModel.restore(data)
                    return@setOnMenuItemClickListener  true
                }

                else -> return@setOnMenuItemClickListener false
            }
        }
        popupMenu.show()
    }


    private val getTrashNotesObserver = Observer<List<NoteEntity>> { list ->
        val placeholder = binding.noteListPlaceholder
        if (list.isEmpty()) {
            adapter.submitList(list)
            placeholder.visibleOrGone(true)
        } else {
            adapter.submitList(list)
            placeholder.visibleOrGone(false)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null){
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