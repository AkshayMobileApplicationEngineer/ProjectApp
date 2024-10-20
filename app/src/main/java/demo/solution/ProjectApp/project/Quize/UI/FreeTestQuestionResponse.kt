package demo.solution.ProjectApp.project.Quize.UI

data class FreeTestQuestionResponse(
    val question_count: Int,
    val questions: List<Question>,
    val status: String,
    val test_data: TestData,
    val user_update_status: String
)