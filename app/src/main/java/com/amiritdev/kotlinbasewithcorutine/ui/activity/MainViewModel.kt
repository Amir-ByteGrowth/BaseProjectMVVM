package com.amiritdev.kotlinbasewithcorutine.ui.activity

//import androidx.hilt.lifecycle.ViewModelInject
import com.amiritdev.kotlinbasewithcorutine.baseclasses.BaseViewModel
import com.amiritdev.kotlinbasewithcorutine.data.remote.reporitory.MainRepository
import com.amiritdev.kotlinbasewithcorutine.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {


}
