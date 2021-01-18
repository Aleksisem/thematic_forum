package com.example.forum.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Section(
  val id: Long,
  val name: String
) : Parcelable