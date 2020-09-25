package com.example.foodrunner2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodrunner2.R
import org.json.JSONObject

class Forgot_passwordActivity : AppCompatActivity() {
    lateinit var emailEditText: EditText
    lateinit var phoneEditText: EditText
    lateinit var forgotButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        emailEditText = findViewById(R.id.forgot_email)
        phoneEditText = findViewById(R.id.forgot_number)
        forgotButton = findViewById(R.id.forgot_button)

        forgotButton.setOnClickListener {
            if(!emailEditText.text.endsWith("@gmail.com")) emailEditText.setError("enter valid email address")
            if(phoneEditText.text.length<8) phoneEditText.setError("enter valid phone number")
            else {
                getOtp()
            }
        }


    }
    fun getOtp(){
        val queue = Volley.newRequestQueue(this)
        val url = "http://13.235.250.119/v2/forgot_password/fetch_result"

        val params = hashMapOf<String,String>()
        params["mobile_number"] = phoneEditText.text.toString()
        params["email"] = emailEditText.text.toString()


        val jsonrequest = object : JsonObjectRequest(
            Request.Method.POST,
            url,
            JSONObject(params as Map<String,String>),
            Response.Listener {
                //this is response listener...
                val success = it.getJSONObject("data").getBoolean("success")
                if (success) {
                    val first_try = it.getJSONObject("data").getBoolean("first_try")
                    if(!first_try){
                        Toast.makeText(this,"use the same otp within 24 hours",Toast.LENGTH_SHORT).show()
                    }
                    val intent = Intent(this,SetNewPaasword::class.java)
                    intent.putExtra("phone",phoneEditText.text.toString())
                    startActivity(intent)
                    finish()

                } else {
                    Toast.makeText(
                        this,
                        it.getJSONObject("data").getString("errorMessage"),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            Response.ErrorListener {
                //this is response error listener....
                Toast.makeText(this,"response error", Toast.LENGTH_SHORT).show()

            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val header = HashMap<String, String>()
                header["token"] = "24958f6a8141aa"
                header["content-type"] = "application/json"
                return header
            }
        }
        queue.add(jsonrequest)
    }
}
