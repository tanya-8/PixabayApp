package com.example.practice_recycler_api

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class DetailFragment: Fragment(R.layout.detail_fragment) {

    private val viewModel: PixabayViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageDetails = arguments?.getSerializable("itemDetail") as image
        imageDetails?.let{
            view.findViewById<TextView>(R.id.textViewLikes).text="Likes: "+imageDetails.likes
            view.findViewById<TextView>(R.id.textView2Comments).text="Comments: "+imageDetails.comments
            view.findViewById<TextView>(R.id.textView3Downloads).text="Downloads: "+imageDetails.downloads
            val imgurl=imageDetails.imgURL
            Picasso.get()
                .load(imgurl)
                .into(view.findViewById<ImageView>(R.id.imageView2))
        }

        val addButton = view.findViewById<Button>(R.id.buttonAdd)
        val removeButton= view .findViewById<Button>(R.id.buttonRemove)

        addButton.setOnClickListener {
            viewModel.insertToDB(imageDetails)
        }
        removeButton.setOnClickListener {
            viewModel.deleteFromDB(imageDetails)
        }


    }
}