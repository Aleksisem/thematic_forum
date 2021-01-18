package com.example.forum.network

import com.squareup.moshi.Json

data class Post(
  val id: Long,
  val title: String,
  val content: String,
  val category: Long,
  @Json(name = "author_id") val authorId: Long,
  @Json(name = "author_login") val authorLogin: String,
  @Json(name = "published_date") val publishedDate: String
)