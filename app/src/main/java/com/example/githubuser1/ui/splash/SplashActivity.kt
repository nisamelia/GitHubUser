package com.example.githubuser1.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.githubuser1.R
import com.example.githubuser1.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //hide toolbar
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        //set image animation
        val imgBackground: ImageView = findViewById(R.id.imageSplash)
        val animationSlide = AnimationUtils.loadAnimation(this, R.anim.splash_anim)
        imgBackground.startAnimation(animationSlide)

        //delay
        Handler().postDelayed({
            val intentSplash = Intent(this, MainActivity::class.java)
            startActivity(intentSplash)
            finish()
        }, 3000)
    }
}