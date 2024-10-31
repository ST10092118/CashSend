package com.example.opsc7312cashsend

import MyAccountFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.OPSC7312CashSend.R
//import com.opsc7311.cashsend_opscpart2.Fragments.MyAccountFragment

class MyAccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_account)

        // Load MyAccountFragment into the container if this is the first time the activity is created
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MyAccountFragment())
                .commit()
        }
    }
}
