package com.yifeplayte.mono.service

import android.content.Intent
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService

class MonoTileService : TileService() {
    override fun onClick() {
        when (qsTile.state) {
            Tile.STATE_INACTIVE -> {
                start()
                qsTile.state = Tile.STATE_ACTIVE
            }

            Tile.STATE_ACTIVE -> {
                stop()
                qsTile.state = Tile.STATE_INACTIVE
            }
        }
        qsTile.updateTile()
    }

    override fun onStartListening() {
        refresh()
    }

    private fun refresh() {
        qsTile.state = if (MonoService.isAlive) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        qsTile.updateTile()
    }

    private fun start() {
        startService(Intent(this, MonoService::class.java))
    }

    private fun stop() {
        stopService(Intent(this, MonoService::class.java))
    }
}