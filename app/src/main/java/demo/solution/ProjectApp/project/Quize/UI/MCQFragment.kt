package demo.solution.ProjectApp.project.Quize.UI

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import demo.solution.ProjectApp.R
import demo.solution.ProjectApp.project.Quize.UI.Adapter.MCQAdapter
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
    private var currentQuestionIndex: Int = 0
    private var userAnswers: MutableList<String?> = mutableListOf() // Initialize userAnswers
    private var countDownTimer: CountDownTimer? = null
    private var startTime: Long = 0 // Track the start time for the quiz

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_m_c_q, container, false)
        init(view)
        testFreeQuestionRetrofit("128", "4")
        return view
    }

    private fun testFreeQuestionRetrofit(userId: String, TestId: String) {
        val call = RetrofitInstance.apiService.getFreeTestQuestion(userId, TestId)

        call.enqueue(object : Callback<FreeTestQuestionResponse> {
            override fun onResponse(call: Call<FreeTestQuestionResponse>, response: Response<FreeTestQuestionResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val freeTestQuestionResponse = response.body()!!
                    mcqAdapter = MCQAdapter(freeTestQuestionResponse, requireContext()) // Corrected line
                    recyclerViewMcqQuestion.adapter = mcqAdapter
                    mcqAdapter.notifyDataSetChanged()
                    setpriview(freeTestQuestionResponse)

                    Toast.makeText(requireContext(), "Questions loaded successfully", Toast.LENGTH_SHORT).show()
                    Log.d("MCQFragment", "Response data: ${freeTestQuestionResponse}")
                } else {
                    Log.e("MCQFragment", "Failed to get test questions: ${response.message()}")
                    Toast.makeText(requireContext(), "Failed to get test questions: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<FreeTestQuestionResponse>, t: Throwable) {
                Log.e("MCQFragment", "Network request failed", t)
                Toast.makeText(requireContext(), "Network request failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setpriview(freeTestQuestionResponse: FreeTestQuestionResponse) {
        setSnapHelper()
        setClickListener(freeTestQuestionResponse)
        val adapter = context?.let { MCQAdapter(freeTestQuestionResponse, it) }
    }





    private fun setClickListener(freeTestQuestionResponse: FreeTestQuestionResponse) {
        previousButton.setOnClickListener {
            if (currentQuestionIndex > 0) {
                recyclerViewMcqQuestion.smoothScrollToPosition(currentQuestionIndex - 1)
            } else {
                Toast.makeText(requireContext(), "This is the first question", Toast.LENGTH_SHORT).show()
                Log.e("MCQFragment", "This is the first question")
            }
        }
        nextButton.setOnClickListener {
            if (currentQuestionIndex < freeTestQuestionResponse.questions.size - 1) {
                recyclerViewMcqQuestion.smoothScrollToPosition(currentQuestionIndex + 1)
            } else {
                submitButton.visibility = View.VISIBLE
                nextButton.visibility = View.GONE
                Toast.makeText(requireContext(), "This is the last question", Toast.LENGTH_SHORT).show()
            }
        }
        submitButton.setOnClickListener {
            val questions = freeTestQuestionResponse.questions
            mcqAdapter.submitDialogOpenHelper()
        }
    }

    private fun setSnapHelper() {
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerViewMcqQuestion)

        recyclerViewMcqQuestion.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val view = snapHelper.findSnapView(recyclerView.layoutManager)
                currentQuestionIndex = view?.let { recyclerView.layoutManager?.getPosition(it) } ?: 0
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
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
        submitButton.visibility = View.GONE // Initially hide the submit button
    }

    private fun showDialog(resultMessage: String) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Quiz Result")
        dialog.setMessage(resultMessage)
        dialog.setPositiveButton("OK") { _, _ -> }
        dialog.show()
    }
}