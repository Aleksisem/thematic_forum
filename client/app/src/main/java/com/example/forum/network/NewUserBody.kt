package com.example.forum.network

data class NewUserBody(
  val login: String,
  val password: String,
  val roles: List<UserRole>
)