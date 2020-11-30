package ru.kpfu.stud.musicplayer2

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_list.*

class MusicListActivity : AppCompatActivity() {
    private var songService: IPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        to_mp.setOnClickListener {
            if (songService?.isPlaying == true) {
                val intent = Intent(this, PlayActivity::class.java)
                startActivity(intent)
            } else Toast.makeText(this, "Плеер не запущен", Toast.LENGTH_SHORT).show()
        }
        val adapter = SongAdapter(SongRepository.songRepository.toList()) {
                id ->
            val intent = Intent(this, PlayActivity::class.java)
            intent.putExtra("ID", id)
            startActivity(intent)
        }
        rv_playlist.adapter = adapter
        view.setOnClickListener {
            intent = null
            SongRepository.idPlayOld = -1
            SongRepository.idPlayNow = -1
            songService?.stop()
        }
    }

    private val aidlConnection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            Log.e("LOG_TAG", "MainActivity onServiceConnected")
            songService = IPlayer.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(className: ComponentName) {
            Log.e("LOG_TAG", "MainActivity onServiceDisconnected")
            songService = null
        }

    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, SongService::class.java)
        bindService(intent, aidlConnection, Context.BIND_AUTO_CREATE)
    }
}