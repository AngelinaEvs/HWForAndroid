package ru.kpfu.stud.musicplayer2

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat

class NotificationMediaPlayer(val context: Context) {
    private val CHANNEL_ID = "MP"
    private val notificationId = 1
    private var notificationManager: NotificationManager? = null
    private var nBuilder: NotificationCompat.Builder
    private var songService: IPlayer? = null

    init {
        notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "my_name"
            val descr = "ntf_ch"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =
                notificationManager?.getNotificationChannel(CHANNEL_ID) ?: NotificationChannel(
                    CHANNEL_ID,
                    name,
                    importance
                ).apply {
                    description = descr
                }
            notificationManager?.createNotificationChannel(channel)
        }

        val toActivity = Intent(context, PlayActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 5, toActivity, PendingIntent.FLAG_UPDATE_CURRENT)

        val previousIntent = Intent(context, SongService::class.java).apply {
            songService?.previous()
        }

        val pauseIntent = Intent(context, SongService::class.java).apply {
            songService?.play(songService?.song)
        }

        val nextIntent = Intent(context, SongService::class.java).apply {
            songService?.next()
        }
        val previousPendingIntent = PendingIntent.getService(context, 0, previousIntent, 0)
        val pausePendingIntent = PendingIntent.getService(context, 1, pauseIntent, 0)
        val nextPendingIntent = PendingIntent.getService(context, 2, nextIntent, 0)

        nBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_play)
                .addAction(R.drawable.ic_rew, "Previous", previousPendingIntent)
                .addAction(R.drawable.ic_baseline_pause_24, "Pause", pausePendingIntent)
                .addAction(R.drawable.ic_ff, "Next", nextPendingIntent )
            .setContentIntent(pendingIntent)
            .setNotificationSilent()
    }

    fun build(songId: Int) {
        val song = SongRepository.songRepository[songId]
        val cover = BitmapFactory.decodeResource(context.resources, song.image)
        val builder = nBuilder
            .setContentText(song.author)
            .setContentTitle(song.title)
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle())
            .setLargeIcon(cover)
        val notification: Notification = builder.build()
        notificationManager?.notify(notificationId, notification)
    }

    fun cancel() = notificationManager?.cancelAll()
}