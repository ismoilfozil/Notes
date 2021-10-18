package uz.ismoil.notes.ui.dialogs

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.ismoil.notes.R
import uz.ismoil.notes.databinding.ColorBottomSheetBinding
import uz.ismoil.notes.ui.adapters.ColorAdapter

class ColorBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.color_bottom_sheet, container, false)


    private val binding by viewBinding(ColorBottomSheetBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val list = listOf(
            R.color.background1,
            R.color.background2,
            R.color.background3,
            R.color.background1,
            R.color.background2,
            R.color.background3,
            R.color.background1,
            R.color.background2,
            R.color.background3,
            R.color.background1,
            R.color.background2,
            R.color.background3,
            R.color.background1,
            R.color.background2,
            R.color.background3,
            R.color.background1,
            R.color.background2,
            R.color.background3
        )

        binding.colorList.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.colorList.adapter = ColorAdapter(list)

    }

}