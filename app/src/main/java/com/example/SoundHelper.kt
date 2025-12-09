package com.example

    import android.content.Context
    import android.media.MediaPlayer
    import com.example.studify_app.R

object SoundHelper {
        fun playEndSound(context: Context) {
            val player = MediaPlayer.create(context, R.raw.timer_end)
            player.start()
            player.setOnCompletionListener { it.release() }
        }
    }
