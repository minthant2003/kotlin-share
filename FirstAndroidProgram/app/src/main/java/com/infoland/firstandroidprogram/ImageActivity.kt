package com.infoland.firstandroidprogram

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ImageActivity : AppCompatActivity() {
    lateinit var imgView: ImageView
    lateinit var imgBtnNext: ImageButton
    lateinit var imgBtnPrevious: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        imgView = findViewById(R.id.imgView)
        imgBtnNext = findViewById(R.id.imgBtn_Next)
        imgBtnPrevious = findViewById(R.id.imgBtn_Previous)

        val imgList = listOf(R.drawable.green_car, R.drawable.green_plane, R.drawable.green_home, R.drawable.fish_test)
        var imgListIndex = 2

        imgView.setImageResource(imgList[imgListIndex])

        imgBtnNext.setOnClickListener {
            if (imgListIndex == imgList.size - 1) imgListIndex = 0 // WRONG LOGIC
            imgListIndex++
            imgView.setImageResource(imgList[imgListIndex])
        }
        imgBtnPrevious.setOnClickListener {
            if (imgListIndex == 0) imgListIndex = imgList.size - 1 // WRONG LOGIC
            imgListIndex--
            imgView.setImageResource(imgList[imgListIndex])
        }
    }
}