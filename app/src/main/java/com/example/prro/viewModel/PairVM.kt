package com.example.prro.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.prro.model.AnalyzeModel
import com.example.prro.util.collectionRef
import com.example.prro.util.uuid
import com.example.prro.view.PairDetailActivity
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import java.lang.Exception

class PairVM : ViewModel() {
    init {
        retrieveData()
    }

    val list = MutableLiveData<ArrayList<AnalyzeModel>>()

    private fun retrieveData() {

        val docRef = collectionRef.orderBy("tarih", Query.Direction.DESCENDING)
        docRef.addSnapshotListener { value, error ->
            try {
                if (value != null && !value.isEmpty) {
                    val allAnalysis = ArrayList<AnalyzeModel>()
                    val documents = value.documents
                    documents.forEach {
                        val analyze = it.toObject(AnalyzeModel::class.java)
                        if (analyze != null) {

                            allAnalysis.add(analyze)
                        }
                    }

                    list.value = allAnalysis
                } else if (error != null) {
                    Toast.makeText(Application(), error.localizedMessage, Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteFromFirebase(model: AnalyzeModel) {
        collectionRef.document(model.id).delete().addOnFailureListener {
            Toast.makeText(Application(), it.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }
}