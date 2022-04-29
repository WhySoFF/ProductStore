package com.bsuir.productlist.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bsuir.productlist.R
import com.google.android.material.transition.MaterialSharedAxis

abstract class BaseFragment(layoutResId: Int): Fragment(layoutResId) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true).addTarget(R.id.root)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false).addTarget(R.id.root)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true).addTarget(R.id.root)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false).addTarget(R.id.root)
    }

}