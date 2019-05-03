package com.example.projecttest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_fetch_eproducts.*

class FetchEProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch_eproducts)

        val selectedBrand:String = intent.getStringExtra("BRAND")
        txtBrandName.text = "Product of $selectedBrand"

        var productsList = ArrayList<EProduct>()

        val productsURL = "http://researchinlagos.org/product/fetch_eproducts?brand=" + selectedBrand
        val requestQ = Volley.newRequestQueue(this@FetchEProductsActivity)
        val jsonAr = JsonArrayRequest(Request.Method.GET, productsURL, null, Response.Listener {
                response ->

            for (productJOIndex in 0.until(response.length())){

                productsList.add(EProduct(response.getJSONObject(productJOIndex).getInt("id"), response.getJSONObject(productJOIndex).getString("brand"), response.getJSONObject(productJOIndex).getInt("price"),response.getJSONObject(productJOIndex).getString("picture")))

            }

            val pAdapter = EProductAdapter(this@FetchEProductsActivity, productsList)
            productsRV.layoutManager = LinearLayoutManager(this@FetchEProductsActivity)
            productsRV.adapter = pAdapter

        }, Response.ErrorListener { error ->
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Messages")
            dialogBuilder.setMessage(error.message)
            dialogBuilder.create().show()
        })

        requestQ.add(jsonAr)
    }
}
