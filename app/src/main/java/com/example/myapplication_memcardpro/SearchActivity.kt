package com.example.myapplication_memcardpro

import RecyclerViewFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity



    class SearchActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_search)

            val fragment = RecyclerViewFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }
