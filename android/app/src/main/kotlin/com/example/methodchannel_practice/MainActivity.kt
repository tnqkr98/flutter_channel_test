package com.example.methodchannel_practice

import android.hardware.SensorManager
import android.os.Bundle
import io.flutter.embedding.android.FlutterActivity

class MainActivity: FlutterActivity() {
    companion object{
        const val METHOD_CHANNEL: String = "com.example.methodchannel_practice/methodChannel"
        const val EVENT_CHANNEL: String = "com.example.methodchannel_practice/eventChannel"
        const val CALL_REQUEST_CODE = 101
    }

    private lateinit var mSensorManager: SensorManager
    private lateinit var

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
}
