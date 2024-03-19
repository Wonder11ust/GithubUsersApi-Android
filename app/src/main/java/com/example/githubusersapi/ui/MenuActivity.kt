package com.example.githubusersapi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.githubusersapi.R
import com.example.githubusersapi.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}