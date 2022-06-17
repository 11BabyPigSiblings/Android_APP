package com.goni99.smartlibraryadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.goni99.smartlibraryadmin.databinding.ActivityMainBinding
import com.goni99.smartlibraryadmin.mqtt.MyMqtt
import com.goni99.smartlibraryadmin.utils.MQTT
import org.eclipse.paho.client.mqttv3.MqttMessage

class MainActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    val subTopic = "iot/#"
    var myMqtt : MyMqtt? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        myMqtt = MyMqtt(this, MQTT.SERVER_URI)
//        myMqtt?.mySetCallback(::onReceived)
//        myMqtt?.connect(arrayOf(subTopic))

        binding.topAppBar.setNavigationOnClickListener {1
            binding.mainLayout.visibility = View.VISIBLE
            binding.dataViewLayout.visibility = View.INVISIBLE
        }

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId){
                R.id.sensor_data_menu -> {
                    binding.mainLayout.visibility = View.INVISIBLE
                    binding.dataViewLayout.visibility = View.VISIBLE
                    true
                }
                else -> false
            }
        }
    }
    fun onReceived(topic:String, message: MqttMessage) {
        val msg = String(message.payload)
        Log.d("mymqtt", "onReceived topic : $topic, msg : $msg")
        // 바코드 topic = iot/barcode
        // 분류 박스 카운팅 = iot/box1/count | iot/box2/count | iot/box3/count
        // 분류 박스 on / off = iot/box1 | iot/box2 | iot/box3
        // 컨베이어 벨트 topic = iot/belt
        // RGB LED topic = iot/RGB
    }
}