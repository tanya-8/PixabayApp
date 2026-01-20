package com.example.practice_recycler_api

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("bindAdapter")
fun bindAdapter(view: RecyclerView,adapter: RecyclerView.Adapter<*>){
    view.adapter=adapter
}
