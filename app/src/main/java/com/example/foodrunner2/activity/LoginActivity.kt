package com.example.foodrunner2.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.Volley
import com.example.foodrunner2.R
import com.example.foodrunner2.util.ConnectionManger
import org.json.JSONObject
import java.lang.Exception
import kotlin.math.log

class LoginActivity : AppCompatActivity() {
    lateinit var phone: EditText
    lateinit var password: EditText
    lateinit var loginButton: Button
    lateinit var registerButton: Button
    lateinit var forgotPasswordButton: Button

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences =
            getSharedPreferences(getString(R.string.sharedPreference), Context.MODE_PRIVATE)
        val loggedIn = sharedPreferences.getBoolean(getString(R.string.loggedIn), false)
        if (loggedIn) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        setContentView(R.layout.activity_login)
        phone = findViewById(R.id.login_phone)
        password = findViewById(R.id.login_password)
        loginButton = findViewById(R.id.login_button)
        registerButton = findViewById(R.id.login_to_register)
        forgotPasswordButton = findViewById(R.id.forgot_password)


        forgotPasswordButton.setOnClickListener {
            val intent = Intent(this, Forgot_passwordActivity::class.java)
            startActivity(intent)
            finish()
        }

        loginButton.setOnClickListener {
            if (phone.text.length < 10) phone.setError("enter valid phone number")
            else if (password.text.length < 8) password.setError("enter valid password")
            else {
                loginUser()
            }

        }
        registerButton.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun loginUser() {

        val queue = Volley.newRequestQueue(this)

        val params = hashMapOf<String, String>()
        params["mobile_number"] = phone.text.toString()
        params["password"] = password.text.toString()
        println(params)


        val url = "http://13.235.250.119/v2/login/fetch_result"

        if(ConnectionManger().checkConnectivity(applicationContext as Context)){
            try {
                val jsonObjectRequest = object : JsonObjectRequest(Request.Method.POST, url, JSONObject(
                    params as Map<String, String>
                ), Response.Listener {
                    //this is response listener .....
                    val success = it.getJSONObject("data").getBoolean("success")
                    if (success) {
                        val data = it.getJSONObject("data").getJSONObject("data")

                        sharedPreferences.edit().putString("address", data.getString("address")).apply()
                        sharedPreferences.edit().putString("mobile_number", data.getString("mobile_number"))
                            .apply()
                        sharedPreferences.edit().putString("email", data.getString("email")).apply()
                        sharedPreferences.edit().putString("user_id", data.getString("user_id")).apply()
                        sharedPreferences.edit().putString(getString(R.string.user), data.getString("name"))
                            .apply()
                        sharedPreferences.edit().putBoolean(getString(R.string.loggedIn), true).apply()


                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        Toast.makeText(
                            this,
                            "login failed ${it.getJSONObject("data").getString("errorMessage")}",
                            Toast.LENGTH_SHORT
                        ).show()
                        
                    }

                }, Response.ErrorListener {
                    //this is Error listener.....
                    print(it);
                }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val header = HashMap<String, String>()
                        header["token"] = "24958f6a8141aa"
                        header["content-type"] = "application/json"
                        return header
                    }

                }
                queue.add(jsonObjectRequest);
                }catch (e:Exception){
                println(e)
            }

        }else{
            val dialog = android.app.AlertDialog.Builder(this)
            dialog.setMessage("Internet is not connected!")
            dialog.setPositiveButton("Open Setting") { text, Listener ->
                val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(intent)
                finish()
            }
            dialog.setNegativeButton("close") { text, Listener ->
                ActivityCompat.finishAffinity(this)
            }
            dialog.show()
        }
    }
}
