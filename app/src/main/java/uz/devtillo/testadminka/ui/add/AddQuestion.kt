package uz.devtillo.testadminka.ui.add

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import okio.ArrayIndexOutOfBoundsException
import uz.devtillo.testadminka.databinding.FragmentAddQuestionBinding
import uz.devtillo.testadminka.model.Subject
import uz.devtillo.testadminka.model.question.Question
import uz.devtillo.testadminka.model.test.Test
import uz.devtillo.testadminka.ui.adapter.spinner.SpinnerAdapterNameTest
import uz.devtillo.testadminka.ui.adapter.spinner.Spinner_Adapter_Name
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

class AddQuestion : Fragment(), CoroutineScope {

    private lateinit var binding: FragmentAddQuestionBinding
    private lateinit var firebacedatabase: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var question: Question
    private lateinit var listSubject: ArrayList<Subject>
    private lateinit var listTest: ArrayList<Test>
    private lateinit var spinnerAdapter: Spinner_Adapter_Name
    private lateinit var spinnerAdapter1: SpinnerAdapterNameTest
    private lateinit var selectedValue: String
    private lateinit var selectedTestValue: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddQuestionBinding.inflate(layoutInflater, container, false)

        firebacedatabase = FirebaseDatabase.getInstance()
        reference = firebacedatabase.getReference("testapp")
        listTest = ArrayList()

        loadSubject()
        spinnerAdapter = Spinner_Adapter_Name(listSubject)
        spinnerAdapter1 = SpinnerAdapterNameTest(listTest)

        binding.testName.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View,
                position: Int,
                l: Long
            ) {
                selectedValue = listSubject[position].name!!
                loadTest(selectedValue)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                try {
                    reference.child("beginner").child("list")
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                listTest.clear()
                                val children = snapshot.children
                                for (child in children) {
                                    val value = child.getValue(Test::class.java)
                                    if (value != null) {
                                        listTest.add(value)
                                        binding.questionName.adapter = spinnerAdapter1
                                    }
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {

                            }
                        })
                } catch (e: ArrayIndexOutOfBoundsException) {
                    e.printStackTrace()
                }
            }
        }

        binding.questionName.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedTestValue = listTest[position].level
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        binding.save.setOnClickListener {
            try {
                val question_tv = binding.question.text.toString()
                val answer1 = binding.answer.text.toString()
                val answer2 = binding.answer1.text.toString()
                val answer3 = binding.answer2.text.toString()
                val correct = binding.correct.text.toString()

                question =
                    Question(
                        id = Random.nextInt(),
                        question = question_tv,
                        answer1 = answer1,
                        answer2 = answer2,
                        answer3 = answer3,
                        correct = correct
                    )
                reference
                    .child(selectedValue)
                    .child("list")
                    .child(selectedTestValue)
                    .child("quizList")
                    .child(question_tv)
                    .setValue(question).addOnCompleteListener {
                        Toast.makeText(requireContext(), "Successfully", Toast.LENGTH_SHORT)
                            .show()
                        findNavController().popBackStack()
                    }
            } catch (e: IndexOutOfBoundsException) {
                e.printStackTrace()
            }
        }

        return binding.root
    }

    private fun loadTest(name: String) {
        reference.child(name)
            .child("list")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    listTest.clear()
                    val children = snapshot.children
                    for (child in children) {
                        val value = child.getValue(Test::class.java)
                        if (value != null) {
                            listTest.add(value)
                            binding.questionName.adapter = spinnerAdapter1
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    private fun loadSubject() {
        listSubject = ArrayList()

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listSubject.clear()
                val children = snapshot.children
                for (child in children) {
                    val value = child.getValue(Subject::class.java)
                    if (value != null) {
                        listSubject.add(value)
                        binding.testName.adapter = spinnerAdapter
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()
}