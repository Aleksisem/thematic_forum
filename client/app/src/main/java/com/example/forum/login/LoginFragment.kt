package com.example.forum.login

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.forum.R
import com.example.forum.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {
  private val viewModel: LoginViewModel by lazy {
    ViewModelProvider(this).get(LoginViewModel::class.java)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val binding = LoginFragmentBinding.inflate(inflater)
    binding.lifecycleOwner = this
    binding.viewModel = viewModel

    viewModel.eventUserAuthorize.observe(viewLifecycleOwner, Observer { user ->
      user?.let {
        this.findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSectionsListFragment(user))
        viewModel.onSignUpComplete()
        val inputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view!!.windowToken, 0)
      }
    })
    viewModel.eventUserRegister.observe(viewLifecycleOwner, Observer {
      if (it) {
        Log.i("LoginFragment", "Go to RegistrationFragment")
        this.findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegistrationFragment())
        viewModel.onSignUpComplete()
      }
    })
    viewModel.isDataCorrect.observe(viewLifecycleOwner, Observer {
      if (!it) {
        binding.usernameEdit.error = "Неверный логин/пароль"
        binding.passwordEdit.error = "Неверный логин/пароль"
      }
    })
    viewModel.isLoginValid.observe(viewLifecycleOwner, Observer {
      if (!it) {
        binding.usernameEdit.error = "Поле не может быть пустым"
      }
    })

    return binding.root
  }

}