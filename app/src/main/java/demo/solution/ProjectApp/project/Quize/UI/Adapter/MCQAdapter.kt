package demo.solution.ProjectApp.project.Quize.UI.Adapter

import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import demo.solution.ProjectApp.R
import demo.solution.ProjectApp.project.Quize.UI.FreeTestQuestionResponse

class MCQAdapter(private val freeTestQuestionResponse: FreeTestQuestionResponse) : RecyclerView.Adapter<MCQAdapter.MCQViewHolder>() {
    private val totalTimeInMillis: Long = 120000 // 120 seconds in milliseconds
    private val userAnswers: MutableList<String?> = MutableList(freeTestQuestionResponse.questions.size) { null }

    class MCQViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionNumberText: TextView = itemView.findViewById(R.id.questionNumberText)
        val timerTextView: TextView = itemView.findViewById(R.id.timerTextView)
        val questionText: TextView = itemView.findViewById(R.id.questionText)
        val optionsGroup: RadioGroup = itemView.findViewById(R.id.optionsGroup)
        val optionA: RadioButton = itemView.findViewById(R.id.optionA)
        val optionB: RadioButton = itemView.findViewById(R.id.optionB)
        val optionC: RadioButton = itemView.findViewById(R.id.optionC)
        val optionD: RadioButton = itemView.findViewById(R.id.optionD)
        var countDownTimer: CountDownTimer? = null
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
        holder.timerTextView.text = "Time left: ${totalTimeInMillis / 1000} seconds"

        // Set previously selected answer
        holder.optionsGroup.setOnCheckedChangeListener(null)
        holder.optionsGroup.clearCheck()
        userAnswers[position]?.let {
            when (it) {
                "A" -> holder.optionA.isChecked = true
                "B" -> holder.optionB.isChecked = true
                "C" -> holder.optionC.isChecked = true
                "D" -> holder.optionD.isChecked = true
            }
        }

        holder.optionsGroup.setOnCheckedChangeListener { _, checkedId ->
            val answer = when (checkedId) {
                R.id.optionA -> "A"
                R.id.optionB -> "B"
                R.id.optionC -> "C"
                R.id.optionD -> "D"
                else -> null
            }
            userAnswers[position] = answer

            // Start timer if an option is selected
            startTimer(holder, totalTimeInMillis)
        }
    }

    private fun startTimer(holder: MCQViewHolder, millisInFuture: Long) {
        holder.countDownTimer?.cancel()
        holder.countDownTimer = object : CountDownTimer(millisInFuture, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                holder.timerTextView.text = "left: $secondsRemaining seconds"
            }

            override fun onFinish() {
                holder.timerTextView.text = "Time's up!"

                // Handle what happens when time is up, e.g., automatically move to the next question or submit the quiz
            }
        }.start()
    }

    override fun getItemCount(): Int {
        return freeTestQuestionResponse.questions.size
    }
}
