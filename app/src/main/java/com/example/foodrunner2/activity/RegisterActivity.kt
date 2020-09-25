package com.example.foodrunner2.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodrunner2.R
import kotlinx.android.synthetic.main.activity_cart.*
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    lateinit var nameEditText: EditText
    lateinit var emailEdit: EditText
    lateinit var numberEdit: EditText
    lateinit var passwordEdit: EditText
    lateinit var addressEdit: EditText
    lateinit var registerButton: Button
    lateinit var registerToLoginButton: Button

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        sharedPreferences =
            getSharedPreferences(getString(R.string.sharedPreference), Context.MODE_PRIVATE)

        nameEditText = findViewById(R.id.register_name)
        emailEdit = findViewById(R.id.register_email)
        numberEdit = findViewById(R.id.register_phone)
        passwordEdit = findViewById(R.id.register_password)
        addressEdit = findViewById(R.id.register_address)
        registerButton = findViewById(R.id.register_button)
        registerToLoginButton = findViewById(R.id.register_to_login)


        registerButton.setOnClickListener {
            if (nameEditText.text.length < 3) nameEditText.setError("enter Valid name")
            else if (!emailEdit.text.endsWith("@gmail.com")) emailEdit.setError("enter vaild address")
            else if (numberEdit.text.length < 10) numberEdit.setError("enter vaild phone number")
            else if (passwordEdit.text.length < 8) passwordEdit.setError("password length should be 8 or more")
            else if (addressEdit.text.length < 10) addressEdit.setError("it's not valid address")
            else {
                registerUser()
            }
        }

        registerToLoginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun registerUser() {
        val queue = Volley.newRequestQueue(this)
        val url = "http://13.235.250.119/v2/register/fetch_result"

        val params = HashMap<String, String>()
        params["name"] = nameEditText.text.toString()
        params["mobile_number"] = numberEdit.text.toString()
        params["password"] = passwordEdit.text.toString()
        params["address"] = addressEdit.text.toString()
        params["email"] = emailEdit.text.toString()


        val jsonrequest = object : JsonObjectRequest(
            Request.Method.POST,
            url,
            JSONObject(params as Map<String, String>),
            Response.Listener {
                //this is response listener...
                val success = it.getJSONObject("data").getBoolean("success")
                if (success) {
                    val data = it.getJSONObject("data").getJSONObject("data")


                    sharedPreferences.edit().putString("address", data.getString("address")).apply()
                    sharedPreferences.edit().putString("mobile_number", data.getString("mobile_number")).apply()
                    sharedPreferences.edit().putString("email", data.getString("email")).apply()
                    sharedPreferences.edit().putString("user_id", data.getString("user_id")).apply()
                    sharedPreferences.edit().putString(getString(R.string.user), data.getString("name")).apply()


                    sharedPreferences.edit().putBoolean(getString(R.string.loggedIn), true).apply()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    Toast.makeText(
                        this,
                        "registratoin failed ${it.getJSONObject("data").getString("errorMessage")}",
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
