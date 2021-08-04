package com.example.githubuserapps2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuserapps2.R
import com.example.githubuserapps2.ViewModel.DetailUserViewModel
import com.example.githubuserapps2.adapter.SectionsPagerAdapter
import com.example.githubuserapps2.databinding.ActivityDetailUsersBinding
import com.example.githubuserapps2.transformpage.ZoomOutPageTransformer
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivityUsers : AppCompatActivity() {
    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "avatar_url"
        const val EXTRA_TYPE = "extra_type"
        private val TITLE_TABS = intArrayOf(R.string.followers, R.string.following)
    }

    private lateinit var binding: ActivityDetailUsersBinding
    private lateinit var vieModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.title = resources.getString(R.string.detail_favorite)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.getIntExtra(EXTRA_ID, 0)
        val username = intent.getStringExtra(EXTRA_USERNAME)
        val avatar = intent.getStringExtra(EXTRA_AVATAR)
        val type = intent.getStringExtra(EXTRA_TYPE)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        vieModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)
        if (username != null) {
            vieModel.setUserDetail(username)
            vieModel.getUserDetails().observe(this, {
                if (it != null) {
                    binding.apply {
                        if (it.name == null){
                            tvNama.text = getString(R.string.name_not_found)
                        }else{
                            tvNama.text = it.name
                        }
                        tvUsername.text =resources.getString(R.string.edd)+"${it.login}"
                        if (it.location == null){
                            tvLocation.text = "-"
                        }else{
                            tvLocation.text = it.location
                        }
                        tvFollwers.text = "${it.followers}"
                        tvFollwing.text = "${it.following}"
                        tvRepos.text = "${it.public_repos}"
                        Glide.with(this@DetailActivityUsers)
                            .load(it.avatar_url)
                            .apply(RequestOptions.circleCropTransform().placeholder(R.drawable.ic_account_circle))
                            .into(img)
                    }
                }
            })

            var _checkFav = false
            CoroutineScope(Dispatchers.IO).launch {
                val count = vieModel.checkUser(id)
                withContext(Dispatchers.Main){
                    if (count != null){
                        if (count > 0){
                            binding.fav.isChecked = true
                            _checkFav = true
                        }else{
                            binding.fav.isChecked = false
                            _checkFav = false
                        }
                    }
                }
            }

            binding.fav.setOnClickListener {
                _checkFav = !_checkFav
                if (_checkFav){
                    vieModel.addFavToDatabase(id, username, avatar, type)
                    Toast.makeText(this, getString(R.string.successfully_added), Toast.LENGTH_SHORT).show()
                }else{
                    vieModel.deleteFavorite(id)
                    Toast.makeText(this, getString(R.string.successfully_remove), Toast.LENGTH_SHORT).show()
                }
                binding.fav.isChecked = _checkFav
            }

            val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
            binding.apply {
                viewPager.adapter = sectionsPagerAdapter
                viewPager.setPageTransformer(ZoomOutPageTransformer())
                TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
                    tab.text = resources.getString(TITLE_TABS[position])
                }.attach()
            }
        }
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
