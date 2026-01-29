package com.example.practice_recycler_api

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.collections.orEmpty

@HiltViewModel
class PixabayViewModel @Inject constructor(
    private val repository: Repository,
    private val fileManager: FileManager
): ViewModel(){
    private var isLoading=false
    private var isLastPage=false
    private var currentPage=1
    private var num=0
    private var firstCall=1
//    private var currentList: MutableList<image> = mutableListOf()
    private val _state= MutableLiveData<ApiState>()
    val state: LiveData<ApiState>
        get()=_state

    fun insertToDB(image: image){
        viewModelScope.launch (Dispatchers.IO){
            repository.insertToDB(image)
        }
    }
    fun deleteFromDB(image: image){
        viewModelScope.launch(Dispatchers.IO) { val rows=repository.deleteFromDB(image)
        Log.d("database operation", "$rows no. of rows deleted")}
    }
    fun deleteDB(){
        viewModelScope.launch (Dispatchers.IO){ repository.deleteDB() }
    }
//    fun isSavedToDB(id: String): Boolean{
//        var isSaved=false
//        viewModelScope.launch (Dispatchers.IO){isSaved= repository.isSavedToDB(id) }
//        return isSaved
//    }

    val allSavedImages: StateFlow<List<image>> =
            repository.getAllImages().stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(2000),
                emptyList()
            )
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
                Log.d("API call was made", "api was hit ")
                if (response.isSuccessful) {
                    val newImages = response.body()?.hits.orEmpty()
                    if (newImages.isEmpty()) {
                        isLastPage = true
                        Log.d("Pagination", "Last page reached")
                    } else {

                        _state.value = ApiState.Success(newImages)
                        currentPage++

                        if(firstCall==1){
                            fileManager.saveFile("pixabaySecondResponse.json", newImages)
                            Log.d("file saving", "this message was called")
                            firstCall++
                        }
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
        //_state.value= ApiState.Success(emptyList())
        fetchImageList(query)
    }


}