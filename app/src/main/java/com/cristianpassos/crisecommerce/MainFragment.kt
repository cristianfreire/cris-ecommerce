package com.cristianpassos.crisecommerce

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.cristianpassos.crisecommerce.database.AppDatabase
import com.cristianpassos.crisecommerce.database.ProductFromDatabase
import com.cristianpassos.crisecommerce.model.Product
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.fragment_main.view.categoriesRecyclerView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL

class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)

        val categories = listOf(
            "Jeans",
            "Socks",
            "Suits",
            "Skirts",
            "Dresses",
            "Jeans",
            "Socks",
            "Pants",
            "Jacket"
        )

        root.categoriesRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            adapter = CategoriesAdapter(categories)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchButton.setOnClickListener {
            doAsync {
                val db = Room.databaseBuilder(
                    activity!!.applicationContext,
                    AppDatabase::class.java, "database-name"
                ).build()

                val productFromDatabase = db.productDao().searchFor("%${searchTerm.text}%")

                val products = productFromDatabase.map {
                    Product(it.title, "https://finepointmobile.com/data/jeans2.jpg", it.price, true
                    )
                }
                uiThread {
                    recycler_view.apply {
                        layoutManager = GridLayoutManager(activity, 2)
                        adapter = ProductsAdapter(products)
                    }
                    progressBar.visibility = View.GONE
                }
            }
        }

    }
}