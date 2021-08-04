package com.example.githubuserapps2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapps2.R
import com.example.githubuserapps2.databinding.ActivityMainBinding
import com.example.githubuserapps2.Respons.ModelUser
import com.example.githubuserapps2.ViewModel.UserViewModel
import android.view.MenuItem
import com.example.githubuserapps2.adapter.AdapterUser

class MainActivity : AppCompatActivity() {
    private lateinit var vieModel: UserViewModel
    private lateinit var userAdapter: AdapterUser
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.title = resources.getString(R.string.title_home)

        binding.apply {
            bgSearch.visibility = View.VISIBLE
            bgTxtsearch.visibility = View.VISIBLE
        }

        userAdapter = AdapterUser()
        userAdapter.notifyDataSetChanged()

        userAdapter.setOnItemClickCallBack(object : AdapterUser.OnItemClickCallBack {
            override fun onItemClick(data: ModelUser) {
                Intent(this@MainActivity, DetailActivityUsers::class.java).also {
                    it.putExtra(DetailActivityUsers.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailActivityUsers.EXTRA_ID, data.id)
                    it.putExtra(DetailActivityUsers.EXTRA_AVATAR, data.avatar_url)
                    it.putExtra(DetailActivityUsers.EXTRA_TYPE, data.type)
                    startActivity(it)
                }
            }

        })
        vieModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserViewModel::class.java)


        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = userAdapter

            search.setOnClickListener {
                searcUser()
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                binding.apply {
                    bgSearch.visibility = View.GONE
                    bgTxtsearch.visibility = View.GONE
                }
            }

            query.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searcUser()
                    binding.apply {
                        bgSearch.visibility = View.GONE
                        bgTxtsearch.visibility = View.GONE
                    }
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        vieModel.getSearchUser().observe(this, {
            if (it != null) {
                userAdapter.setUserList(it)
                showLoading(false)
                binding.apply {
                    bgSearch.visibility = View.GONE
                    bgTxtsearch.visibility = View.GONE
                }
            }
        })
    }

    private fun searcUser() {
        binding.apply {
            val queryKey = query.text.toString()
            if (queryKey.isEmpty()) return
            showLoading(true)
            vieModel.setSearchUser(queryKey)
        }
    }

    private fun showLoading(state: Boolean) {
        binding.apply {
            if (state) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(itemId: Int) {
        when (itemId) {
            R.id.settings -> {
                val mIntent = Intent(this, SettingsActivity::class.java)
                startActivity(mIntent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            R.id.fav -> {
                val mIntent = Intent(this, FavoriteActivity::class.java)
                startActivity(mIntent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }

    }
}


