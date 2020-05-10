package com.cristianpassos.crisecommerce

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.cristianpassos.crisecommerce.database.AppDatabase
import com.cristianpassos.crisecommerce.database.ProductFromDatabase
import kotlinx.android.synthetic.main.fragment_admin.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.uiThread

class AdminFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_admin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        submitButton.setOnClickListener{
            val title  = productTitle.text
            d("Cristian", "button pressed :) with text of $title")

            doAsync {
                val db = Room.databaseBuilder(
                        activity!!.applicationContext,
                        AppDatabase::class.java, "database-name"
                ).build()

                db.productDao().insertAll(ProductFromDatabase(null, title.toString(), 12.34))

                uiThread {
                    d("Cristian", "reditecting to home page")
                }
            }
        }
    }
}