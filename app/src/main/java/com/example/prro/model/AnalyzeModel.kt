package com.example.prro.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.model.Document
import java.util.*

data class AnalyzeModel(
    var concept: String?="",
    var reason: String?="",
    var result: String?="",
    var rrRatio: Double?=0.0,
    var tarih: Timestamp=Timestamp.now(),
    var tradingViewUrl: String="",
    var id : String=""
)
