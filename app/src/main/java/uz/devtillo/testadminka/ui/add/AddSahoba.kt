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
import uz.devtillo.testadminka.databinding.FragmentAddSubjectBinding
import uz.devtillo.testadminka.model.Sahoba
import kotlin.random.Random

class AddSahoba : Fragment() {

    private lateinit var binding: FragmentAddSubjectBinding
    private lateinit var firebacedatabase: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var subject: Sahoba

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddSubjectBinding.inflate(layoutInflater, container, false)

        firebacedatabase = FirebaseDatabase.getInstance()
        reference = firebacedatabase.getReference("testapp")

        binding.save.setOnClickListener {
            val name = binding.name.text.toString()
            val born = binding.born.text.toString()
            val random = Random

            if (name.isNotEmpty() && born.isNotEmpty()) {
                subject =
                    Sahoba(
                        random.nextInt(),
                        name = name,
                        born = born
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