package id.prasetio.aji.marsphotos.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.prasetio.aji.marsphotos.network.MarsApi
import kotlinx.coroutines.launch

class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the request status
    val status: LiveData<String> = _status

    // Memanggil fungsi getMarsPhotos() pada fungsi init
    init {
        getMarsPhotos()
    }

    // Mengambil Data Foto Mars menggunakan servis API Restorfit
    private fun getMarsPhotos() {
        viewModelScope.launch {
            try {
                // Jika data sukses di ambil maka akan menampilkan teks Success
                val listResult = MarsApi.retrofitService.getPhotos()
                _status.value = "Success: ${listResult.size} Mars photos retrieved"
            } catch (e: Exception) {
                // Bila data gagal diambil atau terjadi kesalahan, maka akan tampil teks Failure dengan teks errornya.
                _status.value = "Failure: ${e.message}"
            }
        }
    }
}