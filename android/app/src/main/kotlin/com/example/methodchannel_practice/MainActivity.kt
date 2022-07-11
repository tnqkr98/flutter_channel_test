package com.example.methodchannel_practice

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant

class MainActivity: FlutterActivity() {
    companion object{
        const val METHOD_CHANNEL: String = "com.example.methodchannel_practice/methodChannel"
        const val EVENT_CHANNEL: String = "com.example.methodchannel_practice/eventChannel"
        const val CALL_REQUEST_CODE = 101
    }

    private lateinit var mSensorManager: SensorManager
    private lateinit var mAccelerometer: Sensor

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, METHOD_CHANNEL).setMethodCallHandler { methodCall, result ->
            when (methodCall.method) {
                "getOSVersion" -> getOSVersion(result)
                "getBatteryLevel" -> getBatteryLevel(result)
                else -> result.notImplemented()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //GeneratedPluginRegistrant.registerWith(this)

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        /*MethodChannel(getFlutterView(), METHOD_CHANNEL).setMethodCallHandler { methodCall, result ->
            when (methodCall.method) {
                "getOSVersion" -> getOSVersion(result)
                "isCameraAvailable" -> getOSVersion(result)
            }
        }*/
    }

    private fun getOSVersion(result: MethodChannel.Result){
        val version = Build.VERSION.RELEASE
        if(!version.isNullOrEmpty()){
            result.success(version)
        }
        else{
            result.error("UNAVAILABLE","Version is not available.",null)
        }
    }



    private fun getBatteryLevel(result: MethodChannel.Result) {
        val batteryLevel: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val batteryManager = getSystemService(Context.BATTERY_SERVICE) as BatteryManager
            batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        } else {
            val intent = ContextWrapper(applicationContext).registerReceiver(null, IntentFilter(
                Intent.ACTION_BATTERY_CHANGED))
            intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100 / intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        }

        if (batteryLevel != -1) {
            result.success(batteryLevel)
        } else {
            result.error("UNAVAILABLE", "Battery level not available.", null)
        }
    }
}
