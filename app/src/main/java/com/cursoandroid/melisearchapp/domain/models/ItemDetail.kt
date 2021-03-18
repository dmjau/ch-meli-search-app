package com.cursoandroid.melisearchapp.domain.models

import com.google.gson.annotations.SerializedName

data class ItemDetail(
    @SerializedName("id") val id: String,
    @SerializedName("created") val created: String,
    @SerializedName("plain_text") val plain_text: String,
    @SerializedName("pictures") val pictures: ArrayList<Picture>
)