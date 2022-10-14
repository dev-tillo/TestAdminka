package uz.devtillo.testadminka.ui.adapter.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.*
import uz.devtillo.testadminka.databinding.FragmentQuestionUiBinding
import uz.devtillo.testadminka.model.Subject
import uz.devtillo.testadminka.model.question.Question
import uz.devtillo.testadminka.model.test.Test
import uz.devtillo.testadminka.ui.adapter.QuestionAdapter


class QuestionUi : Fragment() {

    private lateinit var binding: FragmentQuestionUiBinding
    private lateinit var firebacedatabase: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var dataAdapter: QuestionAdapter
    private lateinit var list: ArrayList<Question>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionUiBinding.inflate(layoutInflater, container, false)

        firebacedatabase = FirebaseDatabase.getInstance()
        reference = firebacedatabase.getReference("testapp")

        val data = arguments?.getSerializable("url") as Test
        val course = arguments?.getString("item")

        dataAdapter = QuestionAdapter(requireContext(), object : QuestionAdapter.onItemClicked {
            override fun onCliked(url: Question, position: Int) {
                Toast.makeText(requireContext(), "$url", Toast.LENGTH_SHORT).show()
            }
        })
        binding.rv.adapter = dataAdapter

        list = ArrayList()
        reference.child(course.toString()).child("list").child(data.subId).child("quizList")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    val children = snapshot.children
                    for (child in children) {
                        val value = child.getValue(Question::class.java)
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