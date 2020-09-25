package com.example.foodrunner2.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.foodrunner2.R
import com.example.foodrunner2.fragement.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView
    lateinit var headerName: TextView
    lateinit var headerEmail: TextView

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.homeToolbar)
        drawerLayout = findViewById(R.id.drawerlayout)
        frameLayout = findViewById(R.id.framelayout)
        navigationView = findViewById(R.id.navigation)
        var previousMenuItem: MenuItem? =null

        val navView: View = navigationView.inflateHeaderView(R.layout.header)

        headerEmail = navView.findViewById(R.id.header_email)
        headerName = navView.findViewById(R.id.header_username)

        sharedPreferences =
            getSharedPreferences(getString(R.string.sharedPreference), Context.MODE_PRIVATE)

        setuptoolbar()
        opendashboard()

        navigationView.setNavigationItemSelectedListener {
            if(previousMenuItem!=null){
                previousMenuItem?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            previousMenuItem=it


            when (it.itemId) {
                R.id.dashboard -> {
                    opendashboard()
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.profile -> {
                    supportActionBar?.title = "My Profile"
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout, ProfileFragment()).addToBackStack("profile")
                        .commit()
                    drawerLayout.closeDrawer(GravityCompat.START)

                }
                R.id.faqs -> {
                    supportActionBar?.title = "FAQs"
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout, FaqsFragment()).addToBackStack("faqs").commit()
                    drawerLayout.closeDrawer(GravityCompat.START)

                }
                R.id.fav -> {
                    supportActionBar?.title = "Favorite Restaurants"
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout, FavFragment()).addToBackStack("fav").commit()
                    drawerLayout.closeDrawer(GravityCompat.START)

                }
                R.id.logout -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    logout()

                }
                R.id.history -> {
                    supportActionBar?.title = "Order History"
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout, OrderHistoryFragment()).addToBackStack("history")
                        .commit()
                    drawerLayout.closeDrawer(GravityCompat.START)

                }
            }
            return@setNavigationItemSelectedListener true
        }
    }


    fun opendashboard() {
        supportActionBar?.title = "Home"
        supportFragmentManager.beginTransaction()
            .replace(R.id.framelayout, DashboardFragment()).commit()

    }


    fun setuptoolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Home"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        actionBarDrawerToggle.syncState()
        drawerLayout.addDrawerListener(actionBarDrawerToggle)

        headerName.text = sharedPreferences.getString("user", "")
        headerEmail.text = sharedPreferences.getString("email", "")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.framelayout)
        if (fragment !is DashboardFragment) opendashboard()
        else super.onBackPressed()
    }

    fun logout() {
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage("Are you sure want to Logout?")
        dialog.setPositiveButton("Yes") { text, Listener ->
            sharedPreferences.edit().putBoolean(getString(R.string.loggedIn), false).apply()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        dialog.setNegativeButton("No"){
            text,Listener->
            Toast.makeText(this,"not logged out",Toast.LENGTH_SHORT).show()
        }
        dialog.show()
    }
}
