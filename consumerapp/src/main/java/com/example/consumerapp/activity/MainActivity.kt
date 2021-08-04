package com.example.consumerapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.consumerapp.R
import com.example.consumerapp.adapter.AdapterUser
import com.example.consumerapp.databinding.ActivityMainBinding
import com.example.consumerapp.model.ModelUser
import com.example.consumerapp.viewModel.FavoriteViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var userAdapter : AdapterUser
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.title = resources.getString(R.string.user_favorite)

        userAdapter = AdapterUser()
        userAdapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)


        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.adapter = userAdapter
        }

        viewModel.setFavoriteUser(this)

        viewModel.getFavorite()?.observe(this,{
            if (it!=null){
                userAdapter.setUserList(it)
            }
        })

        userAdapter.setOnItemClickCallBack(object : AdapterUser.OnItemClickCallBack {
            override fun onItemClick(data: ModelUser) {
                    Toast.makeText(this@MainActivity, "Nama Username : " + data.login, Toast.LENGTH_SHORT).show()
                }
        })
    }
}