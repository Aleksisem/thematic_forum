package com.example.forum.network

import com.squareup.moshi.Json

data class PostInfo(
  val id: Long,
  val title: String,
  @Json(name = "published_date") val publishedDate: String,
  @Json(name = "count") val comments: Int
)