package com.example.prro.util

import android.app.Application
import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.prro.R
import com.github.chrisbanes.photoview.PhotoView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import java.util.*

val database = FirebaseFirestore.getInstance()
val uuid= UUID.randomUUID()
val ozelSharedPreferences = OzelSharedPreferences(Application())
val chosenPair = ozelSharedPreferences.clickiAl().toString()
val currentU = Firebase.auth.currentUser
val dbCollection = currentU?.let { it.email.toString() }
val collectionRef = database.collection(dbCollection!!)
    .document("Specified")
    .collection("Pairs")
    .document(chosenPair)
    .collection("Analysis")

fun PhotoView.downloadImage(url: String?, placeHolder: CircularProgressDrawable) {
    if (url.isNullOrBlank()) {
        val invalidUrl = "www.test.com"
        Picasso.get()
            .load(invalidUrl)
            .placeholder(placeHolder)
            .error(R.mipmap.ic_launcher)
            .into(this);
    } else {
        Picasso.get()
            .load(url)
            .placeholder(placeHolder)
            .error(R.mipmap.ic_launcher)
            .into(this);
    }
}

fun placeHolderer(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

@BindingAdapter("android:loadImage")
fun loadImage(view: PhotoView, url: String?) {
    view.downloadImage(url, placeHolderer(view.context))
}