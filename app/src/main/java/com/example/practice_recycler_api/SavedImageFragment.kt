package com.example.practice_recycler_api

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.graphics.Movie
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import android.widget.ViewSwitcher
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
//import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
//import com.example.practice_recycler_api.databinding.StartFragmentBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@AndroidEntryPoint
class SavedImageFragment: Fragment(R.layout.saved_image_list), onClickListener{
    private lateinit var adapter: PixabayAdapter
    private lateinit var recyclerView: RecyclerView
    private val viewModel: PixabayViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter= PixabayAdapter(this)
        recyclerView= view.findViewById<RecyclerView>(R.id.recViewSavedImages)
        recyclerView.adapter=adapter
        recyclerView.layoutManager= LinearLayoutManager(context)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allSavedImages.collect {
                images -> adapter.setData(images)
            }
        }

        val deleteAllButton= view.findViewById<Button>(R.id.buttonDelete)
        deleteAllButton?.setOnClickListener {
            viewModel.deleteDB()
            adapter.clearItems()
        }

    }

    override fun onClickListen(item: image) {
        val sendData= Bundle()
        sendData.putSerializable("itemDetail",item)
        val detailFragment= DetailFragment()
        detailFragment.arguments=sendData
//        Thread.sleep(2000)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, detailFragment)
            .addToBackStack(null)
            .commit()
    }

}