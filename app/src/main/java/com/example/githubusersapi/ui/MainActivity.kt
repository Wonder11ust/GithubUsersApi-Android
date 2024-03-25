package com.example.githubusersapi.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusersapi.R
import com.example.githubusersapi.data.response.UserItem
import com.example.githubusersapi.databinding.ActivityMainBinding
import com.example.githubusersapi.datastore.SettingActivity
import com.example.githubusersapi.datastore.SettingPreferences
import com.example.githubusersapi.datastore.SettingViewModel
import com.example.githubusersapi.datastore.SettingViewModelFactory
import com.example.githubusersapi.datastore.dataStore


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager


        val adapter = UserAdapter()
        binding.rvUsers.adapter = adapter

        mainViewModel.listUser.observe(this) { users ->

            adapter.submitList(users)
        }

        mainViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }


        applyTheme()
        setupFavoriteButton()

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->

                    searchBar.setText(searchView.text)
                    searchView.hide()

                    val username = binding.searchView.editText.text.toString().trim()

                    mainViewModel.getUsers(username)

                    false
                }

        }

            adapter.setOnItemClickListener { user->
                val intent = Intent(this@MainActivity,DetailActivity::class.java).apply {
                    putExtra("USERNAME",user.login)
                }
                startActivity(intent)
            }




    }

    private fun setupFavoriteButton() {
        binding.searchBar.inflateMenu(R.menu.menu_form)
        binding.searchBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_favorite -> {
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.action_setting->{
                    val settingIntent = Intent(this,SettingActivity::class.java)
                    startActivity(settingIntent)
                    true
                }

                else -> false
            }
        }
    }



    private fun showLoading(isLoading:Boolean){
        if (isLoading){
            binding.progressBar.visibility= View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun applyTheme(){
        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref)).get(
            SettingViewModel::class.java)
        settingViewModel.getThemeSetting().observe(this){
                isDarkModeActive:Boolean->
            if (isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            }
        }
    }



}