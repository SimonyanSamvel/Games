package com.example.tictactoe.ui.welcome

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.tictactoe.R
import com.example.tictactoe.ui.base.BaseFragment
import com.example.tictactoe.ui.game.GameFragment
import kotlinx.android.synthetic.main.fragment_welcome.*

class WelcomeFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_welcome, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        player1NameEditText.addTextChangedListener {
            if (player1NameEditText.text?.length == 8) {
                Toast.makeText(activity, "Max length", Toast.LENGTH_SHORT).show()
                return@addTextChangedListener
            }
        }
        player2NameEditText.addTextChangedListener {
            if (player2NameEditText.text?.length == 8) {
                Toast.makeText(activity, "Max length", Toast.LENGTH_SHORT).show()
                return@addTextChangedListener
            }
        }
        continueButton.setOnClickListener {
            if (player1NameEditText.text.isNullOrEmpty()) {
                Toast.makeText(activity, "Empty data provided", Toast.LENGTH_SHORT).show()
                if (player2NameEditText.text.isNullOrEmpty()) {
                    addFragment(
                        R.id.fragmentContainerView, GameFragment.newInstance(
                            "Player 1",
                            "Player 2"
                        )
                    )
                } else {
                    addFragment(
                        R.id.fragmentContainerView, GameFragment.newInstance(
                            "Player 1",
                            player2NameEditText.text.toString()
                        )
                    )
                }
            } else if (player2NameEditText.text.isNullOrEmpty()) {
                Toast.makeText(activity, "Empty data provided", Toast.LENGTH_SHORT).show()
                addFragment(
                    R.id.fragmentContainerView, GameFragment.newInstance(
                        player1NameEditText.text.toString(),
                        "Player 2"
                    )
                )
            } else {
                addFragment(
                    R.id.fragmentContainerView, GameFragment.newInstance(
                        player1NameEditText.text.toString(),
                        player2NameEditText.text.toString()
                    )
                )
            }
        }
    }

}