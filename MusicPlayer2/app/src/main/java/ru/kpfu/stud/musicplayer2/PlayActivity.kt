package ru.kpfu.stud.musicplayer2

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_play.*
import ru.kpfu.stud.musicplayer2.SongRepository.idPlayNow
import ru.kpfu.stud.musicplayer2.SongRepository.idPlayOld
import ru.kpfu.stud.musicplayer2.SongRepository.songRepository

class PlayActivity : AppCompatActivity() {
    private var totalTime: Int = 0
    private var songService: IPlayer? = null
    private var song: Song? = songService?.song
    private var isChange: Boolean = false

    private val aidlConnection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            Log.e("LOG_TAG", "MainActivity onServiceConnected")
            songService = IPlayer.Stub.asInterface(service)
            songService?.setListener(object : SongCallback.Stub() {
                @Throws
                override fun isFinish() {
                    updateUI()
                    onSeekBarChangeTime()
                    Log.e("PlayActivity", "next")
                }
            })
            if (idPlayNow != idPlayOld) {
                idPlayOld = idPlayNow
                songService?.play(song)
            } else {
                song = songRepository[idPlayNow]
                changeAttributes()
            }
            onSeekBarChangeTime()
        }

        override fun onServiceDisconnected(className: ComponentName) {
            Log.e("LOG_TAG", "MainActivity onServiceDisconnected")
            songService = null
        }

    }

    private fun changeSong() {
        val id = Integer.parseInt(intent.getStringExtra("ID")!!)
        if (idPlayOld != id) {
            idPlayNow = id
            song = songRepository[id]
            changeAttributes()
        }
    }

    private fun updateUI() {
        idPlayNow = Integer.parseInt(songService?.song?.id!!)
        song = songRepository[idPlayNow]
        changeAttributes()
    }

    private fun changeAttributes() {
        author_play.text = song?.author
        title_play.text = song?.title
        song?.image?.let { image_play.setBackgroundResource(it) }
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, SongService::class.java)
        bindService(intent, aidlConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        songService?.let {
            unbindService(aidlConnection)
            songService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        if (intent.hasExtra("ID")) {
            changeSong()
            songService?.setCurrentSong(Song().apply {
                songRepository[idPlayNow]
            })
            songService?.setCurrentSongFromBundle(Bundle().apply {
                putParcelable("key_song", Song().apply {
                    songRepository[idPlayNow]
                })
            })
        }
        statement.setOnClickListener {
            if (songService?.isPlaying == true) {
                songService?.pause()
                statement.setBackgroundResource(R.drawable.ic_play)
            } else {
                statement.setBackgroundResource(R.drawable.ic_baseline_pause_24)
                songService?.continuaion()
            }
        }
        next.setOnClickListener {
            statement.setBackgroundResource(R.drawable.ic_baseline_pause_24)
            isChange = true
            songService?.next()
            isChange = false
            updateUI()
            onSeekBarChangeTime()
        }
        back.setOnClickListener {
            statement.setBackgroundResource(R.drawable.ic_baseline_pause_24)
            isChange = true
            songService?.previous()
            isChange = false
            updateUI()
            onSeekBarChangeTime()
        }
        onSeekBarChangeVolume()
    }

    private fun onSeekBarChangeVolume() {
        seek_bar_volume.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        val volumeNum = progress / 100.0f
                        songService?.setVolumePlayer(volumeNum)
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }
            }
        )
    }

    private fun onSeekBarChangeTime() {
        totalTime = songService?.duration!!
        seek_bar_music.max = totalTime
        seek_bar_music.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) songService?.seek(progress)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }

            }
        )
        Thread {
            while (songService?.isPlaying == true && !isChange) {
                try {
                    var msg = Message()
                    msg.what = songService?.currentTime!!
                    handler.sendMessage(msg)
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {

                }
            }
        }.start()
    }

    @SuppressLint("HandlerLeak")
    var handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            var currentPosition = msg.what
            seek_bar_music.progress = currentPosition
            var elapsedTime = createTimeLabel(currentPosition)
            elapsedTimeLabel.text = elapsedTime
            var remainingTime = createTimeLabel(totalTime - currentPosition)
            remainingTimeLabel.text = "-$remainingTime"
        }
    }

    fun createTimeLabel(time: Int): String {
        var timeLabel: String
        var min = time / 1000 / 60
        var sec = time / 1000 % 60
        timeLabel = "$min:"
        if (sec < 10) timeLabel += "0"
        timeLabel += sec
        return timeLabel
    }

}