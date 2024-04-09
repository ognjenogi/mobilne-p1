import com.example.projekat1.breeds.Breed
import com.example.projekat1.breeds.BreedDetails
import com.example.projekat1.breeds.CatImage
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response
import retrofit2.http.Path

interface CatApiService {
    @GET("breeds")
    suspend fun getBreeds(): Response<List<Breed>>

    @GET("images/search")
    suspend fun getImagesByBreedId(@Query("breed_ids") breedId: String): Response<List<CatImage>>
    @GET("breeds/{breedId}")
    suspend fun getBreedDetails(@Path("breedId") breedId: String): Response<BreedDetails>
    @GET("breeds/search")
    suspend fun getBreedsByQuery(@Query("name") name: String): Response<List<Breed>>

}
