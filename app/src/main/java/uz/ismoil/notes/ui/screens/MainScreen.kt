package uz.ismoil.notes.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
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
class MainScreen : Fragment(R.layout.screen_main) {

    private val viewModel: MainScreenViewModel by viewModels<MainScreenViewModelImpl>()
    private val viewBinding by viewBinding(ScreenMainBinding::bind)
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private lateinit var adapter: NoteAdapter
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout:DrawerLayout
    private lateinit var navView:NavigationView


    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.openCreateScreenLiveData.observe(this, openCreateScreenObserver)
        viewModel.openReadeScreenLiveData.observe(this, openReadScreenObserver)
//        viewModel.getAllNotes().observe(viewLifecycleOwner, getAllNotesObserver)
        viewModel.getActiveNotes().observe(viewLifecycleOwner, getActiveNotesObserver)
        setUpUI()
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
        val inflater = popupMenu.menuInflater.inflate(R.menu.list_menu, popupMenu.menu)
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
        viewBinding.list.layoutManager = GridLayoutManager(context, 2)
    }


    private val openCreateScreenObserver = Observer<Unit> {
        navController.navigate(MainScreenDirections.actionMainScreenToCreateNoteScreen())
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

    private val openReadScreenObserver = Observer<NoteEntity>{
        navController.navigate(MainScreenDirections.actionMainScreenToReadNoteScreen(it))
    }


}