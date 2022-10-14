package uz.devtillo.testadminka.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.devtillo.testadminka.databinding.ItemRvBinding
import uz.devtillo.testadminka.model.test.Test

class TestAdapter(var context: Context, var listener: onItemClicked) :
    ListAdapter<Test, TestAdapter.Vh>(MyDiffUtil()) {

    class MyDiffUtil :
        DiffUtil.ItemCallback<Test>() {
        override fun areItemsTheSame(
            oldItem: Test,
            newItem: Test,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: Test,
            newItem: Test,
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
        fun onCliked(url: Test, position: Int)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val item = getItem(position)
        holder.itemImageBinding.apply {
            try {

                name.text = item.testName
                desc.text = item.subId

                item1.setOnClickListener {
                    listener.onCliked(item, position)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}