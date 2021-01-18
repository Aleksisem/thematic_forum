package com.example.forum.registration

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.forum.databinding.RegistrationFragmentBinding
import com.example.forum.network.UserRole

class RegistrationFragment : Fragment() {
  private val viewModel: RegistrationViewModel by lazy {
    ViewModelProvider(this).get(RegistrationViewModel::class.java)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val binding = RegistrationFragmentBinding.inflate(inflater)
    binding.lifecycleOwner = this
    binding.viewModel = viewModel

    val adapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, viewModel.facultiesList)
    binding.facultyListSpinner.adapter = adapter
    binding.facultyListSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.faculty = UserRole.values()[position + 3]
      }

      override fun onNothingSelected(parent: AdapterView<*>?) {

      }
    }

    viewModel.user.observe(viewLifecycleOwner, Observer { user ->
      user?.let {
        this.findNavController().navigate(RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment())
        viewModel.actionToLoginComplete()
      }
    })

    viewModel.isLoginValid.observe(viewLifecycleOwner, Observer { valid ->
      if (!valid) {
        binding.usernameEdit.error = "Некорректный логин"
      }
    })

    viewModel.isPasswordValid.observe(viewLifecycleOwner, Observer { valid ->
      if (!valid) {
        binding.passwordEdit.error = "Некорректный пароль"
      }
    })

    viewModel.isUserExist.observe(viewLifecycleOwner, Observer { exist ->
      if (exist) {
        binding.usernameEdit.error = "Данный логин уже занят"
      }
    })

    return binding.root
  }

}