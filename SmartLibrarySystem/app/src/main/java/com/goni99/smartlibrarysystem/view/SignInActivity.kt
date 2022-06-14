package com.goni99.smartlibrarysystem.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.goni99.smartlibrarysystem.databinding.ActivitySignInBinding
import com.goni99.smartlibrarysystem.utils.API
import com.goni99.smartlibrarysystem.utils.Constants.TAG
import com.goni99.smartlibrarysystem.utils.onMyTextChanged
import com.google.android.material.textfield.TextInputLayout
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import kotlin.concurrent.thread

class SignInActivity : AppCompatActivity() {
    val binding by lazy {
        ActivitySignInBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Log.d(TAG, "SignInActivity - onCreate() called")

        binding.signInIdEditText.onMyTextChanged {
            setEditText(it.toString().count(), binding.signInIdTextLayout, "아이디를 입력해주세요")
            if (it.toString().count() == 12){
                Toast.makeText(this, "검색어를 12자까지만 입력할 수 있습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.signInPwdEditText.onMyTextChanged {
            setEditText(it.toString().count(), binding.signInPwdTextLayout, "비밀번호를 입력해주세요")
            if (it.toString().count() == 12){
                Toast.makeText(this, "검색어를 12자까지만 입력할 수 있습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.signInButton.setOnClickListener {
            if (binding.signInIdEditText.text.toString().count() == 0){
                Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else if (binding.signInPwdEditText.text.toString().count() == 0){
                Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                thread {
                    val jsonObj =JSONObject()
                    jsonObj.put("boardNo", binding.signInIdEditText.text)
                    jsonObj.put("writer", binding.signInPwdEditText.text)
                    Log.d(TAG, "jsonObj : ${jsonObj}")

                    val client = OkHttpClient()

                    val jsonData = jsonObj.toString()
                    Log.d(TAG, "jsonData : ${jsonData}")

                    val body = jsonData.toRequestBody("application/json".toMediaTypeOrNull())
                    val builder = Request.Builder()
                        .url(API.BASE_URL)
                        .post(body)
                    val request = builder.build()
                    val response: Response = client.newCall(request).execute()
                    val result: String? = response.body?.string()

                    Log.d(TAG, "result : ${result}")
                    runOnUiThread {
                        Toast.makeText(this, result!!, Toast.LENGTH_SHORT).show()
                    }

                }
            }

        }
    }

    fun setEditText(cnt: Int, textLayout: TextInputLayout, inputText:String){
        if (cnt > 0){
            textLayout.helperText = ""
            binding.signInScrollView.scrollTo(0, 500)
        } else {
            textLayout.helperText = inputText
        }
    }
}