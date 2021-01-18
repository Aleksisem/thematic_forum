package com.example.forum.posts

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.forum.R
import com.example.forum.databinding.PostsListFragmentBinding
import com.example.forum.network.PostInfo
import com.example.forum.network.User
import com.example.forum.network.UserRole

class PostsListFragment : Fragment() {

  lateinit var viewModel: PostsListViewModel
  lateinit var adapter: PostsAdapter

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val application = requireNotNull(activity).application
    val binding = PostsListFragmentBinding.inflate(inflater)
    binding.lifecycleOwner = this

    val user = PostsListFragmentArgs.fromBundle(arguments!!).user
    val section = PostsListFragmentArgs.fromBundle(arguments!!).section
    val viewModelFactory = PostsListViewModelFactory(user, section, application)
    viewModel = ViewModelProvider(this, viewModelFactory).get(PostsListViewModel::class.java)
    binding.viewModel = viewModel
    adapter = PostsAdapter(PostsAdapter.OnClickListener {
      viewModel.displayPost(it)
    }, viewModel, user.roles.contains(UserRole.MODERATOR))
    binding.postsList.adapter = adapter

    viewModel.navigateToSelectedPost.observe(viewLifecycleOwner, Observer { post ->
      post?.let {
        this.findNavController().navigate(PostsListFragmentDirections.actionPostsListFragmentToPostFragment(user, post.id))
        viewModel.displayPostComplete()
      }
    })
    viewModel.posts.observe(viewLifecycleOwner, Observer {
      it.let {
        adapter.submitList(it)
        adapter.notifyDataSetChanged()
      }
    })

    setHasOptionsMenu(true)
    (activity as AppCompatActivity).supportActionBar?.title = section.name

    return binding.root
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    if (viewModel.user.roles.size < 2 && viewModel.user.roles.contains(UserRole.GUEST)) {
      inflater.inflate(R.menu.forum_main_menu, menu)
    } else {
      inflater.inflate(R.menu.forum_posts_menu, menu)
    }
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    val user = PostsListFragmentArgs.fromBundle(arguments!!).user
    val section = PostsListFragmentArgs.fromBundle(arguments!!).section
    return when (item.itemId) {
      R.id.create_post -> {
        this.findNavController().navigate(PostsListFragmentDirections.actionPostsListFragmentToPostCreatorFragment(user, section))
        true
      }
      else -> NavigationUI.onNavDestinationSelected(item, view!!.findNavController()) || super.onOptionsItemSelected(item)
    }
  }
}