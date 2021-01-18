package com.example.forum.registration

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.forum.Utils
import com.example.forum.network.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationViewModel : ViewModel() {

  lateinit var faculty: UserRole
  val user = MutableLiveData<User>()
  val login = MutableLiveData<String>()
  val password = MutableLiveData<String>()
  val isLoginValid = MutableLiveData<Boolean>()
  val isPasswordValid = MutableLiveData<Boolean>()
  val isUserExist = MutableLiveData<Boolean>()

  val facultiesList = arrayOf(
    "РТФ",
    "РКФ",
    "ФВС",
    "ФСУ",
    "ФЭТ",
    "ФИТ",
    "ЭФ",
    "ГФ",
    "ЮФ",
    "ФБ",
    "ЗИВФ",
    "ФДО"
  )

  init {
    initializeFields()
  }

  fun actionToLoginComplete() {
    initializeFields()
  }

  fun addNewUser() {
    val l = login.value!!.trim()
    val p = password.value!!
    if (!isFieldValid(l)) {
      isLoginValid.value = false
    } else if (!isFieldValid(p)) {
      isPasswordValid.value = false
    } else {
      val hash = Utils.hash(p)
      ForumApi.retrofitService.addUser(NewUserBody(login.value!!, hash, listOf(UserRole.GUEST, UserRole.USER, faculty))).enqueue(object: Callback<User> {
        override fun onResponse(call: Call<User>, response: Response<User>) {
          if (response.code() == ForumApiStatus.ERROR.code) {
            isUserExist.value = true
          } else {
            user.value = response.body()
            Log.i("RegistrationViewModel", "${response.body()}")
          }

        }

        override fun onFailure(call: Call<User>, t: Throwable) {
          Log.e("RegistrationViewModel", t.message!!)
        }
      })
    }
  }

  private fun isFieldValid(field: String): Boolean {
    return field.isNotEmpty()
  }

  private fun initializeFields() {
    login.value = ""
    password.value = ""
    faculty = UserRole.GUEST
    isLoginValid.value = true
    isPasswordValid.value = true
    isUserExist.value = false
    user.value = null
  }
}