package com.example.tictactoe.ui.base

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    protected fun beginTransaction(@IdRes fragmentContainerView: Int, fragment: BaseFragment) {
        supportFragmentManager.beginTransaction()
            .add(fragmentContainerView, fragment)
            .commit()
    }
}