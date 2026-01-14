package com.example.practice_recycler_api

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
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment: Fragment(R.layout.start_fragment), onClickListener {
    private lateinit var adapter: PixabayAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var hitItems: MutableList<image>
    private var currentPage = 1
    private var isLoading = false
    private var isLastPage = false
    private var query="yellow"
    private val pageSize = 20

    val retrofit = Retrofit.Builder()
        .baseUrl("https://pixabay.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService = retrofit.create(RetrofitInterface::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hitItems=mutableListOf()
        recyclerView=view.findViewById(R.id.recyclerView)
        adapter= PixabayAdapter(hitItems,this)
        recyclerView.adapter=adapter
        recyclerView.layoutManager= LinearLayoutManager(context)

        val submitButton=view.findViewById<Button>(R.id.button)
        val inputText=view.findViewById<EditText>(R.id.editTextForTags)
        view.findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
        submitButton.setOnClickListener {
            val tags = inputText.text.toString().trim()
            if(tags.isNotEmpty()) query=tags
            doAPI()
        }
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager=rv.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
                if (!isLoading && !isLastPage) {
                    if (visibleItemCount + firstVisibleItem >= totalItemCount - 5) {
                        doAPI()
                    }
                }
            }
        })
        doAPI()

    }

    override fun onClickListen(item: image) {
        val map= Bundle()
        map.putSerializable("itemDetail",item)
        val detailFragment= DetailFragment()
        detailFragment.arguments=map
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, detailFragment)
            .addToBackStack(null)
            .commit()

    }



    private fun doAPI(){
        if(isLoading||isLastPage) return
        isLoading=true

        apiService.getImages("45834665-cd812607af12ca3f1bd5d4c19",query,currentPage,pageSize,true,true).enqueue(object : Callback<PixbayResponse> {
            override fun onResponse(
                call: Call<PixbayResponse>,
                response: Response<PixbayResponse>
            ) {
                isLoading=false
                if (response.isSuccessful) {

                    val hits = response.body()?.hits.orEmpty()
                    hitItems.clear()
                    //imp
                    hitItems.addAll(hits)
                    adapter.notifyDataSetChanged()
                    view?.findViewById<ProgressBar>(R.id.progressBar)?.visibility=View.GONE
                    if(hits.size<pageSize){
                        isLastPage=true
                    }
                    else currentPage++

                } else {
                    println("error occured")
                }
            }

            override fun onFailure(
                call: retrofit2.Call<com.example.practice_recycler_api.PixbayResponse?>,
                t: kotlin.Throwable
            ) {
                isLoading = false
            }

        })
    }

}