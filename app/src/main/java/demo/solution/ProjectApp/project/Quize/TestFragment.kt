package demo.solution.ProjectApp.project.Quize

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import demo.solution.ProjectApp.R
import demo.solution.ProjectApp.project.Quize.UI.QuizFragment
import demo.solution.ProjectApp.project.Quize.UI.MCQFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TestFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var btnQuizP: Button
    private lateinit var btnMcq: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_test, container, false)
        init(view)
        setup()
        return view
    }

    private fun setup() {
        btnQuizP.setOnClickListener {
            startFragment(QuizFragment())
        }
        btnMcq.setOnClickListener {
            startFragment(MCQFragment())
        }
    }

    private fun startFragment(fragment: Fragment) {
        if (activity != null) {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private fun init(view: View?) {
        btnQuizP = view?.findViewById(R.id.btn_quiz_p) as Button
        btnMcq = view?.findViewById(R.id.btn_mcq) as Button
    }
}
