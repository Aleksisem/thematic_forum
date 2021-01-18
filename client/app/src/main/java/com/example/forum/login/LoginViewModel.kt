package com.example.forum.login

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.forum.Utils
import com.example.forum.network.ForumApi
import com.example.forum.network.User
import com.example.forum.network.UserBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//
//class User {
//  var user_id: Int = 0
//  var login: String = "Lex"
//  var password: String = "Lex"
//}

class LoginViewModel : ViewModel() {

  val login = MutableLiveData<String>()
  val password = MutableLiveData<String>()
  val eventUserAuthorize = MutableLiveData<User?>()
  val eventUserRegister = MutableLiveData<Boolean>()
  val isDataCorrect = MutableLiveData<Boolean>()
  val isLoginValid = MutableLiveData<Boolean>()

  init {
    login.value = ""
    password.value = ""
    eventUserAuthorize.value = null
    eventUserRegister.value = false
    isDataCorrect.value = true
  }

  /**
   * Идентифицировать пользователя
   */
  fun signIn() {
    if (login.value!!.trim().isEmpty()) {
      isLoginValid.value = false
    } else {
      isLoginValid.value = true
      val hash = Utils.hash(password.value!!)
      ForumApi.retrofitService.getUser(UserBody(login.value!!, hash)).enqueue(object: Callback<User> {
        override fun onResponse(call: Call<User>, response: Response<User>) {
          if (response.code() == 404) {
            isDataCorrect.value = false
          } else {
            val user = response.body()
            user?.let {
              authorizeUser(it)
            }
          }
        }

        override fun onFailure(call: Call<User>, t: Throwable) {
          Log.e("LoginViewModel", t.message!!)
        }
      })
    }
  }

  /**
   * Зарегистрировать нового пользователя
   */
  fun signUp() {
    eventUserRegister.value = true
  }

  /**
   * Завершает переход к полю регистрации
   */
  fun onSignUpComplete() {
    eventUserRegister.value = false
  }

  /**
   * Завершает авторизацию пользователя
   */
  fun authorizeUserComplete() {
    eventUserAuthorize.value = null
  }
  /**
   * Запускает авторизацию пользователя.
   * @param user пользователь.
   */
//  private fun authorizeUser(user: User) {
//    _eventUserAuthorize.value = user.user_id
//  }
  private fun authorizeUser(user: User) {
    eventUserAuthorize.value = user
  }
}