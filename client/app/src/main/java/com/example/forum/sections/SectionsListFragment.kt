package com.example.forum.sections

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.forum.R
import com.example.forum.databinding.SectionsListFragmentBinding

class SectionsListFragment : Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val application = requireNotNull(activity).application
    val binding = SectionsListFragmentBinding.inflate(inflater)
    binding.lifecycleOwner = this

    val user = SectionsListFragmentArgs.fromBundle(arguments!!).user
    val viewModelFactory = SectionsListViewModelFactory(user, application)
    val viewModel = ViewModelProvider(this, viewModelFactory).get(SectionsListViewModel::class.java)
    binding.viewModel = viewModel
    binding.sectionsList.adapter = SectionsAdapter(SectionsAdapter.OnClickListener {
      viewModel.displaySectionPosts(it)
    })

    viewModel.navigateToSelectedSection.observe(viewLifecycleOwner, Observer { section ->
      section?.let {
        this.findNavController().navigate(SectionsListFragmentDirections.actionSectionsListFragmentToPostsListFragment(user, section))
        viewModel.displaySectionPostsComplete()
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