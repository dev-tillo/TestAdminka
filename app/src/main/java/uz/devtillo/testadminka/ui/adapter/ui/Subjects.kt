package uz.devtillo.testadminka.ui.adapter.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.*
import uz.devtillo.testadminka.R
import uz.devtillo.testadminka.databinding.FragmentSubjectsBinding
import uz.devtillo.testadminka.model.Subject
import uz.devtillo.testadminka.ui.adapter.SubjectAdapter

class Subjects : Fragment() {

    private lateinit var binding: FragmentSubjectsBinding
    private lateinit var firebacedatabase: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var dataAdapter: SubjectAdapter
    private lateinit var list: ArrayList<Subject>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubjectsBinding.inflate(layoutInflater, container, false)

        firebacedatabase = FirebaseDatabase.getInstance()
        reference = firebacedatabase.getReference("testapp")

        dataAdapter = SubjectAdapter(requireContext(), object : SubjectAdapter.onItemClicked {
            override fun onCliked(url: Subject, position: Int) {
                var bundle = Bundle()
                bundle.putSerializable("item", url)
                findNavController().navigate(R.id.tests_fragment, bundle)
            }
        })
        binding.rv.adapter = dataAdapter

        list = ArrayList()

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                val children = snapshot.children
                for (child in children) {
                    val value = child.getValue(Subject::class.java)
                    if (value != null) {
                        list.add(value)
                        dataAdapter.submitList(list)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        return binding.root
    }
}