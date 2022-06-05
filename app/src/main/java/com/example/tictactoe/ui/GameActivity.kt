package com.example.tictactoe.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.R
import com.example.tictactoe.ui.base.BaseActivity
import com.example.tictactoe.ui.welcome.WelcomeFragment

class GameActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_layout)
        beginTransaction(R.id.fragmentContainerView, WelcomeFragment())
    }
}