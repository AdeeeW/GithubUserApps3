package com.example.githubuserapps2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.example.githubuserapps2.R
import com.example.githubuserapps2.databinding.ActivitySettingsBinding
import com.example.githubuserapps2.preference.ReminderPreference
import com.example.githubuserapps2.preference.model.ModelPreference
import com.example.githubuserapps2.receiver.ReminderReceiver

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var reminderReceiver: ReminderReceiver
    private lateinit var modelPreference: ModelPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeLanguageSetting()

        val actionBar = supportActionBar
        actionBar?.title = resources.getString(R.string.title_setting)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val remindPref = ReminderPreference(this)
        if (remindPref.getPreference().preferenceReminder) {
            binding.swReminder.isChecked = true
        } else {
            binding.swReminder.isChecked = false
        }

        reminderReceiver = ReminderReceiver()
        binding.swReminder.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                saveReminder(true)
                reminderReceiver.setRepeatingAlarm(
                    this,
                    "RepeatingAlarm",
                    "09:00",
                    "Github Reminder"
                )
            } else {
                saveReminder(false)
                reminderReceiver.cancelAlarm(this)
            }
        }
    }

    private fun saveReminder(b: Boolean) {
        val remindPref = ReminderPreference(this)
        modelPreference = ModelPreference()

        modelPreference.preferenceReminder = b
        remindPref.setPreference(modelPreference)
    }

    private fun changeLanguageSetting() {
        binding.groupLanguageSetting.setOnClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        return true
    }
}