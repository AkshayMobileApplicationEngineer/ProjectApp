package demo.solution.ProjectApp.project.Quize.UI.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import demo.solution.ProjectApp.R
import demo.solution.ProjectApp.project.Quize.UI.Question

class MCQAdapter(private val questionList: List<Question>) : RecyclerView.Adapter<MCQAdapter.MCQViewHolder>() {

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
        val questionItem = questionList[position]
        holder.questionNumberText.text = "Question ${position + 1} of ${questionList.size}"
        holder.questionText.text = questionItem.question

        // Set options
        holder.optionA.text = questionItem.optionA
        holder.optionB.text = questionItem.optionB
        holder.optionC.text = questionItem.optionC
        holder.optionD.text = questionItem.optionD

        // Clear any previously set RadioButton
        holder.optionsGroup.clearCheck()

        // Set the correct option as checked if available

    }

    override fun getItemCount(): Int {
        return questionList.size
    }
}
