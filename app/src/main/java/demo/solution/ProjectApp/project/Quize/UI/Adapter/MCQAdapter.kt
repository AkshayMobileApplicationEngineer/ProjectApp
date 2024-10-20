package demo.solution.ProjectApp.project.Quize.UI.Adapter

import android.content.Context
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.recyclerview.widget.RecyclerView
import demo.solution.ProjectApp.R
import demo.solution.ProjectApp.project.Quize.UI.FreeTestQuestionResponse

class MCQAdapter(
    private val freeTestQuestionResponse: FreeTestQuestionResponse,
    private val context: Context
) : RecyclerView.Adapter<MCQAdapter.MCQViewHolder>() {

    private val userAnswers: MutableList<String?> = MutableList(freeTestQuestionResponse.questions.size) { null }
    private val timers: MutableList<CountDownTimer?> = MutableList(freeTestQuestionResponse.questions.size) { null }

    // Lists to store question numbers
    private val correctQuestions: MutableList<Int> = mutableListOf()
    private val incorrectQuestions: MutableList<Int> = mutableListOf()
    private val skippedQuestions: MutableList<Int> = mutableListOf()

    class MCQViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionNumberText: TextView = itemView.findViewById(R.id.questionNumberText)
        val timerTextView: TextView = itemView.findViewById(R.id.timerTextView)
        val questionText: TextView = itemView.findViewById(R.id.questionText)
        val optionsGroup: RadioGroup = itemView.findViewById(R.id.optionsGroup)
        val optionA: RadioButton = itemView.findViewById(R.id.optionA)
        val optionB: RadioButton = itemView.findViewById(R.id.optionB)
        val optionC: RadioButton = itemView.findViewById(R.id.optionC)
        val optionD: RadioButton = itemView.findViewById(R.id.optionD)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MCQViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mcq_question, parent, false)
        return MCQViewHolder(view)
    }

    override fun onBindViewHolder(holder: MCQViewHolder, position: Int) {
        val question = freeTestQuestionResponse.questions[position]
        holder.questionNumberText.text = "Question ${position + 1} of ${freeTestQuestionResponse.questions.size}"
        holder.questionText.text = question.question
        holder.optionA.text = question.optionA
        holder.optionB.text = question.optionB
        holder.optionC.text = question.optionC
        holder.optionD.text = question.optionD

        // Set previously selected answer and reset background colors
        holder.optionsGroup.setOnCheckedChangeListener(null)
        holder.optionsGroup.clearCheck()

        userAnswers[position]?.let { answer ->
            when (answer) {
                "A" -> holder.optionA.isChecked = true

                "B" -> holder.optionB.isChecked = true
                "C" -> holder.optionC.isChecked = true
                "D" -> holder.optionD.isChecked = true
            }
        }

        // Set click listeners for each option
        holder.optionA.setOnClickListener { selectedUserAnswer(holder, holder.optionA, "A", position) }
        holder.optionB.setOnClickListener { selectedUserAnswer(holder, holder.optionB, "B", position) }
        holder.optionC.setOnClickListener { selectedUserAnswer(holder, holder.optionC, "C", position) }
        holder.optionD.setOnClickListener { selectedUserAnswer(holder, holder.optionD, "D", position) }

        // Start the timer for this question
        startTimer(holder, position)
    }

    private fun selectedUserAnswer(holder: MCQViewHolder, selectedOption: RadioButton, answer: String, position: Int) {
        val question = freeTestQuestionResponse.questions[position]
        userAnswers[position] = answer





        // Check if the selected answer is correct
        if (question.correct_option == answer) {
            correctQuestions.add(position + 1) // Store correct question number (1-based index)
        } else {
            incorrectQuestions.add(position + 1) // Store incorrect question number (1-based index)
        }
    }



    // Method to handle skipped questions
    fun skipQuestion(position: Int) {
        if (userAnswers[position] == null) {
            skippedQuestions.add(position + 1) // Store skipped question number (1-based index)
        }
    }

    private fun startTimer(holder: MCQViewHolder, position: Int) {
        timers[position]?.cancel() // Cancel any existing timer for this position

        // Create a new timer for this question
        timers[position] = object : CountDownTimer(15000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                holder.timerTextView.text = "Time left: $secondsRemaining seconds"
            }

            override fun onFinish() {
                holder.timerTextView.text = "Time's up!"
                if (userAnswers[position] == null) {
                    skipQuestion(position) // Mark question as skipped if time runs out
                }
            }
        }.start()
    }

    override fun getItemCount(): Int {
        return freeTestQuestionResponse.questions.size
    }

    // Cancel all timers when the adapter is no longer needed
    fun cancelAllTimers() {
        timers.forEach { it?.cancel() }
    }

    // Optionally, provide methods to retrieve question numbers
    fun getCorrectQuestions(): List<Int> = correctQuestions
    fun getIncorrectQuestions(): List<Int> = incorrectQuestions
    fun getSkippedQuestions(): List<Int> = skippedQuestions

    // Method to show totals in a Toast
    fun submitDialogOpenHelper() {
        val totalCorrect = correctQuestions.size
        val totalIncorrect = incorrectQuestions.size
        val totalSkipped = skippedQuestions.size

        val dialog = AlertDialog.Builder(context)
        dialog.setTitle("Quiz Result")
        dialog.setMessage("Correct: $totalCorrect, Incorrect: $totalIncorrect, Skipped: $totalSkipped")
        dialog.setPositiveButton("OK") { _, _ -> }
        dialog.show()
    }


}
