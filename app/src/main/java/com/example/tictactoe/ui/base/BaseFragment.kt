package com.example.tictactoe.ui.base

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

private const val BACK_STACK_NAME = "myBackStackName"

open class BaseFragment : Fragment() {

    protected fun addFragment(@IdRes fragmentContainerView: Int, fragment: BaseFragment) {
        parentFragmentManager
            .beginTransaction()
            .add(fragmentContainerView, fragment)
            .addToBackStack(BACK_STACK_NAME)
            .commit()
    }

    protected fun replaceFragment(@IdRes fragmentContainerView: Int, fragment: BaseFragment) {
        parentFragmentManager
            .beginTransaction()
            .replace(fragmentContainerView, fragment)
            .commit()
    }
}