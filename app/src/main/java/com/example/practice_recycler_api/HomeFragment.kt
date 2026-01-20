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
import android.widget.Toast
import android.widget.ViewSwitcher
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelStore
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practice_recycler_api.databinding.StartFragmentBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//@AndroidEntryPoint
class HomeFragment: Fragment(), onClickListener {
    private var _binding: StartFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PixabayAdapter
    private lateinit var recyclerView: RecyclerView
    private val viewModel: PixabayViewModel by viewModels()
    var query: String?="yellow"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.start_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter= PixabayAdapter(this)
        recyclerView = binding.recyclerView
        binding.apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
        }
        query= savedInstanceState?.getString("query")
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when(state){
                is ApiState.Success->(adapter.setData(state.hitItems))
                else-> showError()
            }
        }

        binding.button.setOnClickListener {
            val tags = binding.editTextForTags.text.toString().trim()
            if (tags.isNotEmpty()) query = tags
            viewModel.newSearch(query)
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
        val map= Bundle()
        map.putSerializable("itemDetail",item)
        val detailFragment= DetailFragment()
        detailFragment.arguments=map
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, detailFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}