package com.example.githubuserapps2.receiver

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.githubuserapps2.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ReminderReceiver : BroadcastReceiver() {
    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "channel_01"
        private const val CHANNEL_NAME = "Github Reminder"
        const val EXTRA_MESSAGE = "message"
        const val EXTRA_TYPE = "type"
        private const val TIME_FORMAT = "HH:mm"
        private const val ID_REPEATING = 101
    }

    override fun onReceive(context: Context, intent: Intent) {
       senNotification(context)
    }

    fun senNotification(context: Context){
        val intent = context?.packageManager.getLaunchIntentForPackage("com.example.githubuserapps2")
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val mNotificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notifications_white)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_notifications_white))
                .setContentTitle(context.resources.getString(R.string.title_notif))
                .setContentText(context.getString(R.string.text_notif))
                .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)

            mBuilder.setChannelId(CHANNEL_ID)

            mNotificationManager.createNotificationChannel(channel)
        }

        val notification = mBuilder.build()

        mNotificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun isDateInvalid(time: String, timeFormat: String): Boolean {
        return try {
            val df = SimpleDateFormat(timeFormat, Locale.getDefault())
            df.isLenient = false
            df.parse(time)
            false
        }catch (e : ParseException){
            true
        }
    }

    fun setRepeatingAlarm(context: Context, type: String, time: String, message: String){
        if (isDateInvalid(time, TIME_FORMAT)) return
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, ReminderReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_TYPE, type)
        val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)

        Toast.makeText(context, context.getString(R.string.repeat_alarm_on), Toast.LENGTH_SHORT).show()
    }

    fun cancelAlarm(context: Context){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java)
        val requestCode = ID_REPEATING
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, context.getString(R.string.repeat_alarm_off), Toast.LENGTH_SHORT).show()
    }
}