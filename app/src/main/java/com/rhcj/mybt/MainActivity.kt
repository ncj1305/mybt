package com.rhcj.mybt

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.rhcj.mybt.open.Util
import android.content.Intent

/**
 * Created by ncj on 2018-02-27.
 */
class MainActivity: AppCompatActivity() {
    var etPassword: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btSignIn = findViewById<Button>(R.id.btSignIn)
        etPassword = findViewById<EditText>(R.id.etPassword)

        btSignIn.setOnClickListener({
            if (checkPasswordValidation()) {
                val intent = Intent(this@MainActivity, DashboardActivity::class.java)
                startActivity(intent)
            } else {
                etPassword?.setText("")
            }
        })
    }

    override fun onResume() {
        super.onResume()
        etPassword?.setText("")
    }

    private fun checkPasswordValidation(): Boolean {
        val hash = Util.getHashString(etPassword?.text.toString())
        return hash.equals("9b3db3678e9d05654ffe61eb30f19fa6c07e34f915078dcaa80f513cee342061")
    }
}