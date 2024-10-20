package demo.solution.ProjectApp.project.Quize.UI

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import demo.solution.ProjectApp.R
import demo.solution.ProjectApp.project.Quize.UI.Adapter.MCQAdapter
import demo.solution.ProjectApp.project.Quize.UI.Question
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MCQFragment : Fragment() {

    private lateinit var recyclerViewMcqQuestion: RecyclerView
    private lateinit var mcqAdapter: MCQAdapter
    private lateinit var iconApp: ImageView
    private lateinit var previousButton: Button
    private lateinit var nextButton: Button
    private lateinit var submitButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_m_c_q, container, false)
        init(view)
        testFreeQuestionRetrofit("128", "4")
        return view
    }

    private fun testFreeQuestionRetrofit(userId: String, TestId: String) {
        // Make the API call to get the test questions
        val call = RetrofitInstance.apiService.getFreeTestQuestion(userId, TestId)

        call.enqueue(object : Callback<FreeTestQuestionResponse> {
            override fun onResponse(call: Call<FreeTestQuestionResponse>, response: Response<FreeTestQuestionResponse>) {
                try {
                    if (response.isSuccessful && response.body() != null) {
                        val freeTestQuestionResponse = response.body()
                        val questions = freeTestQuestionResponse?.questions

                        if (questions != null) {
                            mcqAdapter = MCQAdapter(questions)

                            recyclerViewMcqQuestion.adapter = mcqAdapter
                            mcqAdapter.notifyDataSetChanged()
                            Toast.makeText(requireContext(), "Questions loaded successfully", Toast.LENGTH_SHORT).show()
                            Log.d("MCQFragment", "Response data: ${freeTestQuestionResponse}")
                        } else {
                            Log.e("MCQFragment", "No questions available")
                            Toast.makeText(requireContext(), "No questions available", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        Log.e("MCQFragment", "Failed to get test questions: ${response.message()}")
                        Toast.makeText(requireContext(), "Failed to get test questions: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("MCQFragment", "Failed to get test questions: ${e.message}")
                    Toast.makeText(requireContext(), "Failed to get test questions: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<FreeTestQuestionResponse>, t: Throwable) {
                Log.e("MCQFragment", "Network request failed", t)
                Toast.makeText(requireContext(), "Network request failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun init(view: View?) {
        recyclerViewMcqQuestion = view?.findViewById(R.id.recycler_view_mcq_question) as RecyclerView
        recyclerViewMcqQuestion.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        iconApp = view.findViewById(R.id.icon_app) as ImageView
        previousButton = view.findViewById(R.id.previousButton) as Button
        nextButton = view.findViewById(R.id.nextButton) as Button
        submitButton = view.findViewById(R.id.submitButton) as Button
    }
}
