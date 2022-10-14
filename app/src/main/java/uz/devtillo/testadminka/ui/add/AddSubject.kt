package uz.devtillo.testadminka.ui.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import uz.devtillo.testadminka.R
import uz.devtillo.testadminka.databinding.FragmentAddSubjectBinding
import uz.devtillo.testadminka.model.Subject
import kotlin.random.Random

class AddSubject : Fragment() {

    private lateinit var binding: FragmentAddSubjectBinding
    private lateinit var firebacedatabase: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var subject: Subject

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddSubjectBinding.inflate(layoutInflater, container, false)

        firebacedatabase = FirebaseDatabase.getInstance()
        reference = firebacedatabase.getReference("testapp")

        binding.save.setOnClickListener {
            val author = binding.author.text.toString()
            val name = binding.name.text.toString()
            val count = binding.soniEt.text.toString()
            val description = binding.description.text.toString()
            val image = binding.image.text.toString()
            val random = Random

            if (author.isNotEmpty() && name.isNotEmpty() && count.isNotEmpty() && description.isNotEmpty() && image.isNotEmpty()) {
                subject =
                    Subject(
                        random.nextInt(),
                        name = name,
                        author = author,
                        count = count,
                        description = description
                    )
                reference.child(name).setValue(subject).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(requireContext(), "Successfully", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }
                }.addOnFailureListener {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Barcha ma`lumotlarni to`ldiring",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return binding.root
    }
}