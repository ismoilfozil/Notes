package uz.ismoil.notes.ui.screens

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import uz.ismoil.notes.R
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.databinding.ScreenMainBinding
import uz.ismoil.notes.ui.adapters.NoteAdapter
import uz.ismoil.notes.utils.visibleOrGone
import uz.ismoil.notes.viewmodels.MainScreenViewModel
import uz.ismoil.notes.viewmodels.impl.MainScreenViewModelImpl

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.screen_main), SearchView.OnQueryTextListener {

    private val viewModel: MainScreenViewModel by viewModels<MainScreenViewModelImpl>()
    private val viewBinding by viewBinding(ScreenMainBinding::bind)
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private lateinit var adapter: NoteAdapter
//    private lateinit var appBarConfiguration: AppBarConfiguration
//    private lateinit var drawerLayout:DrawerLayout
//    private lateinit var navView:NavigationView


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
        viewModel.openCreateScreenLiveData.observe(this, openCreateScreenObserver)
        viewModel.openReadeScreenLiveData.observe(this, openReadScreenObserver)
//        viewModel.getAllNotes().observe(viewLifecycleOwner, getAllNotesObserver)
        viewModel.getActiveNotes().observe(viewLifecycleOwner, getActiveNotesObserver)
        setUpUI()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.archive_toolbar_menu, menu)

        val search = menu.findItem(R.id.searchNote)
        val searchView = search?.actionView as? androidx.appcompat.widget.SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setUpUI() {
        setUpNoteList()
        setupNavigation()

        adapter.setOnItemClickListener {
            viewModel.openReadeScreen(it)
        }

        adapter.setOnItemLongClickListener { view, data ->
            showMenu(view, data)
        }

        viewBinding.faButton.setOnClickListener {
            viewModel.openCreateScreen()
        }

    }

    private fun showMenu(v:View, data:NoteEntity) {
        val popupMenu = PopupMenu(context, v)
        val inflater = popupMenu.menuInflater.inflate(R.menu.main_popup_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.deleteNote -> {
                    viewModel.deleteNote(data)
                    return@setOnMenuItemClickListener  true
                }
                R.id.archiveNote -> {
                    viewModel.archiveNote(data)
                    return@setOnMenuItemClickListener  true
                }

                else -> return@setOnMenuItemClickListener false
            }
        }
        popupMenu.show()
    }

    private fun setupNavigation() {
        val activity = (requireActivity() as AppCompatActivity)
        activity.setSupportActionBar(viewBinding.mToolBar)

    }


    private fun setUpNoteList() {
        adapter = NoteAdapter()
        viewBinding.list.adapter = adapter
        if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            viewBinding.list.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        }else{
            viewBinding.list.layoutManager = StaggeredGridLayoutManager(4, RecyclerView.VERTICAL)
        }

    }

    private val getAllNotesObserver = Observer<List<NoteEntity>> { list ->
        val placeholder = viewBinding.noteListPlaceholder
        if (list.isEmpty()) {
            placeholder.visibleOrGone(true)
        } else {
            adapter.submitList(list)
            placeholder.visibleOrGone(false)
        }
    }

    private val getActiveNotesObserver = Observer<List<NoteEntity>>{ list ->
        val placeholder = viewBinding.noteListPlaceholder
        Timber.tag("TTT").d("$list")
        if (list.isEmpty()) {
            adapter.submitList(list)
            placeholder.visibleOrGone(true)
        } else {
            adapter.submitList(list)
            placeholder.visibleOrGone(false)
        }
    }

    private val openCreateScreenObserver = Observer<Unit> {
        navController.navigate(MainScreenDirections.actionMainScreenToCreateNoteScreen())
    }

    private val openReadScreenObserver = Observer<NoteEntity>{
        navController.navigate(MainScreenDirections.actionMainScreenToReadNoteScreen(it))
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