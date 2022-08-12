package com.merryblue.myvoicealarm.ui.addalarm

import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Recorder @Inject constructor(@ApplicationContext private val context: Context) {

    private val recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        MediaRecorder(context)
    } else {
        MediaRecorder()
    }

    fun startRecording(id: Int?) {
        if (context.packageManager!!.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)) {
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            // aac example
            recorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            // mp4 example
//        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
//        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
            recorder.setOutputFile(filePathForId(1))
            recorder.prepare()
            recorder.start()
        }
    }

    fun stopRecording() {
        recorder.stop()
    }

    fun release() = recorder.release()

    private fun filePathForId(id: Int?): String {
        return context.getExternalFilesDir(null)!!.absolutePath + "$id.aac"
    }

}