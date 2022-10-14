package uz.devtillo.testadminka.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.devtillo.testadminka.databinding.ItemQuestionBinding
import uz.devtillo.testadminka.databinding.ItemRvBinding
import uz.devtillo.testadminka.model.question.Question
import uz.devtillo.testadminka.model.test.Test

class QuestionAdapter(var context: Context, var listener: onItemClicked) :
    ListAdapter<Question, QuestionAdapter.Vh>(MyDiffUtil()) {

    class MyDiffUtil :
        DiffUtil.ItemCallback<Question>() {
        override fun areItemsTheSame(
            oldItem: Question,
            newItem: Question,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: Question,
            newItem: Question,
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class Vh(var itemImageBinding: ItemQuestionBinding) :
        RecyclerView.ViewHolder(itemImageBinding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemQuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    interface onItemClicked {
        fun onCliked(url: Question, position: Int)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val item = getItem(position)
        holder.itemImageBinding.apply {
            try {

                question.text = item.question
                correct.text = item.correct
                answer1.text = item.answer1
                answer2.text = item.answer2

                item1.setOnClickListener {
                    listener.onCliked(item, position)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}