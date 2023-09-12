package com.example.demo.learning.welecome_hacks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {
    private lateinit var onboardingItemAdapter: OnboardingItemAdapter
    private lateinit var indicatorsContainer: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setOnboardingItems()
        setupindicator()
        setCurrentIndicator(0)
    }

    private fun setOnboardingItems() {
        onboardingItemAdapter = OnboardingItemAdapter(
            listOf(
                OnboardingItem(
                    onboardingImage = R.drawable.logo,
                    title = "Welcome",
                    descriptor = "Department of Employment Generation, Skill Development & Training- Govt. Of Punjab, India."
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.ar_logo,
                    title = "AR / VR",
                    descriptor = "Explore local job openings with our AR job finder app on your mobile device."
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.gps_logo,
                    title = "GPS-Guided",
                    descriptor = "Navigate to your dream job with our GPS -powered job finder app."
                ),
            )
        )
        val onboardingViewPager = findViewById<ViewPager2>(R.id.onboardingViewPager)
        onboardingViewPager.adapter = onboardingItemAdapter
        onboardingViewPager.registerOnPageChangeCallback(object :
        ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        (onboardingViewPager.getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        findViewById<ImageView>(R.id.imageNext).setOnClickListener{
            if (onboardingViewPager.currentItem+1 < onboardingItemAdapter.itemCount){
                onboardingViewPager.currentItem+=1
            }else{
                navigateToWelecomeActivity()
            }
        }
        findViewById<TextView>(R.id.textskip).setOnClickListener{
            navigateToWelecomeActivity()
        }
    }
    private fun navigateToWelecomeActivity(){
        startActivity(Intent(applicationContext,WelecomeActivity::class.java))
        finish()
    }
    private fun setupindicator(){
        indicatorsContainer = findViewById(R.id.indicatorContainer)
        val indicators = arrayOfNulls<ImageView>(onboardingItemAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8,0,8,0)
for (i in indicators.indices){
    indicators[i]= ImageView(applicationContext)
    indicators[i]?.let {
        it.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.indicator_inactive_background
            )
        )
        it.layoutParams=layoutParams
        indicatorsContainer.addView(it)
    }
}
    }
    private fun setCurrentIndicator(position: Int){
        val childCount = indicatorsContainer.childCount
        for (i in 0 until childCount){
            val imageView=indicatorsContainer.getChildAt(i) as ImageView
            if (i == position){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active_background
                    )
                )
            }else{
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
            }
        }
    }
}