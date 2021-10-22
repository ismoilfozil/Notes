package uz.ismoil.notes.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.ismoil.notes.data.entities.NoteEntity
import uz.ismoil.notes.databinding.ItemNoteBinding
import uz.ismoil.notes.models.Note
import java.util.*
import kotlin.collections.ArrayList

class NoteAdapter() : ListAdapter<NoteEntity, NoteAdapter.NoteViewHolder>(NoteComparator) {
    private var onClickListener: ((NoteEntity) -> Unit)? = null
    private var onClickLongListener: ((View, NoteEntity) -> Unit)? = null


    inner class NoteViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind() {
            val d = getItem(absoluteAdapterPosition)

            binding.titleText.text = d.title
            binding.contentText.text = d.text
            binding.textTime.text = d.timestamp

            binding.root.setOnClickListener {
                onClickListener?.invoke(d)
            }

            binding.root.setOnLongClickListener { v->
                onClickLongListener?.invoke(v, d)
                true
            }

        }

    }

    companion object NoteComparator : DiffUtil.ItemCallback<NoteEntity>() {
        override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem.id == newItem.id || oldItem.title == newItem.title || oldItem.pinned == newItem.pinned || oldItem.color == newItem.color
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NoteViewHolder(ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) = holder.bind()



    fun setOnItemClickListener(block: (NoteEntity) -> Unit) {
        onClickListener = block
    }

    fun setOnItemLongClickListener(block: (View, NoteEntity) -> Unit) {
        onClickLongListener = block
    }


}