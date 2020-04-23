package com.deezer.test.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deezer.test.data.model.Data
import com.deezer.test.data.repository.AlbumRepository
import com.deezer.test.utils.CoroutineScopeUtil.coroutineContext
import com.deezer.test.utils.CoroutineScopeUtil.scope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumViewModel(private val albumRepository: AlbumRepository) : ViewModel() {


    private val _albumList = MutableLiveData<List<Data>>()
    val albumList: LiveData<List<Data>>
        get() = _albumList
    private val _albumDetail = MutableLiveData<Data>()
    val albumDetail: LiveData<Data>
        get() = _albumDetail

    init {

        getAlbum()
    }

    private fun getAlbum() {
        scope.launch {
            val result = albumRepository.getAlbums()

            withContext(Dispatchers.Main) {
                _albumList.value = result.data
            }
        }
    }

    fun setAlbumDetails(data: Data) {
        _albumDetail.value = data
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }
}