package com.example.forum.post

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.forum.R
import com.example.forum.databinding.PostCreatorFragmentBinding

class PostCreatorFragment : Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val application = requireNotNull(activity).application
    val binding = PostCreatorFragmentBinding.inflate(inflater)
    binding.lifecycleOwner = this

    val user = PostCreatorFragmentArgs.fromBundle(arguments!!).user
    val section = PostCreatorFragmentArgs.fromBundle(arguments!!).section
    val viewModelFactory = PostCreatorViewModelFactory(user, section, application)
    val viewModel = ViewModelProvider(this, viewModelFactory).get(PostCreatorViewModel::class.java)
    binding.viewModel = viewModel

    viewModel.navigateToPosts.observe(viewLifecycleOwner, Observer {
      if (it) {
        this.findNavController().navigate(PostCreatorFragmentDirections.actionPostCreatorFragmentToPostsListFragment(user, section))
        viewModel.displayPostsListComplete()
      }
    })

    setHasOptionsMenu(true)
    return binding.root
  }

}