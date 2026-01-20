package com.example.practice_recycler_api

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso

class DetailFragment: Fragment(R.layout.detail_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageDetails = arguments?.getSerializable("itemDetail") as? image
        imageDetails?.let{
            view.findViewById<TextView>(R.id.textViewLikes).text="Likes: "+imageDetails.likes
            view.findViewById<TextView>(R.id.textView2Comments).text="Comments: "+imageDetails.comments
            view.findViewById<TextView>(R.id.textView3Downloads).text="Downloads: "+imageDetails.downloads
            val imgurl=imageDetails.imgURL
            Picasso.get()
                .load(imgurl)
                .into(view.findViewById<ImageView>(R.id.imageView2))
        }
    }
}