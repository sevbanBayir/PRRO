package com.example.prro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.prro.R
import com.example.prro.databinding.ProfileRecyclerRowBinding
import com.example.prro.model.CoinListModel
import com.example.prro.view.ProfileActivity
import kotlinx.android.synthetic.main.activity_profile.view.*
import java.text.DecimalFormat

class ProfileRecyclerAdapter(var assetList: ArrayList<CoinListModel>, var context: ProfileActivity) :
    RecyclerView.Adapter<ProfileRecyclerAdapter.CoinListViewHolder>() {

    interface Listener {
        fun onItemClick(view : View)
    }

    class CoinListViewHolder(var view: ProfileRecyclerRowBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ProfileRecyclerRowBinding>(inflater,R.layout.profile_recycler_row,parent,false)
        return CoinListViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinListViewHolder, position: Int) {
        holder.view.coinList=assetList[position]
        holder.view.listener= context
        //Formatting
        val df = DecimalFormat("#.###")
        val price = assetList[position].price_usd
        price.toString().replace("\\s".toRegex(), "")
        holder.view.coinPrice.text= price.toString()
        df.format(price)
        df.format(assetList[position].price_usd).let { holder.view.coinPrice.text = it.toString() }
        //

        val coinName = assetList[position].asset_id
        coinName.replace("\\s".toRegex(), "")
        holder.view.coinTicker.text = coinName

    }

    fun updateCoinList(newCoinList: List<CoinListModel>) {
        assetList.clear()
        assetList.addAll(newCoinList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return assetList.size
    }

    fun filteredList(filtered_list: ArrayList<CoinListModel>) {

        assetList = filtered_list
        notifyDataSetChanged()
    }

}