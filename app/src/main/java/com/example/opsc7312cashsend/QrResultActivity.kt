package com.example.opsc7312cashsend

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.OPSC7312CashSend.R
import org.json.JSONException
import org.json.JSONObject

class QrResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_result)

        val qrData = intent.getStringExtra("QR_CODE_DATA") ?: return

        // Sample parsing logic, assuming QR data is a JSON string
        try {
            val jsonObject = JSONObject(qrData)
            val location = jsonObject.optString("location", "Unknown")
            val amount = jsonObject.optString("amount", "0.00")

            findViewById<TextView>(R.id.locationTextView).text = "Location: $location"
            findViewById<TextView>(R.id.amountTextView).text = "Amount: $amount"
        } catch (e: JSONException) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to parse QR code data", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.cancelButton).setOnClickListener {
            val intent = Intent(this, HomeScreenActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
