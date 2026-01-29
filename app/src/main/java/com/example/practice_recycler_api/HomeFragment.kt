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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
//import com.example.practice_recycler_api.databinding.StartFragmentBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@AndroidEntryPoint
class HomeFragment: Fragment(R.layout.start_fragment), onClickListener {
//    private var _binding: StartFragmentBinding? = null
//    private val binding get() = _binding!!
    private lateinit var adapter: PixabayAdapter
    private lateinit var recyclerView: RecyclerView
    private val viewModel: PixabayViewModel by activityViewModels()
    var query: String?="yellow"
    private lateinit var last : String
    private lateinit var secondLast : String
    private var searchNum=1
//    var a: String?=null
//
//    constructor(a:String,b: String,c: String){
//        this.a  = a
//    }

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = DataBindingUtil.inflate(
//            inflater,
//            R.layout.start_fragment,
//            container,
//            false
//        )
//        return binding.root
//    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // calling of :Location service
//        val intent = Intent(requireContext(), LocationService::class.java).apply {
//            putExtra("NAME", "value")
//        }
//        ContextCompat.startForegroundService(requireContext(), intent)
    // super is the og method in activities it needs it be called ow it may cause crashes or or crash or not save state
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter= PixabayAdapter(this)
//        recyclerView = binding.recyclerView
//        binding.apply {
//            vm = viewModel
//            lifecycleOwner = viewLifecycleOwner
//            recyclerView.layoutManager = LinearLayoutManager(requireContext())
//            recyclerView.adapter = adapter
//        }

        recyclerView=view.findViewById(R.id.recyclerView)
        recyclerView.adapter=adapter
        recyclerView.layoutManager= LinearLayoutManager(context)

        query= savedInstanceState?.getString("query")
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when(state){
                is ApiState.Success->{
                    Log.d("test","The state hitItems is ${state.hitItems}")
                    (adapter.setData(state.hitItems))
                }
                else->{
                    Log.d("test","The state in other cases is ${state}")
                    showError()
                }
            }
        }
        val submitB=view.findViewById<Button>(R.id.button)
        val inputText=view.findViewById<EditText>(R.id.editTextForTags)

        // setting shared preferences method
        val preference= activity?.getSharedPreferences("My Search Preferences", MODE_PRIVATE )
        //setting the editor which actually sets key value pairs
        val editor= preference?.edit()


        submitB.setOnClickListener {
            val tags = inputText.text.toString().trim()
            if (tags.isNotEmpty()) query = tags
            adapter.clearItems()
            viewModel.newSearch(query)
            if(searchNum==1){
                //setting both to first query
               editor?.putString("lastQuery", query)
                editor?.putString("secondLastQuery", query)
                editor?.commit()
            }
            else{
                //setting second last to last and the latest one as last
                val last=preference?.getString("lastQuery","yellow")
                editor?.putString("lastQuery", query)
                editor?.putString("secondLastQuery", last)
                editor?.commit()
            }
            searchNum++
        }
        //calling the second last query to be submitted again to viewmodel after clearing the adapter
        val retrieveB=view.findViewById<Button>(R.id.buttonToRetrieve)
        retrieveB.setOnClickListener {
            val lastQuery= preference?.getString("secondLastQuery","yellow")
            adapter.clearItems()
            viewModel.newSearch(lastQuery)
        }
        val savedImageListButton= view.findViewById<Button>(R.id.getButton)
        savedImageListButton.setOnClickListener {
            val savedImagesFragment= SavedImageFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, savedImagesFragment)
                .addToBackStack(null)
                .commit()
        }

        recyclerView.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
                    val isAtEnd = firstVisibleItem + visibleItemCount >= totalItemCount
                    val isScrollDown = dy > 0
                    if (isAtEnd && isScrollDown) {
                        viewModel.fetchImageList(query)
                    }
                }
            },
        )
    }
    fun showError(){
        println("error happened while calling api ")
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