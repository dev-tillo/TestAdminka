package uz.devtillo.testadminka.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.devtillo.testadminka.databinding.ItemRvBinding
import uz.devtillo.testadminka.model.Subject

class SubjectAdapter(var context: Context, var listener: onItemClicked) :
    ListAdapter<Subject, SubjectAdapter.Vh>(MyDiffUtil()) {

    class MyDiffUtil :
        DiffUtil.ItemCallback<Subject>() {
        override fun areItemsTheSame(
            oldItem: Subject,
            newItem: Subject,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: Subject,
            newItem: Subject,
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class Vh(var itemImageBinding: ItemRvBinding) :
        RecyclerView.ViewHolder(itemImageBinding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    interface onItemClicked {
        fun onCliked(url: Subject, position: Int)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val item = getItem(position)
        holder.itemImageBinding.apply {
            try {

                name.text = item.name
                desc.text = item.description

                item1.setOnClickListener {
                    listener.onCliked(item, position)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}