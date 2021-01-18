package com.example.forum.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

enum class UserRole {
  GUEST,
  USER,
  MODERATOR,
  RTF_STUDENT,
  RKF_STUDENT,
  FVS_STUDENT,
  FSU_STUDENT,
  FET_STUDENT,
  FIT_STUDENT,
  EF_STUDENT,
  GF_STUDENT,
  UF_STUDENT,
  FB_STUDENT,
  ZIVF_STUDENT,
  FDO_STUDENT
}

@Parcelize
data class User(
  val id: Long,
  val login: String,
  val roles: List<UserRole>) : Parcelable