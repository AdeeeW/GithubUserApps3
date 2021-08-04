package com.example.githubuserapps2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import com.example.githubuserapps2.databinding.ActivitySpalshScreenBinding

class SpalshScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySpalshScreenBinding
    private var i = 0
    private val delay: Long = 8000
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpalshScreenBinding.inflate(layoutInflater)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(binding.root)

        i = binding.progressBar?.progress
        Thread(Runnable {
            while (i < 100) {
                i += 5
                handler.post(Runnable {
                    binding.progressBar?.progress = i
                    binding.textView?.text = i.toString() + "/" + binding.progressBar?.max
                })
                try {
                    Thread.sleep(400)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        }).start()

        Handler().postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }, delay)
    }
}