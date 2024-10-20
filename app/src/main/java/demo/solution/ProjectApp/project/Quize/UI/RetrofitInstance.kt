package demo.solution.ProjectApp.project.Quize.UI

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Retrofit object creator for Kotlin classes
object RetrofitInstance {

    // Using the base URL from BuildConfig
    private val BASE_URL: String = "https://workholicpraveen.in/api/V34/"

    // Lazy initialization of Retrofit
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)  // Ensure BASE_URL is a valid URL string
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Lazy initialization of ApiService
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

}
