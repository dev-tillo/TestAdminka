package uz.devtillo.testadminka.ui.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SpinnerAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.*
import uz.devtillo.testadminka.databinding.FragmentAddTestBinding
import uz.devtillo.testadminka.model.Subject
import uz.devtillo.testadminka.model.test.Test
import uz.devtillo.testadminka.ui.adapter.spinner.Spinner_Adapter_Name
import kotlin.random.Random

class AddTest : Fragment() {

    private lateinit var binding: FragmentAddTestBinding
    private lateinit var firebacedatabase: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var test: Test
    private lateinit var spinnerAdapter: SpinnerAdapter
    private lateinit var list: ArrayList<Subject>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTestBinding.inflate(layoutInflater, container, false)

        firebacedatabase = FirebaseDatabase.getInstance()
        reference = firebacedatabase.getReference("testapp")
        loadData()

        spinnerAdapter = Spinner_Adapter_Name(list)

        binding.save.setOnClickListener {
            val name = list[binding.name.selectedItemPosition]
            val nomi = binding.nomiTest.text.toString()
            val level = binding.level.text.toString()
            val subid = binding.subjectid.text.toString()

            if (name.name?.isNotEmpty()!! && nomi.isNotEmpty() && level.isNotEmpty() && subid.isNotEmpty()) {
                test = Test(
                    id = Random.nextInt(),
                    testName = name.name!!,
                    subId = subid,
                    count = nomi,
                    level = level)

                reference
                    .child(name.name!!)
                    .child("list")
                    .child(level)
                    .setValue(test).addOnCompleteListener {
                        Toast.makeText(requireContext(), "Successfully", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }
            }
        }

        return binding.root
    }

    private fun loadData() {
        list = ArrayList()

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                val children = snapshot.children
                for (child in children) {
                    val value = child.getValue(Subject::class.java)
                    if (value != null) {
                        list.add(value)
                        binding.name.adapter = spinnerAdapter
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}