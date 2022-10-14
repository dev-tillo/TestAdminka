package uz.devtillo.testadminka.ui.adapter.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.*
import uz.devtillo.testadminka.R
import uz.devtillo.testadminka.databinding.FragmentTestsBinding
import uz.devtillo.testadminka.model.Subject
import uz.devtillo.testadminka.model.test.Test
import uz.devtillo.testadminka.ui.adapter.TestAdapter

class TestsFragment : Fragment() {

    private lateinit var firebacedatabase: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var dataAdapter: TestAdapter
    private lateinit var binding: FragmentTestsBinding
    private lateinit var list: ArrayList<Test>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTestsBinding.inflate(layoutInflater, container, false)

        firebacedatabase = FirebaseDatabase.getInstance()
        reference = firebacedatabase.getReference("testapp")

        val data = arguments?.getSerializable("item") as Subject

        dataAdapter = TestAdapter(requireContext(), object : TestAdapter.onItemClicked {
            override fun onCliked(url: Test, position: Int) {
                val bundle = Bundle()
                bundle.putString("item", data.name)
                bundle.putSerializable("url", url)
                findNavController().navigate(R.id.questionUi, bundle)
            }
        })
        binding.rv.adapter = dataAdapter

        list = ArrayList()

        reference.child(data.name!!)
            .child("list")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val children = snapshot.children
                    for (child in children) {
                        val value = child.getValue(Test::class.java)
                        if (value != null) {
                            list.add(value)
                            dataAdapter.submitList(list)
                            binding.rv.adapter = dataAdapter
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })


        return binding.root
    }
}