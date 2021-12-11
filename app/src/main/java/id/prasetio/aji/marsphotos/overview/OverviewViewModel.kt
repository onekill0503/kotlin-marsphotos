package id.prasetio.aji.marsphotos.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.prasetio.aji.marsphotos.network.MarsApi
import id.prasetio.aji.marsphotos.network.MarsPhoto
import kotlinx.coroutines.launch

enum class MarsApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel : ViewModel() {

    // Digunakan untuk mengambil data status koneksi
    private val _status = MutableLiveData<MarsApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<MarsApiStatus> = _status

    // Mengambil data URL foto dan di masukan menjadi list ke dalam varable _photos
    private val _photos = MutableLiveData<List<MarsPhoto>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val photos: LiveData<List<MarsPhoto>> = _photos

    // Memanggil fungsi getMarsPhotos() pada fungsi init
    init {
        getMarsPhotos()
    }

    // Mengambil Data Foto Mars menggunakan servis API Restorfit
    private fun getMarsPhotos() {

        viewModelScope.launch {
            // menampilkan status loading
            _status.value = MarsApiStatus.LOADING
            try {
                // Jika tidak ada error, maka foto tampil dengan status DONE
                _photos.value = MarsApi.retrofitService.getPhotos()
                _status.value = MarsApiStatus.DONE
            } catch (e: Exception) {
                // Jika Error maka akan menampilkan status ERROR serta data _photos akan di isi dengan list kosong.
                _status.value = MarsApiStatus.ERROR
                _photos.value = listOf()
            }
        }
    }
}