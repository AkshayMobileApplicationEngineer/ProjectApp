package demo.solution.ProjectApp.project.Quize.UI.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.TextView
import demo.solution.ProjectApp.R
import demo.solution.ProjectApp.project.Quize.UI.FreeTestQuestionResponse

class QuestionGridAdapter(
    private val freeTestQuestionResponse: FreeTestQuestionResponse,
    private val status: Int,
    private val onQuestionClickListener: OnQuestionClickListener
) : BaseAdapter() {

    override fun getCount(): Int {
        return freeTestQuestionResponse.questions.size
    }

    override fun getItem(position: Int): Any {
        return freeTestQuestionResponse.questions[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val myView: View = convertView ?: LayoutInflater.from(parent?.context)
            .inflate(R.layout.item_quiz_number, parent, false)

        val quizNumberGetItem: TextView = myView.findViewById(R.id.quizNumberGetItem)

        // Check if the view is not null
        if (quizNumberGetItem != null) {
            quizNumberGetItem.text = "${position + 1}" // Set text as needed
            quizNumberGetItem.setOnClickListener {
                onQuestionClickListener.onQuestionClick(position)
            }
        } else {
            Log.e("demo.solution.ProjectApp.project.Quize.UI.Adapter.QuestionGridAdapter", "quizNumberGetItem is null for position: $position")
        }


        return myView
    }
}
