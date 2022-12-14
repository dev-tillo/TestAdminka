package uz.devtillo.testadminka.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import uz.devtillo.testadminka.R
import uz.devtillo.testadminka.databinding.FragmentHomeBinding

class Home : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        with(binding) {
            addSahoba.setOnClickListener {
                findNavController().navigate(R.id.addSubject2)
            }
            addWhatDid.setOnClickListener {
                findNavController().navigate(R.id.addTest)
            }
        }
        return binding.root
    }
}