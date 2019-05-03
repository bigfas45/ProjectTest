package com.example.projecttest

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.ArrayAdapter
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_home_screen.*

class HomeScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        val urlBrands = "https://researchinlagos.org/product/fetch_brands"

        val brandList = ArrayList<String>()

        val requestQ = Volley.newRequestQueue(this@HomeScreen)

        val jsonAR = JsonArrayRequest(Request.Method.GET, urlBrands, null, Response.Listener {
            response ->

            for (jsonObject in 0.until(response.length())) {
                brandList.add(response.getJSONObject(jsonObject).getString("brand"))
            }
            val brandListAdapter = ArrayAdapter(this@HomeScreen, R.layout.brand_item_text_view, brandList)
            brandsListView.adapter = brandListAdapter

        }, Response.ErrorListener { error ->

            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Messages")
            dialogBuilder.setMessage(error.message)
            dialogBuilder.create().show()

        })

        requestQ.add(jsonAR)


        brandsListView.setOnItemClickListener { adapterView, view, i, l ->
            val tappedBrand = brandList.get(i)
            val intent = Intent(this@HomeScreen, FetchEProductsActivity::class.java)
            intent.putExtra("BRAND", tappedBrand)
            startActivity(intent)

        }

    }
}
