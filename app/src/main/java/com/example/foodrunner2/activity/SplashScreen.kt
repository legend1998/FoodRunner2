package com.example.foodrunner2.activity

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import android.widget.ImageView
import androidx.core.view.ViewCompat
import com.example.foodrunner2.R

class SplashScreen : AppCompatActivity() {
    lateinit var logoImage: ImageView
    lateinit var logoFood: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        logoImage = findViewById(R.id.logo_Splash)

        ViewCompat.animate(logoImage).translationY(-150f).setDuration(1500)
            .setInterpolator(DecelerateInterpolator(1.2f)).start()

        Handler().postDelayed({
            intent = Intent(this@SplashScreen, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }

}
