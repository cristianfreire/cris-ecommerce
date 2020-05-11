package com.cristianpassos.crisecommerce

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.room.Room
import com.cristianpassos.crisecommerce.cart.CartActivity
import com.cristianpassos.crisecommerce.database.AppDatabase
import com.cristianpassos.crisecommerce.database.CartModel
import com.cristianpassos.crisecommerce.database.ProductFromDatabase

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        doAsync {
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database-name"
            ).build()

            db.productDao().insertAll(ProductFromDatabase(null, "Socks - one dozen", 1.99))
            val products = db.productDao().getAll()

            val cart = db.cartDao()
            cart.insertAll(CartModel(null, "Test product", 12.34, 3))
            val allCartItems = cart.getAll()

            uiThread {
                d("cristian", "products size? ${products.size} ${products[0].title}")

                allCartItems.forEach{
                    d("cristian", "item in cart: ${it.title} ${it.price}")
                }
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, MainFragment())
            .commit()

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.actionHome -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, MainFragment())
                        .commit()
                }
                R.id.actionJeans -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, JeansFragment())
                        .commit()
                }
                R.id.actionShorts -> {
                    d("Cristian", "shorts was pressed!")
                }
                R.id.action_admin -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, AdminFragment())
                        .commit()
                }
            }
            it.isChecked = true
            drawerLayout.closeDrawers()
            true
        }

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp)
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.actionCart){
            d("cristian", "going to cart")
            startActivity(Intent(this, CartActivity::class.java))
            return true
        }
        drawerLayout.openDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }
}
