package com.example.forum.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

enum class ForumApiStatus(val code: Int) {
  SUCCESS(200),
  CREATED(201),
  BAD(400),
  NOT_FOUND(404),
  ERROR(500)
}

private const val BASE_URL = "http://10.0.2.2:3000/api/v1/"

private val moshi = Moshi.Builder()
  .add(KotlinJsonAdapterFactory())
  .build()

private val retrofit = Retrofit.Builder()
  .addConverterFactory(MoshiConverterFactory.create(moshi))
  .baseUrl(BASE_URL)
  .build()

interface ForumApiService {

  @POST("auth/join")
  fun addUser(@Body user: NewUserBody): Call<User>

  @POST("auth/login")
  fun getUser(@Body user: UserBody): Call<User>

  @POST("auth/exit")
  fun updateUser(@Body user: User): Call<User>

  @GET("sections")
  fun getPostSections(@Header("user_id") userId: Long): Call<List<Section>>

  @GET("sections/{sectionId}/posts")
  fun getAllPostsFromSection(@Path("sectionId") sectionId: Long): Call<List<PostInfo>>

  @POST("sections/{sectionId}/posts")
  fun addPost(@Body post: Post): Call<Unit>

  @GET("posts/{id}")
  fun getPost(@Path("id") postId: Long): Call<Post>

  @POST("posts/{id}")
  fun addComment(@Path("id") postId: Long, @Body comment: CommentBody): Call<CommentResponse>

  @GET("posts/{id}/comments")
  fun getComments(@Path("id") postId: Long): Call<List<Comment>>

  @DELETE("posts/{id}")
  fun deletePost(@Path("id") postId: Long): Call<Unit>

  @DELETE("comments/{id}")
  fun deleteComment(@Path("id") commentId: Long): Call<Unit>
}

object ForumApi {
  val retrofitService: ForumApiService by lazy {
    retrofit.create(ForumApiService::class.java)
  }
}