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

class SetNewPaasword : AppCompatActivity() {
    lateinit var otpEditText: EditText
    lateinit var newPaasword: EditText
    lateinit var confirmPasswordEditText: EditText
    lateinit var newPasswordButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_new_paasword)
        otpEditText = findViewById(R.id.otp)
        newPaasword = findViewById(R.id.new_password)
        confirmPasswordEditText = findViewById(R.id.confirm_password)
        newPasswordButton = findViewById(R.id.set_new_password_button)

        newPasswordButton.setOnClickListener {
            if (newPaasword.text.length < 8) newPaasword.setError("password length should 8 or more")

            else if (!newPaasword.text.toString()
                    .equals(confirmPasswordEditText.text.toString())
            ) confirmPasswordEditText.setError("password didn't match")

            else {
                setNewPassword()
            }
        }
    }

    fun setNewPassword() {
        val phone = intent.getStringExtra("phone")

        val params = HashMap<String, String>()
        params["mobile_number"] = if (phone != null) phone else "no phone"
        params["otp"] = otpEditText.text.toString()
        params["password"] = newPaasword.text.toString()

        val queue = Volley.newRequestQueue(this)
        val url = "http://13.235.250.119/v2/reset_password/fetch_result"
        val jsonrequest = object : JsonObjectRequest(
            Request.Method.POST,
            url,
            JSONObject(params as Map<String, String>),
            Response.Listener {
                //this is response listener...
                val success = it.getJSONObject("data").getBoolean("success")
                if (success) {

                    val message = it.getJSONObject("data").getString("successMessage")
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, LoginActivity::class.java)
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
                Toast.makeText(this, "response error", Toast.LENGTH_SHORT).show()

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
