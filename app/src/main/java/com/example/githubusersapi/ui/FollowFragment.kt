package com.example.githubusersapi.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusersapi.R
import com.example.githubusersapi.databinding.FragmentFollowBinding


class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private val followingViewModel by viewModels<FollowingViewModel>()
    private val followersViewModel by viewModels<FollowersViewModel>()
     var position =0
    var username = "halo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter =UserAdapter()

        adapter.setOnItemClickListener { user->
            val intent = Intent(requireContext(),DetailActivity::class.java).apply {
                putExtra("USERNAME",user.login)
            }
            startActivity(intent)
        }

        arguments?.let{
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)?:"Username tidak ditemukan"

        }

        binding.rvUsers.apply { 
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

        if (position ==1){
            followingViewModel.isLoading.observe(viewLifecycleOwner){
                isLoading->showLoading(isLoading)
            }
            followingViewModel.getFollowing(username)
        }else{
            followersViewModel.isLoading.observe(viewLifecycleOwner){
                    isLoading->showLoading(isLoading)
            }
            followersViewModel.getFollowers(username)
        }

        followingViewModel.listFollowing.observe(viewLifecycleOwner, Observer { dataList->
        adapter.submitList(dataList)
        })
        
        followersViewModel.listFollowers.observe(viewLifecycleOwner, Observer { dataList->
            adapter.submitList(dataList)
        })
    }

    private fun showLoading(isLoading:Boolean){
        if (isLoading){
            binding.progressBar.visibility= View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object{
         const val ARG_POSITION = "0"
         const val ARG_USERNAME = "USERNAME"
    }


}