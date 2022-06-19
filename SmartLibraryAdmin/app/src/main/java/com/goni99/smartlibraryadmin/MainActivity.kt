package com.goni99.smartlibraryadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.goni99.smartlibraryadmin.databinding.ActivityMainBinding
import com.goni99.smartlibraryadmin.mqtt.MyMqtt
import com.goni99.smartlibraryadmin.recyclerview.BarcodeRecyclerViewAdapter
import com.goni99.smartlibraryadmin.utils.MQTT
import org.eclipse.paho.client.mqttv3.MqttMessage
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    val subTopic = "iot/#"
    var myMqtt : MyMqtt? = null

    private lateinit var adapter: BarcodeRecyclerViewAdapter
    private lateinit var barcodeList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        barcodeList = ArrayList<String>()

        myMqtt = MyMqtt(this, MQTT.SERVER_URI)
        myMqtt?.mySetCallback(::onReceived)
        myMqtt?.connect(arrayOf(subTopic))

        adapter = BarcodeRecyclerViewAdapter()
        adapter.setBarcodeList(barcodeList)
        binding.bookReturnStatusRecyclerView.adapter = adapter
        binding.bookReturnStatusRecyclerView.layoutManager = LinearLayoutManager(this)

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

        binding.toggleButton.addOnButtonCheckedListener { toggleButton, checkedId, isChecked ->
            when (checkedId){
                R.id.force_belt_start_btn -> {
                    myMqtt?.publish("iot/belt", "force/start")
                    binding.conveyorBeltStatusSwitch.isChecked = true
                    binding.conveyorBeltStatusTextView.text = "작동 중"
                }
                R.id.force_belt_stop_btn -> {
                    myMqtt?.publish("iot/belt", "force/stop")
                    binding.conveyorBeltStatusSwitch.isChecked = false
                    binding.conveyorBeltStatusTextView.text = "멈춤"
                }
            }

        }
    }
    fun onReceived(topic:String, message: MqttMessage) {
        val msg = String(message.payload)
        Log.d("mymqtt", "onReceived topic : $topic, msg : $msg")

        when (topic){
            "iot/barcode" -> {
                barcodeList.add(msg)
                adapter.notifyDataSetChanged()
            }
            "iot/belt" -> {
                if (msg == "start"){
                    binding.conveyorBeltStatusSwitch.isChecked = true
                    binding.conveyorBeltStatusTextView.text = "작동 중"
                } else if (msg == "stop"){
                    binding.conveyorBeltStatusSwitch.isChecked = false
                    binding.conveyorBeltStatusTextView.text = "멈춤"
                } else if (msg == "error"){
                    thread {
                        runOnUiThread {
                            binding.errorLayout.visibility = View.VISIBLE
                        }
                        Thread.sleep(2000)
                        runOnUiThread {
                            binding.errorLayout.visibility = View.INVISIBLE
                        }
                    }
                }

            }
            "iot/RGB_val" -> {
                val RGBlist = msg.split("/")
                binding.rgbLedColorRData.text = RGBlist[0]
                binding.rgbLedColorGData.text = RGBlist[1]
                binding.rgbLedColorBData.text = RGBlist[2]
            }
            "iot/RGB" -> {
                if (msg == "stop"){
                    binding.RGBLight.setImageResource(R.drawable.ic_baseline_highlight_24)
                } else {
                    val kdc = msg.toInt()
                    when(kdc){
                        in 0 until 100 -> {
                            binding.RGBLight.setImageResource(R.drawable.light_000)
                            binding.rgbLedColorStatusImageView.setImageResource(R.drawable.adb_000)
                        }
                        in 100 until 200 -> {
                            binding.RGBLight.setImageResource(R.drawable.light_100)
                            binding.rgbLedColorStatusImageView.setImageResource(R.drawable.adb_100)
                        }
                        in 200 until 300 -> {
                            binding.RGBLight.setImageResource(R.drawable.light_200)
                            binding.rgbLedColorStatusImageView.setImageResource(R.drawable.adb_200)
                        }
                        in 300 until 400 -> {
                            binding.RGBLight.setImageResource(R.drawable.light_300)
                            binding.rgbLedColorStatusImageView.setImageResource(R.drawable.adb_300)
                        }
                        in 400 until 500 -> {
                            binding.RGBLight.setImageResource(R.drawable.light_400)
                            binding.rgbLedColorStatusImageView.setImageResource(R.drawable.adb_400)
                        }
                        in 500 until 600 -> {
                            binding.RGBLight.setImageResource(R.drawable.light_500)
                            binding.rgbLedColorStatusImageView.setImageResource(R.drawable.adb_500)
                        }
                        in 600 until 700 -> {
                            binding.RGBLight.setImageResource(R.drawable.light_600)
                            binding.rgbLedColorStatusImageView.setImageResource(R.drawable.adb_600)
                        }
                        in 700 until 800 -> {
                            binding.RGBLight.setImageResource(R.drawable.light_700)
                            binding.rgbLedColorStatusImageView.setImageResource(R.drawable.adb_700)
                        }
                        in 800 until 900 -> {
                            binding.RGBLight.setImageResource(R.drawable.light_800)
                            binding.rgbLedColorStatusImageView.setImageResource(R.drawable.adb_800)
                        }
                        in 900 until 1000 -> {
                            binding.RGBLight.setImageResource(R.drawable.light_900)
                            binding.rgbLedColorStatusImageView.setImageResource(R.drawable.adb_900)
                        }
                    }
                }
            }
            "iot/return_kdc" -> {
                val returnKDC = msg
                val KDC = returnKDC.split(".")[0].toInt()
            }
            "iot/laser" -> {
                when (msg){
                   "1" -> {
                       thread {
                           runOnUiThread {
                               binding.line1Object.visibility = View.VISIBLE
                           }
                           Thread.sleep(1000)
                           runOnUiThread {
                               binding.line1Object.visibility = View.INVISIBLE
                           }
                       }
                   }
                    "2" -> {
                        thread {
                            runOnUiThread {
                                binding.line2Object.visibility = View.VISIBLE
                            }
                            Thread.sleep(1000)
                            runOnUiThread {
                                binding.line2Object.visibility = View.INVISIBLE
                            }
                        }
                    }
                }
            }
            "iot/led" -> {
                when (msg){
                    "blue" -> {
                        binding.beltStatusCorrect.setImageResource(R.drawable.circle_blue)
                        binding.beltStatusError.setImageResource(R.drawable.ic_baseline_circle_24)
                    }
                    "red" -> {
                        binding.beltStatusCorrect.setImageResource(R.drawable.ic_baseline_circle_24)
                        binding.beltStatusError.setImageResource(R.drawable.circle_red)
                    }
                }
            }
        }
        // 바코드 topic = iot/barcode
        // 분류 박스 카운팅 = iot/box1/count | iot/box2/count | iot/box3/count
        // 분류 박스 on / off = iot/box1 | iot/box2 | iot/box3
        // 컨베이어 벨트 topic = iot/belt
        // RGB LED topic = iot/RGB
    }

}