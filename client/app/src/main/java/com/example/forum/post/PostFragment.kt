package com.example.forum.post

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.forum.R
import com.example.forum.databinding.PostFragmentBinding
import com.example.forum.network.UserRole

class PostFragment : Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val application = requireNotNull(activity).application
    val binding = PostFragmentBinding.inflate(inflater)
    binding.lifecycleOwner = this

    val user = PostFragmentArgs.fromBundle(arguments!!).user
    val postId = PostFragmentArgs.fromBundle(arguments!!).postId
    val viewModelFactory = PostViewModelFactory(user, postId, application)
    val viewModel = ViewModelProvider(this, viewModelFactory).get(PostViewModel::class.java)
    binding.viewModel = viewModel
    val adapter = CommentsAdapter(viewModel, user.roles.contains(UserRole.MODERATOR))
    binding.commentsListView.adapter = adapter

    viewModel.isCommentEditorEnable.observe(viewLifecycleOwner, Observer {
      if (it) {
        binding.commentInputLayout.visibility = View.VISIBLE
        binding.userCommentEdit.requestFocus()
        val inputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(binding.userCommentEdit, InputMethodManager.SHOW_IMPLICIT)
      } else {
        binding.commentInputLayout.visibility = View.GONE
        val inputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view!!.windowToken, 0)
      }
    })

    viewModel.comments.observe(viewLifecycleOwner, Observer {
      it.let {
        adapter.submitList(it)
        adapter.notifyDataSetChanged()
      }
    })

    setHasOptionsMenu(true)
    return binding.root
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.forum_main_menu, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return NavigationUI.onNavDestinationSelected(item, view!!.findNavController()) || super.onOptionsItemSelected(item)
  }
}