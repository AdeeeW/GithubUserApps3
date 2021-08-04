package com.example.githubuserapps2.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapps2.R
import com.example.githubuserapps2.ViewModel.FollowersViewModel
import com.example.githubuserapps2.activity.DetailActivityUsers
import com.example.githubuserapps2.adapter.AdapterUser
import com.example.githubuserapps2.databinding.FragmentFollowersBinding

class FragmentFollowers : Fragment(R.layout.fragment_followers ) {
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelFollowers : FollowersViewModel
    private lateinit var adapteruser : AdapterUser
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailActivityUsers.EXTRA_USERNAME).toString()
        _binding = FragmentFollowersBinding.bind(view)
        adapteruser = AdapterUser()
        adapteruser.notifyDataSetChanged()

        binding.apply {
            rvFollowers.layoutManager = LinearLayoutManager(activity)
            rvFollowers.setHasFixedSize(true)
            rvFollowers.adapter = adapteruser
        }
        showLoading(true)
        viewModelFollowers = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)
        viewModelFollowers.setListFollowrs(username)
        viewModelFollowers.getListFollowers().observe(viewLifecycleOwner, {
            if (it != null){
                adapteruser.setUserList(it)
                showLoading(false)
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun showLoading(state: Boolean){
        binding.apply {
            if (state){
                progressBar.visibility = View.VISIBLE
            }else{
                progressBar.visibility = View.GONE
            }
        }
    }
}