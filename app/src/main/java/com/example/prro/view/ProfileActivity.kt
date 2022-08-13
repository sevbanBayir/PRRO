package com.example.prro.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prro.R
import com.example.prro.adapter.ProfileRecyclerAdapter
import com.example.prro.databinding.ActivityProfileBinding
import com.example.prro.model.CoinListModel
import com.example.prro.util.OzelSharedPreferences
import com.example.prro.viewModel.ProfileVM
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.profile_recycler_row.view.*
import kotlin.collections.ArrayList

class ProfileActivity : AppCompatActivity(), ProfileRecyclerAdapter.Listener {
    private lateinit var searchView: SearchView
    private lateinit var auth: FirebaseAuth
    private val recyclerProfileAdapter = ProfileRecyclerAdapter(arrayListOf(), this)
    private lateinit var dataBinding: ActivityProfileBinding
    private lateinit var viewModel : ProfileVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        viewModel = ViewModelProvider(this)[ProfileVM::class.java]
        viewModel.fromInternet()

        profileRecycler.layoutManager = LinearLayoutManager(applicationContext)
        profileRecycler.adapter = recyclerProfileAdapter

        searchView = ProfileSearch
        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        nameText.text = currentUser?.email.toString()
        observeLiveData()
        search()

    }


    private fun observeLiveData() {

        viewModel.coins.observe(this, Observer {
            it?.let {
                profileRecycler.visibility = View.VISIBLE
                recyclerProfileAdapter.updateCoinList(it)
            }
        })

        viewModel.progressBar.observe(this, Observer {
            it?.let {
                if (it) {
                    profileRecycler.visibility = View.GONE
                    assetListProgress.visibility = View.VISIBLE
                } else {
                    assetListProgress.visibility = View.GONE
                }
            }
        })
    }

    private fun search() {

        viewModel.coins.observe(this, Observer {
            it?.let {
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        val list = ArrayList<CoinListModel>()
                        for (i in it) {
                            if (i.asset_id.lowercase().contains(newText!!.lowercase())) {
                                list.add(i)
                            }
                        }
                        recyclerProfileAdapter.filteredList(list)
                        return true
                    }
                })
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.options_menu1, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.log_out_option) {
            auth.signOut()
            val intent = Intent(this, SigningActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(view: View) {
        val intent = Intent(this, PairDetailActivity::class.java)
        startActivity(intent)

        OzelSharedPreferences.invoke(this).clickiKaydet(view.coinTicker.text.toString())
    }
}