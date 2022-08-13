package com.example.prro.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.prro.R
import com.example.prro.databinding.PairDetailRowBinding
import com.example.prro.model.AnalyzeModel

class PairDetailRecyclerAdapter()
    :RecyclerView.Adapter<PairDetailRecyclerAdapter.AnalyzeViewHolder>() {

    class AnalyzeViewHolder(var view: PairDetailRowBinding) : RecyclerView.ViewHolder(view.root) {

    }

    private val diffUtil= object: DiffUtil.ItemCallback<AnalyzeModel>(){
        override fun areItemsTheSame(oldItem: AnalyzeModel, newItem: AnalyzeModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: AnalyzeModel, newItem: AnalyzeModel): Boolean {
            return oldItem== newItem
        }
    }

    private val listDifferRecycler = AsyncListDiffer(this,diffUtil)

    var analyzes: List<AnalyzeModel>
        get() = listDifferRecycler.currentList
        set(value) = listDifferRecycler.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnalyzeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<PairDetailRowBinding>(inflater,R.layout.pair_detail_row,parent,false)
        return AnalyzeViewHolder(view)

    }

    override fun onBindViewHolder(holder: AnalyzeViewHolder, position: Int) {
        holder.view.analyzeModel= analyzes[position]
        holder.view.imageView


    }

    override fun getItemCount(): Int {
        return analyzes.size

    }

/*    fun updateAnalyzeList(newAnalyzeList: List<AnalyzeModel>) {
        list.addAll(newAnalyzeList)
        notifyDataSetChanged()
    }*/

}



