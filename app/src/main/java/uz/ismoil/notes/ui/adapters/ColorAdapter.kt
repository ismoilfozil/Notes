package uz.ismoil.notes.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.ismoil.notes.databinding.ItemColorBinding

class ColorAdapter(private val list: List<Int>) : RecyclerView.Adapter<ColorAdapter.ViewHolder>(){


    inner class ViewHolder(private val binding: ItemColorBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(){
               val color = list[absoluteAdapterPosition]
                binding.card.setBackgroundResource(color)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemColorBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    override fun getItemCount() = list.size


}