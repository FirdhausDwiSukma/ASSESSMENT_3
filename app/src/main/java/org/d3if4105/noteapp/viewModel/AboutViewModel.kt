package org.d3if4105.noteapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if4105.noteapp.model.About
import org.d3if4105.noteapp.network.AboutApi
import org.d3if4105.noteapp.network.ApiStatus

class AboutViewModel : ViewModel() {
    private val aboutData = MutableLiveData<List<About>>()
    private val status = MutableLiveData<ApiStatus>()

    init {
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch (Dispatchers.IO) {
            status.postValue(ApiStatus.LOADING)
            try {
                aboutData.postValue(AboutApi.service.getAbout())
                status.postValue(ApiStatus.SUCCESS)
            } catch (e: Exception) {
                Log.d("AboutViewModel", "Failure: ${e.message}")
                status.postValue(ApiStatus.FAILED)
            }
        }
    }

    fun getTipsData(): LiveData<List<About>> = aboutData
    fun getStatus(): LiveData<ApiStatus> = status
}