package com.cursoandroid.melisearchapp.domain.models

import com.google.gson.annotations.SerializedName

open class InfoResponse(
        @SerializedName("paging") val paging: Paging,
        @SerializedName("results") val results: List<Attribute>,
        @SerializedName("site_id") val site_id: String
)