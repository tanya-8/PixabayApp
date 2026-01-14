package com.example.practice_recycler_api

data class PixbayResponse(
    val total: Int,
    val totalHits: Int,
    val hits: List<image>
)
