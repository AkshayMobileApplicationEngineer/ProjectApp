package demo.solution.ProjectApp.project.Quize.UI

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import demo.solution.ProjectApp.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.max

class QuizFragment : Fragment() {

        private lateinit var questionNumberText: TextView
        private lateinit var timerTextView: TextView
        private lateinit var questionText: TextView
        private lateinit var optionA: RadioButton
        private lateinit var optionB: RadioButton
        private lateinit var optionC: RadioButton
        private lateinit var optionD: RadioButton
        private lateinit var previousButton: Button
        private lateinit var nextButton: Button
        private lateinit var submitButton: Button

        private var questions: List<Question> = listOf()
        private var currentQuestionIndex: Int = 0
        private var countDownTimer: CountDownTimer? = null
        private val totalTimeInMillis: Long = 120000 // 120 seconds in milliseconds
        private val maxQuestionsToShow = 30
        private var startTime: Long = 0

        private val userAnswers = mutableListOf<String?>()

        override fun onCreateView(
                inflater: LayoutInflater,
                container: ViewGroup?,
                savedInstanceState: Bundle?
        ): View? {
                // Inflate the layout for this fragment
                val view = inflater.inflate(R.layout.fragment_quiz, container, false)
                idInitialization(view)

                testFreeQuestionRetrofit("128", "2")
                return view
        }

        // Retrofit API call
        private fun testFreeQuestionRetrofit(userId: String, testId: String) {
                // Make the API call to get the test questions
                val call = RetrofitInstance.apiService.getFreeTestQuestion(userId, testId)

                call.enqueue(object : Callback<FreeTestQuestionResponse> {
                        override fun onResponse(call: Call<FreeTestQuestionResponse>, response: Response<FreeTestQuestionResponse>) {
                                try {
                                        if (response.isSuccessful && response.body() != null) {
                                                val freeTestQuestionResponse = response.body()
                                                questions = freeTestQuestionResponse?.questions ?: listOf()

                                                if (questions.isNotEmpty()) {
                                                        startTime = System.currentTimeMillis()
                                                        userAnswers.addAll(List(questions.size) { null })
                                                        displayQuestion(0)
                                                } else {
                                                        Log.e("QuizFragment", "No questions available")
                                                        Toast.makeText(requireContext(), "No questions available", Toast.LENGTH_SHORT).show()
                                                }
                                        } else {
                                                Log.e("QuizFragment", "Failed to get test questions: ${response.message()}")
                                                Toast.makeText(requireContext(), "Failed to get test questions: ${response.message()}", Toast.LENGTH_SHORT).show()
                                        }
                                } catch (e: Exception) {
                                        Log.e("QuizFragment", "Failed to get test questions: ${e.message}")
                                        Toast.makeText(requireContext(), "Failed to get test questions: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                        }

                        override fun onFailure(call: Call<FreeTestQuestionResponse>, t: Throwable) {
                                Log.e("QuizFragment", "Network request failed", t)
                                Toast.makeText(requireContext(), "Network request failed: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                })
        }

        private fun idInitialization(view: View?) {
                questionNumberText = view?.findViewById(R.id.questionNumberText) as TextView
                timerTextView = view?.findViewById(R.id.timerTextView) as TextView
                questionText = view?.findViewById(R.id.questionText) as TextView
                optionA = view?.findViewById(R.id.optionA) as RadioButton
                optionB = view?.findViewById(R.id.optionB) as RadioButton
                optionC = view?.findViewById(R.id.optionC) as RadioButton
                optionD = view?.findViewById(R.id.optionD) as RadioButton
                previousButton = view?.findViewById(R.id.previousButton) as Button
                nextButton = view?.findViewById(R.id.nextButton) as Button
                submitButton = view?.findViewById(R.id.submitButton) as Button

                previousButton.setOnClickListener { displayPreviousQuestion() }
                nextButton.setOnClickListener { displayNextQuestion() }
                submitButton.setOnClickListener { submitQuiz() }

                optionA.setOnClickListener { saveUserAnswer("A") }
                optionB.setOnClickListener { saveUserAnswer("B") }
                optionC.setOnClickListener { saveUserAnswer("C") }
                optionD.setOnClickListener { saveUserAnswer("D") }
        }

        private fun displayQuestion(index: Int) {
                if (index in 0 until max(maxQuestionsToShow,questions.size, )) {
                        val question = questions[index]
                        questionNumberText.text = "Question ${index + 1} of ${minOf(questions.size, maxQuestionsToShow)}"
                        questionText.text = question.question
                        optionA.text = question.optionA
                        optionB.text = question.optionB
                        optionC.text = question.optionC
                        optionD.text = question.optionD

                        currentQuestionIndex = index
                        startTimer(totalTimeInMillis)

                        val userAnswer = userAnswers[index]
                        optionA.isChecked = userAnswer == "A"
                        optionB.isChecked = userAnswer == "B"
                        optionC.isChecked = userAnswer == "C"
                        optionD.isChecked = userAnswer == "D"

                        if (index == maxQuestionsToShow - 1 || index == questions.size - 1) {
                                submitButton.visibility = View.VISIBLE
                                nextButton.visibility = View.GONE
                        } else {
                                submitButton.visibility = View.GONE
                                nextButton.visibility = View.VISIBLE
                        }
                } else {
                        Log.e("QuizFragment", "Invalid question index: $index")
                        Toast.makeText(requireContext(), "Invalid question index: $index", Toast.LENGTH_SHORT).show()
                }
        }

        private fun saveUserAnswer(answer: String) {
                userAnswers[currentQuestionIndex] = answer
        }

        private fun displayPreviousQuestion() {
                if (currentQuestionIndex > 0) {
                        displayQuestion(currentQuestionIndex - 1)
                } else {
                        Toast.makeText(requireContext(), "This is the first question", Toast.LENGTH_SHORT).show()
                }
        }

        private fun displayNextQuestion() {
                if (currentQuestionIndex < minOf(questions.size, maxQuestionsToShow) - 1) {
                        displayQuestion(currentQuestionIndex + 1)
                } else {
                        submitButton.visibility = View.VISIBLE
                        nextButton.visibility = View.GONE
                        Toast.makeText(requireContext(), "This is the last question", Toast.LENGTH_SHORT).show()
                }
        }

        private fun submitQuiz() {
                val totalQuestions = minOf(questions.size, maxQuestionsToShow)
val correctAnswers = questions.count { question ->
    question.correct_option == userAnswers[questions.indexOf(question)]
}
val incorrectAnswers = totalQuestions - correctAnswers
val skippedQuestions = totalQuestions - (correctAnswers + incorrectAnswers)

val totalTestTime = System.currentTimeMillis() - startTime

val totalTimeInSeconds = totalTestTime / 1000
val minutes = totalTimeInSeconds / 60
val seconds = totalTimeInSeconds % 60

                val resultMessage = """
            Quiz submitted
            Correct Answers: $correctAnswers
            Incorrect Answers: $incorrectAnswers
            Skipped Questions: $skippedQuestions
            Total Questions: $totalQuestions
            Total Test Time: ${minutes}m ${seconds}s
        """.trimIndent()

                showDialog(resultMessage)
                Toast.makeText(requireContext(), resultMessage, Toast.LENGTH_LONG).show()
                Log.d("QuizFragment", resultMessage)
        }

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

        private fun showDialog(resultMessage: String) {
                val dialog = AlertDialog.Builder(requireContext())
                dialog.setTitle("Quiz Result")
                dialog.setMessage(resultMessage)
                dialog.setPositiveButton("OK") { _, _ -> }
                dialog.show()
        }
}
