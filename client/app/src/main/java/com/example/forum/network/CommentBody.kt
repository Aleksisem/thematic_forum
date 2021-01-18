package com.example.forum.network

import com.squareup.moshi.Json

data class CommentBody(
  @Json(name = "post_id") val postId: Long,
  @Json(name = "author_id") val authorId: Long,
  val content: String
)