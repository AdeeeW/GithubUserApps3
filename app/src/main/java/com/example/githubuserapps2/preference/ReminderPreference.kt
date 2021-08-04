package com.example.githubuserapps2.preference

import android.content.Context
import com.example.githubuserapps2.preference.model.ModelPreference

class ReminderPreference(context: Context) {
    companion object{
        const val NAME_PREFERNCE = "reminder_preference"
        private const val PREFERENCE_REMINDER = "preference_reminder"
    }

    private val preference = context.getSharedPreferences(NAME_PREFERNCE, Context.MODE_PRIVATE)

    fun setPreference(value: ModelPreference){
        val editPref = preference.edit()
        editPref.putBoolean(PREFERENCE_REMINDER, value.preferenceReminder)
        editPref.apply()
    }

    fun getPreference(): ModelPreference{
        val model = ModelPreference()
        model.preferenceReminder = preference.getBoolean(PREFERENCE_REMINDER, false)
        return model
    }
}