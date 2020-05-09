package com.cristianpassos.crisecommerce

import android.content.DialogInterface
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_details.*

class ProductDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_details)

        val title = intent.getStringExtra("title")
        val photoUrl = intent.getStringExtra("photo_url")

        product_name.text = title
        Picasso.get().load(photoUrl).into(photo)

        availability.setOnClickListener{
            AlertDialog.Builder(this)
                .setMessage("Hey, $title is in stock!")
                .setPositiveButton("OK"
                ) { dialog, which -> }
                .create()
                .show()
        }
    }
}