package com.goni99.smartlibrarysystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.goni99.smartlibrarysystem.databinding.ActivityMainBinding
import com.goni99.smartlibrarysystem.network.MyMqtt
import com.goni99.smartlibrarysystem.view.SubActivity
import org.eclipse.paho.client.mqttv3.MqttMessage

class MainActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    val subTopic = "iot/#"
    val serverUri = "tcp://192.168.35.159:1883" // broker의 ip 와 port
    var myMqtt : MyMqtt? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        myMqtt = MyMqtt(this, serverUri)
        myMqtt?.mySetCallback(::onReceived)
        myMqtt?.connect(arrayOf(subTopic))


    }

    fun onReceived(topic:String, message: MqttMessage) {
        val msg = String(message.payload)
        Log.d("mymqtt", "onReceived topic : $topic, msg : $msg")

    }
}