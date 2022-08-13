package com.example.prro.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.prro.R
import com.example.prro.model.AnalyzeModel
import com.example.prro.util.OzelSharedPreferences
import com.example.prro.viewModel.AddAnalyzeVM
import com.example.prro.viewModel.PairVM
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_analyze.*
import java.util.*

class AddAnalyzeActivity : AppCompatActivity() {
    val concepts = arrayOf(
        "Order Block",
        "Fair Value Gap",
        "Breaker",
        "Market Structure Break",
        "Quasimodo",
        "Range",
        "Optimal Trade Entry",
        "Wyckoff",
        "Sponsored Candle",
        "SFP"
    )
    private lateinit var viewModel: AddAnalyzeVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_analyze)



        viewModel = ViewModelProvider(this).get(AddAnalyzeVM::class.java)

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, concepts)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                conceptText.text = concepts[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
               return
            }
        }
    }

    fun addData(view: View) {

        val tarih = com.google.firebase.Timestamp.now()
        val rr = rrText.text.toString()
        var doubleRR = rr.toDoubleOrNull()
        if (doubleRR == null) { doubleRR = 0.0 }

        val analyzeDTO = AnalyzeModel(
            conceptText.text.toString(),
            reasonForText.text.toString(),
            resultAddingText.text.toString(),
            doubleRR,
            tarih,
            chartImage.text.toString()
        )
        viewModel.save(analyzeDTO)

        val intent = Intent(this, PairDetailActivity::class.java)
        startActivity(intent)
        finish()
    }
}