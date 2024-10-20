package demo.solution.ProjectApp.project.Quize.UI

data class Question(
    val correct_option: String,
    val created_at: String,
    val level: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val question: String,
    val questionid: Int,
    val section: String
)
