package com.example.practice_recycler_api

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class PixabayAdapter(private val hitItems: MutableList<image>, private val listener: onClickListener):
    RecyclerView.Adapter<PixabayAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView= LayoutInflater.from(parent.context).
                inflate(R.layout.recycler_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currInfo=hitItems[position]
        val imgurl= currInfo.imgURL
        Picasso.get()
                .load(imgurl)
                .into(holder.img)
        holder.img.setOnClickListener { listener.onClickListen(currInfo) }
    }

    override fun getItemCount(): Int {
        return hitItems.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val img: ImageView=itemView.findViewById<ImageView>(R.id.imageView)
    }

}