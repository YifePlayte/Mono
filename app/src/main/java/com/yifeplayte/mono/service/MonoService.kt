package com.yifeplayte.mono.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.IBinder
import com.yifeplayte.mono.R

class MonoService : Service() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate() {
        INSTANCE = this
        mediaPlayer = MediaPlayer.create(this, R.raw.silent).apply {
            isLooping = true
            start()
        }
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.mode = AudioManager.MODE_NORMAL
        audioManager.isBluetoothScoOn = true
        audioManager.startBluetoothSco()
        audioManager.mode = AudioManager.MODE_IN_COMMUNICATION
    }

    override fun onDestroy() {
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.mode = AudioManager.MODE_NORMAL
        audioManager.stopBluetoothSco()
        audioManager.isBluetoothScoOn = false
        mediaPlayer?.apply {
            stop()
            release()
        }
        mediaPlayer = null
        INSTANCE = null
    }

    override fun onBind(intent: Intent?): IBinder? = null

    companion object {
        private var INSTANCE: MonoService? = null
        val isAlive get() = INSTANCE != null
    }
}