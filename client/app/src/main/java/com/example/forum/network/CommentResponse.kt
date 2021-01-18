package com.example.forum.network

import com.squareup.moshi.Json

data class CommentResponse(
  val id: Long,
  @Json(name = "post_id") val postId: Long,
  @Json(name = "author_id") val authorId: Long,
  val content: String,
  @Json(name = "published_date") val publishedDate: String
)