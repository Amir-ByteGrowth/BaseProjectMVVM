package com.amiritdev.kotlinbasewithcorutine.ui.secondfragment

import com.amiritdev.kotlinbasewithcorutine.BR
import com.amiritdev.kotlinbasewithcorutine.R
import com.amiritdev.kotlinbasewithcorutine.baseclasses.BaseFragment
import com.amiritdev.kotlinbasewithcorutine.databinding.LayoutSecondBinding

class SecondFragment : BaseFragment<LayoutSecondBinding, SecondViewModel>() {

    override val layoutId: Int
        get() = R.layout.layout_second
    override val viewModel: Class<SecondViewModel>
        get() = SecondViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel
}