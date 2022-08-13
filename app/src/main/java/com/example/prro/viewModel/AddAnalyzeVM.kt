package com.example.prro.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.example.prro.model.AnalyzeModel
import com.example.prro.util.OzelSharedPreferences
import com.example.prro.util.chosenPair
import com.example.prro.util.database
import com.example.prro.util.dbCollection
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class AddAnalyzeVM(application: Application) : AndroidViewModel(application) {

    fun save(data: AnalyzeModel) {

        val newRef = database.collection(dbCollection!!).document("Specified").collection("Pairs")
            .document(chosenPair)
            .collection("Analysis")
            .document()     // 👈 generates a new reference with a unique ID

        data.id = newRef.id // 👈 set the ID into your object

        newRef.set(data)    // 👈 writes the data to the new reference
            .addOnFailureListener { exception ->
                exception.printStackTrace()
                Toast.makeText(getApplication(), exception.localizedMessage, Toast.LENGTH_LONG).show()
            }
    }



/*        database.collection(dbCollection!!).document("Specified").collection("Pairs")
            .document(chosenPair!!)
            .collection("Analysis")
            .add(data)
            .addOnFailureListener { exception ->
                exception.printStackTrace()
                Toast.makeText(getApplication(), exception.localizedMessage, Toast.LENGTH_LONG).show()
            }*/

}