package com.picpay.david.davidrockpicpay.features.creditCard

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.picpay.david.davidrockpicpay.R
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_new_credit_card.*


class NewCreditCardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_credit_card)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar

        toolbar.setNavigationOnClickListener {
            // back button pressed
        }

        val items = arrayOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6")
        var adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(adapter)
    }
}