package com.example.ceta.model

import android.graphics.Bitmap

data class paymentData(
    var name: String?,
    var city: String?,
    var pick: String?,
    var back: String?,
    var price: Int?,
    var deposit: Int?,
    var total: Int?,
    var image: Bitmap?,
    var date: Int?,
)