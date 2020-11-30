package ru.kpfu.stud.musicplayer2

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import java.lang.Thread.sleep
import java.time.temporal.TemporalAdjusters.next

class SongService : Service() {
    private var mediaPlayer: MediaPlayer = MediaPlayer()
    private var callback: SongCallback? = null
    private lateinit var serviceSong: Song
    private lateinit var ntf: NotificationMediaPlayer

    private val mBinder: IPlayer.Stub = object : IPlayer.Stub() {
        override fun play(song: Song) {
            //mediaPlayer = MediaPlayer()
            serviceSong = song
            ntf = NotificationMediaPlayer(applicationContext).apply {
                build(0)
            }
            playLocalMusic()
        }

        override fun isPlaying(): Boolean = this@SongService.isPlaying()

        override fun pause() = this@SongService.pause()

        override fun getSong(): Song = serviceSong

        override fun getCurrentTime(): Int = this@SongService.currentTime()

        override fun getDuration(): Int = this@SongService.getDuration()

        override fun seek(currentPosition: Int) = this@SongService.seek(currentPosition)

        override fun continuaion() = update()

        override fun next() = this@SongService.next()

        override fun stop() = this@SongService.stop()

        override fun previous() = this@SongService.previous()

        override fun isEnd(): Boolean = this@SongService.isEnd()

        override fun setVolumePlayer(volumeNum: Float) = mediaPlayer.setVolume(volumeNum, volumeNum)

        override fun setCurrentSong(song: Song?) = showSong(song)

        override fun setCurrentSongFromBundle(bundle: Bundle) {
            with(bundle) {
                classLoader = this@SongService.classLoader
                getParcelable<Song>("key_song").also {
                    showSong(it)
                }
            }
        }

        override fun setListener(callback: SongCallback?) {
            this@SongService.callback = callback
        }

    }

    override fun onBind(intent: Intent): IBinder = mBinder

    private fun showSong(song: Song?) {
        Log.e("Song from activity", song?.author.toString())
    }

    private fun playLocalMusic() {
        if (mediaPlayer.isPlaying) mediaPlayer.stop()
        mediaPlayer = MediaPlayer.create(applicationContext, serviceSong.mp3)
        mediaPlayer.setVolume(0.5f, 0.5f)
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {
            next()
            callback?.isFinish()
        }
        ntf.build(Integer.parseInt(serviceSong.id))
    }

    private fun update() = mediaPlayer.start()

    private fun next() {
        pause()
        val newId: Int
        var currentId = Integer.parseInt(serviceSong.id)
        if (currentId < SongRepository.songRepository.size - 1) newId = currentId + 1
        else newId = 0
        serviceSong = SongRepository.songRepository[newId]
        mediaPlayer = MediaPlayer.create(applicationContext, serviceSong.mp3)
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {
            next()
            callback?.isFinish()
        }
        ntf.build(Integer.parseInt(serviceSong.id))
    }

    private fun previous() {
        pause()
        val newId: Int
        var currentId = Integer.parseInt(serviceSong.id)
        if (currentId != 0) newId = currentId - 1
        else newId = SongRepository.songRepository.size - 1
        serviceSong = SongRepository.songRepository[newId]
        mediaPlayer = MediaPlayer.create(applicationContext, serviceSong.mp3)
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {
            next()
            callback?.isFinish()
        }
        ntf.build(Integer.parseInt(serviceSong.id))
    }

    private fun currentTime(): Int = mediaPlayer.currentPosition

    private fun seek(currentPosition: Int) = mediaPlayer.seekTo(currentPosition)

    private fun pause() = mediaPlayer.pause()

    private fun isPlaying() = mediaPlayer.isPlaying

    private fun stop() {
        mediaPlayer.stop()
        ntf.cancel()
    }

    private fun getDuration() = mediaPlayer.duration

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    private fun isEnd(): Boolean {
        if (!isPlaying()) return true
        return false
        }

}