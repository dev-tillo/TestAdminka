package uz.devtillo.testadminka.ui.adapter.spinner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import uz.devtillo.testadminka.databinding.ItemSpinnerBinding
import uz.devtillo.testadminka.model.Sahoba

class Spinner_Adapter_Name(var list: List<Sahoba>) : BaseAdapter() {

    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Sahoba = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ItemSpinnerBinding = if (convertView == null) {
            ItemSpinnerBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        } else {
            ItemSpinnerBinding.bind(convertView)
        }

        binding.txt.text = list[position].name

        return binding.root
    }

}