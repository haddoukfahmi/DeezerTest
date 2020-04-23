package com.deezer.test.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deezer.test.data.model.DataTrackList
import com.deezer.test.data.repository.TrackListRepository
import com.deezer.test.utils.CoroutineScopeUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrackListViewModel(private val trackListRepository: TrackListRepository) : ViewModel() {


    private val _trackList = MutableLiveData<List<DataTrackList>>()
    private val _trackUrl = MutableLiveData<String>()
    val trackList: LiveData<List<DataTrackList>>
        get() = _trackList
    val trackUrl: LiveData<String>
        get() = _trackUrl

    init {
        _trackUrl.value = ""
    }

    fun getTrackList(albumId: String) {
        CoroutineScopeUtil.scope.launch {

            val result = trackListRepository.getTrackList(albumId)

            withContext(Dispatchers.Main) {
                _trackList.value = result.data
            }
        }
    }

    fun setTrackUrl(url: String) {

        _trackUrl.value = url
    }

    override fun onCleared() {
        super.onCleared()
        CoroutineScopeUtil.coroutineContext.cancel()
    }
}