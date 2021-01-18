package com.example.forum.network

import com.squareup.moshi.Json

data class Comment(
  val id: Long,
  @Json(name = "author_name") val authorName: String,
  val content: String,
  @Json(name = "published_date") val publishedDate: String
)