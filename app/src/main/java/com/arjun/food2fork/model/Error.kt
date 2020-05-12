package com.arjun.food2fork.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Error(val message: String)