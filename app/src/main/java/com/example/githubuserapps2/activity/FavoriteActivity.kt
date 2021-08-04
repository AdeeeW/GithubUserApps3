package com.example.githubuserapps2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapps2.R
import com.example.githubuserapps2.Respons.ModelUser
import com.example.githubuserapps2.ViewModel.FavoriteViewModel
import com.example.githubuserapps2.adapter.AdapterUser
import com.example.githubuserapps2.database.entity.UserFavorite
import com.example.githubuserapps2.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity(){

    private lateinit var userAdapter : AdapterUser
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.title = resources.getString(R.string.user_favorite)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        userAdapter = AdapterUser()
        userAdapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        userAdapter.setOnItemClickCallBack(object : AdapterUser.OnItemClickCallBack{
            override fun onItemClick(data: ModelUser) {
                Intent(this@FavoriteActivity, DetailActivityUsers::class.java).also {
                    it.putExtra(DetailActivityUsers.EXTRA_AVATAR, data.avatar_url)
                    it.putExtra(DetailActivityUsers.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailActivityUsers.EXTRA_TYPE, data.type)
                    it.putExtra(DetailActivityUsers.EXTRA_ID, data.id)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUser.adapter = userAdapter
        }

        viewModel.getFavorite()?.observe(this,{
            if (it!=null){
                val list = mapList(it)
                userAdapter.setUserList(list)
            }
        })
    }

    private fun mapList(it: List<UserFavorite>): ArrayList<ModelUser> {
        val listUser = ArrayList<ModelUser>()
        for (user in it){
            val userMap = ModelUser(
                    user.id,
                    user.avatar_url,
                    user.login,
                    user.type
            )
            listUser.add(userMap)
        }
        return listUser
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.settings, menu)
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
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        return true
    }
}