package com.amiritdev.kotlinbasewithcorutine.ui.firstfragment

import androidx.databinding.ObservableField
//import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amiritdev.kotlinbasewithcorutine.baseclasses.BaseViewModel
import com.amiritdev.kotlinbasewithcorutine.data.models.PostsResponse
import com.amiritdev.kotlinbasewithcorutine.data.remote.Resource
import com.amiritdev.kotlinbasewithcorutine.data.remote.reporitory.MainRepository
import com.amiritdev.kotlinbasewithcorutine.utils.NetworkHelper
import com.amiritdev.kotlinbasewithcorutine.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirstViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {


    private val _posts = MutableLiveData<Resource<PostsResponse>>()
    val postsData: LiveData<Resource<PostsResponse>>
        get() = _posts

    val myName = ObservableField<String>()
    val myedittext = ObservableField<String>()
    val btnClick = SingleLiveEvent<Any>()


    fun fetchPostsFromApi() {
        viewModelScope.launch {
            _posts.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.getPosts().let {
                    if (it.isSuccessful) {
                        _posts.postValue(Resource.success(it.body()!!))
                    } else _posts.postValue(Resource.error(it.message(), null))
                }
            } else _posts.postValue(Resource.error("No internet connection", null))
        }
    }

    fun onClick() {
        btnClick.call()
    }

}