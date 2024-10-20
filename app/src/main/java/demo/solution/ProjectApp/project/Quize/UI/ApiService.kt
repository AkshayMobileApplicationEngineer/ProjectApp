package demo.solution.ProjectApp.project.Quize.UI

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


//Kotlin class for all Kotlin classes that want to do Retrofit calls
interface ApiService {

    //Free TestQuestion
    @FormUrlEncoded
    @POST("test/testQuestions.php")
    fun getFreeTestQuestion(
        @Field("user_id") user_id: String,
        @Field("test_id") test_id:String
    ): Call<FreeTestQuestionResponse>

}