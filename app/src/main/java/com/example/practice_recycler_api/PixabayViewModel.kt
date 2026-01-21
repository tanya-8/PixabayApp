package com.example.practice_recycler_api

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.collections.orEmpty

@HiltViewModel
class PixabayViewModel @Inject constructor(
    private val repository: Repository
): ViewModel(){
    private var isLoading=false
    private var isLastPage=false
    private var currentPage=1
    private var num=0

    private val _state= MutableLiveData<ApiState>()
    val state: LiveData<ApiState>
        get()=_state


    fun fetchImageList(query: String?){

        if(isLoading||isLastPage) return
        isLoading=true

        Log.d("Pagination","Requesting page: $currentPage")
        println("api called $num")
        num++

        repository.getImageList("45834665-cd812607af12ca3f1bd5d4c19",query,currentPage,10,true,true).enqueue(object : Callback<PixbayResponse> {
            override fun onResponse(
                call: Call<PixbayResponse>,
                response: Response<PixbayResponse>
            ) {
                if (response.isSuccessful) {
                    val newImages = response.body()?.hits.orEmpty()
                    if (newImages.isEmpty()) {
                        isLastPage = true
                        Log.d("Pagination", "Last page reached")
                    } else {
                        val currentList=when(val state=_state.value){
                            is ApiState.Success->state.hitItems
                            else -> emptyList()
                        }
                        val updatedList = currentList + newImages
                        _state.value = ApiState.Success(updatedList)
                        currentPage++
                    }
                    isLoading=false
                } else {
                    _state.value= ApiState.Error
                }
            }
            override fun onFailure(
                call: retrofit2.Call<com.example.practice_recycler_api.PixbayResponse?>,
                t: kotlin.Throwable
            ) {
                Log.e("Pagination", "Error loading page $currentPage")
                _state.value= ApiState.Error
                isLoading = false
            }
        })
    }

    fun newSearch(query:String?){
        isLastPage=false
        isLoading=false
        currentPage=1
        _state.value= ApiState.Success(emptyList())
        fetchImageList(query)
    }


}