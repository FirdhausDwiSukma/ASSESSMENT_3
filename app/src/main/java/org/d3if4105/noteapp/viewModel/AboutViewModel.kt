package org.d3if4105.noteapp.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if4105.noteapp.MainActivity
import org.d3if4105.noteapp.model.About
import org.d3if4105.noteapp.network.AboutApi
import org.d3if4105.noteapp.network.ApiStatus
import org.d3if4105.noteapp.network.UpdateWorker
import java.util.concurrent.TimeUnit

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

    fun scheduleUpdater(app: Application) {
        val request = OneTimeWorkRequestBuilder<UpdateWorker>()
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(app).enqueueUniqueWork(
            MainActivity.CHANNEL_ID,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }


    fun getTipsData(): LiveData<List<About>> = aboutData
    fun getStatus(): LiveData<ApiStatus> = status
}