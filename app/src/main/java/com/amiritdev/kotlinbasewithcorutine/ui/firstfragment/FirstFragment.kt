package com.amiritdev.kotlinbasewithcorutine.ui.firstfragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.amiritdev.kotlinbasewithcorutine.BR
import com.amiritdev.kotlinbasewithcorutine.R
import com.amiritdev.kotlinbasewithcorutine.baseclasses.BaseFragment
import com.amiritdev.kotlinbasewithcorutine.data.local.datastore.DataStoreProvider
import com.amiritdev.kotlinbasewithcorutine.data.models.PostsResponseItem
import com.amiritdev.kotlinbasewithcorutine.data.remote.Resource
import com.amiritdev.kotlinbasewithcorutine.databinding.FirstFragmentBinding
import com.amiritdev.kotlinbasewithcorutine.ui.firstfragment.adapter.PostsRecyclerAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FirstFragment : BaseFragment<FirstFragmentBinding, FirstViewModel>() {

    override val layoutId: Int
        get() = R.layout.first_fragment
    override val viewModel: Class<FirstViewModel>
        get() = FirstViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var adapter: PostsRecyclerAdapter
    private var postsList: ArrayList<PostsResponseItem> = ArrayList()
    lateinit var dataStoreProvider: DataStoreProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Get reference to our Data Store Provider class
        dataStoreProvider = DataStoreProvider(requireContext())


        subscribeToObserveDataStore()

        //calling api
        mViewModel.fetchPostsFromApi()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialising()
    }

    override fun subscribeToViewLiveData() {
        super.subscribeToViewLiveData()
        mViewModel.btnClick.observe(this, Observer {

            //observing data from edittext
            mViewModel.myedittext.get()?.let {

                //setting data to textview
                mViewModel.myName.set(it)

                //saving data to data store
                //Stores the values
                GlobalScope.launch {
                    dataStoreProvider.storeData(false, it)
                }
            }
        })
    }

    private fun subscribeToObserveDataStore() {

        //observing data from data store and showing
        dataStoreProvider.userNameFlow.asLiveData().observe(this, Observer {
            mViewModel.myName.set(it)
        })

    }


    private fun initialising() {

        adapter = PostsRecyclerAdapter(postsList, object : PostsRecyclerAdapter.ClickItemListener {
            override fun onClicked(position: Int) {
                Navigation.findNavController(mViewDataBinding.recyclerPosts)
                    .navigate(R.id.action_firstFragment_to_secondFragment)
            }

            override fun onProductLiked(position: Int, isLiked: Boolean) {
            }

        })

       mViewDataBinding. recyclerPosts.layoutManager = LinearLayoutManager(requireContext())
        mViewDataBinding.recyclerPosts.adapter = adapter

    }

    //subscribing to network live data
    override fun subscribeToNetworkLiveData() {
        super.subscribeToNetworkLiveData()

        mViewModel.postsData.observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    hideProgressBar()
                    it.data?.let {
                        postsList.addAll(it)
                        adapter.notifyDataSetChanged()
                    }

                }
                Resource.Status.LOADING -> {
                    showProgressBar()
                }
                Resource.Status.ERROR -> {
                    hideProgressBar()

                    Snackbar.make(mViewDataBinding.recyclerPosts!!, it.message!!, Snackbar.LENGTH_SHORT)
                        .show()

                }
            }
        })
    }


}