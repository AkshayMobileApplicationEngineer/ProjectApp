package demo.solution.ProjectApp.project.Quize.UI

import android.os.Bundle
<<<<<<< HEAD
import android.os.CountDownTimer
=======
>>>>>>> f85cc3e7f44b99e2e25c9d0972ab613b5b5ec513
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
<<<<<<< HEAD
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
=======
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
>>>>>>> f85cc3e7f44b99e2e25c9d0972ab613b5b5ec513
import demo.solution.ProjectApp.R
import demo.solution.ProjectApp.project.Quize.UI.Adapter.MCQAdapter
import demo.solution.ProjectApp.project.Quize.UI.Question
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
<<<<<<< HEAD
import kotlin.math.max
=======
>>>>>>> f85cc3e7f44b99e2e25c9d0972ab613b5b5ec513

class MCQFragment : Fragment() {

    private lateinit var recyclerViewMcqQuestion: RecyclerView
    private lateinit var mcqAdapter: MCQAdapter
    private lateinit var iconApp: ImageView
    private lateinit var previousButton: Button
    private lateinit var nextButton: Button
    private lateinit var submitButton: Button
<<<<<<< HEAD
    private var currentQuestionIndex: Int = 0
    private var countDownTimer: CountDownTimer? = null
=======

>>>>>>> f85cc3e7f44b99e2e25c9d0972ab613b5b5ec513
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
<<<<<<< HEAD

        call.enqueue(object : Callback<FreeTestQuestionResponse> {
            override fun onResponse(call: Call<FreeTestQuestionResponse>, response: Response<FreeTestQuestionResponse>) {
                try {
                    if (response.isSuccessful && response.body() != null) {
                        val freeTestQuestionResponse = response.body()


                        if (freeTestQuestionResponse != null) {
                            mcqAdapter = MCQAdapter(freeTestQuestionResponse)

                            recyclerViewMcqQuestion.adapter = mcqAdapter
                            mcqAdapter.notifyDataSetChanged()

                            setSnapHelper()
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
    }
    private fun showDialog(resultMessage: String) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Quiz Result")
        dialog.setMessage(resultMessage)
        dialog.setPositiveButton("OK") { _, _ -> }
        dialog.show()
    }
    /*
    private fun startTimer(millisInFuture: Long) {

        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(millisInFuture, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                timerTextView.text = "Time left: $secondsRemaining seconds"

            }

            override fun onFinish() {
                timerTextView.text = "Time's up!"
                // Handle what happens when time is up, e.g., automatically move to the next question or submit the quiz
            }
        }.start()
    }


     */
=======
>>>>>>> f85cc3e7f44b99e2e25c9d0972ab613b5b5ec513

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
