package com.example.tictactoe.ui.game

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.example.tictactoe.R
import com.example.tictactoe.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_game.*
import kotlin.system.exitProcess

class GameFragment : BaseFragment() {
    private lateinit var player1Name: String
    private lateinit var player2Name: String

    // player winning count
    private var player1Count = 0
    private var player2Count = 0
    private var player1Turn = true
    private var player2Turn = false
    private var stepCount = 1
    private var gameCount = 1
    private var player1 = ArrayList<Int>()
    private var player2 = ArrayList<Int>()
    private var emptyCells = ArrayList<Int>()
    private var activeUser = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_game, container, false)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        player1Name = arguments?.getSerializable(ARG_PARAM1) as String
        player1TextView.text = "$player1Name : $player1Count"
        player2Name = arguments?.getSerializable(ARG_PARAM2) as String
        player2TextView.text = "$player2Name : $player2Count"
        setUpView()
    }

    // this function handle the click event on the board.
    @SuppressLint("SetTextI18n")
    fun playerTap(imageView: ImageView, cellID: Int) {
        if (player1Turn) {
            gameStatusTextView.text = "X's Turn - Tap to play"
            player1Turn = false
            Handler().postDelayed({ player2Turn = true }, 100)
        } else {
            gameStatusTextView.text = "O's Turn - Tap to play"
            player2Turn = false
            Handler().postDelayed({ player1Turn = true }, 100)
        }
        stepCount++
        playNow(imageView, cellID)
    }

    // this function update update the game board after every move.
    private fun playNow(imageSelected: ImageView, currCell: Int) {
        val audio = MediaPlayer.create(activity, R.raw.click)
        if (activeUser == 1) {
            imageSelected.setImageResource(R.drawable.ic_baseline_cross_24)
            player1.add(currCell)
            emptyCells.add(currCell)
            audio.start()
            imageSelected.isEnabled = false
            Handler().postDelayed({ audio.release() }, 200)
            val checkWinner = checkWinner()
            if (checkWinner == 1) {
                Handler().postDelayed({ reset() }, 0)
            } else
                activeUser = 2
        } else {
            imageSelected.setImageResource(R.drawable.ic_outline_circle_24)
            audio.start()
            activeUser = 1
            player2.add(currCell)
            emptyCells.add(currCell)
            Handler().postDelayed({ audio.release() }, 200)
            imageSelected.isEnabled = false
            val checkWinner = checkWinner()
            if (checkWinner == 1)
                Handler().postDelayed({ reset() }, 0)
        }
    }

    private fun checkWinner(): Int {
        val audio = MediaPlayer.create(activity, R.raw.win)
        if (checkPlayer(player1)) {
            if (gameCount % 2 != 0) {
                player1Count += 1
            } else {
                player2Count += 1
            }
            imageDisable()
            audio.start()
            disableReset()
            Handler().postDelayed({ audio.release() }, 200)
            return buildDialog(1)
        } else if (checkPlayer(player2)) {
            if (gameCount % 2 != 0) {
                player1Count += 1
            } else {
                player2Count += 1
            }
            audio.start()
            imageDisable()
            disableReset()
            Handler().postDelayed({ audio.release() }, 200)
            return buildDialog(2)
        } else if (emptyCells.contains(1) && emptyCells.contains(2) && emptyCells.contains(3) && emptyCells.contains(
                4
            ) && emptyCells.contains(5) && emptyCells.contains(6) && emptyCells.contains(7) &&
            emptyCells.contains(8) && emptyCells.contains(9)
        ) {
            return buildDialog(0)
        }
        return 0
    }

    private fun buildDialog(winner: Int): Int {
        val build = activity?.let { AlertDialog.Builder(it) }
        gameCount++
        when (winner) {
            0 -> {
                build?.setTitle("Game Draw")
                build?.setMessage("Nobody Wins" + "\n" + "Do you want to play again?")
            }
            1 -> {
                build?.setTitle("$player1Name won the game...")
                build?.setMessage("Do you want to play again?")
            }
            2 -> {
                build?.setTitle("$player2Name won the game...")
                build?.setMessage("Do you want to play again?")
            }
        }
        build?.setPositiveButton("Ok") { _, _ ->
            reset()
        }
        build?.setNegativeButton("Exit") { _, _ ->
            exitProcess(1)
        }
        build?.show()
        return 1
    }

    private fun checkPlayer(list: ArrayList<Int>): Boolean {
        if (list.size < 3) {
            return false
        }
        if ((list.contains(1) && list.contains(2) && list.contains(3)) || (list.contains(
                1
            ) && list.contains(4) && list.contains(7)) ||
            (list.contains(3) && list.contains(6) && list.contains(9)) || (list.contains(
                7
            ) && list.contains(8) && list.contains(9)) ||
            (list.contains(4) && list.contains(5) && list.contains(6)) || (list.contains(
                1
            ) && list.contains(5) && list.contains(9)) ||
            list.contains(3) && list.contains(5) && list.contains(7) || (list.contains(2) && list.contains(
                5
            ) && list.contains(8))
        ) {
            return true
        }
        return false
    }

    private fun imageDisable() {
        for (i in 1..9) {
            val imageSelected = when (i) {
                1 -> imageView1
                2 -> imageView2
                3 -> imageView3
                4 -> imageView4
                5 -> imageView5
                6 -> imageView6
                7 -> imageView7
                8 -> imageView8
                9 -> imageView9
                else -> {
                    return
                }
            }
            if (imageSelected.isEnabled) {
                !imageSelected.isEnabled
            }
        }
    }

    // this function resets the game.
    @SuppressLint("SetTextI18n")
    fun reset() {
        gameCount++
        if (gameCount % 2 != 0) {
            gameStatusTextView.text = "X's Turn - Tap to play"
        } else {
            gameStatusTextView.text = "O's Turn - Tap to play"
        }
        player1.clear()
        player2.clear()
        emptyCells.clear()
        activeUser = 1
        for (i in 1..9) {
            val imageSelected = when (i) {
                1 -> imageView1
                2 -> imageView2
                3 -> imageView3
                4 -> imageView4
                5 -> imageView5
                6 -> imageView6
                7 -> imageView7
                8 -> imageView8
                9 -> imageView9
                else -> {
                    return
                }
            }
            imageSelected.isEnabled = true
            imageSelected.setImageResource(0)
        }
        player1TextView.text = "$player1Name : $player1Count"
        player2TextView.text = "$player2Name : $player2Count"
    }

    // this function disable all the button on the board for a while.
    private fun disableReset() {
        resetButton.isEnabled = false
        Handler().postDelayed({ resetButton.isEnabled = true }, 1200)
    }

    @SuppressLint("SetTextI18n")
    private fun setUpView() {
        gameToolBar?.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
        gameStatusTextView.text = "X's Turn - Tap to play"
        resetButton.setOnClickListener {
            reset()
        }
        imageView1.setOnClickListener {
            playerTap(imageView1, 1)
        }
        imageView2.setOnClickListener {
            playerTap(imageView2, 2)
        }
        imageView3.setOnClickListener {
            playerTap(imageView3, 3)
        }
        imageView4.setOnClickListener {
            playerTap(imageView4, 4)
        }
        imageView5.setOnClickListener {
            playerTap(imageView5, 5)
        }
        imageView6.setOnClickListener {
            playerTap(imageView6, 6)
        }
        imageView7.setOnClickListener {
            playerTap(imageView7, 7)
        }
        imageView8.setOnClickListener {
            playerTap(imageView8, 8)
        }
        imageView9.setOnClickListener {
            playerTap(imageView9, 9)
        }
    }

    companion object {
        private const val ARG_PARAM1 = "name1"
        private const val ARG_PARAM2 = "name2"

        @JvmStatic
        fun newInstance(name1: String, name2: String) =
            GameFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, name1)
                    putSerializable(ARG_PARAM2, name2)
                }
            }
    }
}