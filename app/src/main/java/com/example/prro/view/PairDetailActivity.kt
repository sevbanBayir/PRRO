package com.example.prro.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prro.R
import com.example.prro.adapter.PairDetailRecyclerAdapter
import com.example.prro.databinding.ActivityPairDetailBinding
import com.example.prro.util.OzelSharedPreferences
import com.example.prro.util.ozelSharedPreferences
import com.example.prro.viewModel.PairVM
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_pair_detail.*

class PairDetailActivity : AppCompatActivity(){

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseFirestore
    private var recyclerA = PairDetailRecyclerAdapter()
    private lateinit var viewModel: PairVM
    private lateinit var dataBinding :ActivityPairDetailBinding

    private val swipeCallBack = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition= viewHolder.layoutPosition
            val selectedAnalyze= recyclerA.analyzes[layoutPosition]
            viewModel.deleteFromFirebase(selectedAnalyze)

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding=DataBindingUtil.setContentView(this,R.layout.activity_pair_detail)


        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        viewModel = ViewModelProvider(this).get(PairVM::class.java)
        //viewModel.retrieveData(this)


        pair_detail_recyclerView.layoutManager = LinearLayoutManager(this)
        pair_detail_recyclerView.adapter = recyclerA
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(dataBinding.pairDetailRecyclerView)

        pairDetailPairHeader.text = ozelSharedPreferences.clickiAl()

        observeLiveData()

    }

    fun observeLiveData() {
        viewModel.list.observe(this, Observer { myList ->
            myList?.let {
                recyclerA.analyzes=it

            }
        })
    }

    fun addButton1(v: View) {
        val intent = Intent(this, AddAnalyzeActivity::class.java)
        startActivity(intent)
        finish()
    }

}