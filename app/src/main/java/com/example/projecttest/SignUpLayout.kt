package com.example.projecttest

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.sign_up_layout.*

class SignUpLayout : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_layout)

        signup_layout_btnSignUp.setOnClickListener {
            if (sign_up_layout_edtPassword.text.toString().equals(sign_up_layout_edtConfirmPassword.text.toString())) {

//                Registration Process

                val url =
                    "https://researchinlagos.org/product/join_new_user?email" + sign_up_layout_edtEmail.text.toString() +
                            "&username=" + sign_layout_edtUsername.text.toString() + "&pass=" + sign_up_layout_edtPassword.text.toString()

                val requestQ = Volley.newRequestQueue(this@SignUpLayout)
                val stringRequest = StringRequest(Request.Method.GET, url, Response.Listener { response ->

                    if (response.equals("A user with the ame email address already exist")) {
                        val dialogBuilder = AlertDialog.Builder(this)
                        dialogBuilder.setTitle("Messages")
                        dialogBuilder.setMessage(response)
                        dialogBuilder.create().show()
                    } else {
//                        val dialogBuilder = AlertDialog.Builder(this)
//                        dialogBuilder.setTitle("Messages")
//                        dialogBuilder.setMessage(response)
//                        dialogBuilder.create().show()

                        Person.email = sign_up_layout_edtEmail.text.toString()

                        Toast.makeText(this@SignUpLayout, response, Toast.LENGTH_SHORT).show()
                        val homeIntent = Intent(this@SignUpLayout, HomeScreen::class.java)
                        startActivity(homeIntent)
                    }


                }, Response.ErrorListener { error ->
                    val dialogBuilder = AlertDialog.Builder(this)
                    dialogBuilder.setTitle("Messages")
                    dialogBuilder.setMessage(error.message)
                    dialogBuilder.create().show()
                })

                requestQ.add(stringRequest)

            } else {

                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Messages")
                dialogBuilder.setMessage("password Mismatch")
                dialogBuilder.create().show()

            }
        }

//        signup_layout_btnLogin.setOnClickListener {
//            var signUpIntent = Intent(this@SignUpLayout, MainActivity::class.java)
//            startActivity(signUpIntent)
  //  }

        signup_layout_btnLogin.setOnClickListener {
            finish()

        }
    }
}
