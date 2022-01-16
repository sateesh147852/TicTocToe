package com.tictoctoe

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.tictoctoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private var currentPlayer = 1  // 1 = green 2 = Red  0 = null
    private var isStarted: Boolean = false
    private var isGameOver = false
    private var counter = 0
    private var handler : Handler? = null
    private var runnable : Runnable? = null

    var Positions = arrayOf(
        intArrayOf(0, 1, 2),
        intArrayOf(3, 4, 5),
        intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6),
        intArrayOf(1, 4, 7),
        intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8),
        intArrayOf(2, 4, 6)
    )

    var gameState = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeClickListener()
        initializeData()
    }

    private fun initializeData() {
        binding.btStart.setOnClickListener {
            isGameOver = false
            if (isStarted) {
                binding.btStart.text = "Start"
                isStarted = false
                clearTheGame()
            } else {
                binding.btStart.text = "Restart"
                isStarted = true
                startTimer(true)
            }
        }
    }

    private fun clearTheGame() {

        binding.ivZero.setImageResource(0)
        binding.ivOne.setImageResource(0)
        binding.ivTwo.setImageResource(0)

        binding.ivThree.setImageResource(0)
        binding.ivFour.setImageResource(0)
        binding.ivFive.setImageResource(0)

        binding.ivSix.setImageResource(0)
        binding.ivSeven.setImageResource(0)
        binding.ivEight.setImageResource(0)

        gameState[0] = 0
        gameState[1] = 0
        gameState[2] = 0

        gameState[3] = 0
        gameState[4] = 0
        gameState[5] = 0

        gameState[6] = 0
        gameState[7] = 0
        gameState[8] = 0

        currentPlayer = 1
        counter = 0
        binding.tvWinner.text = ""
        binding.tvTimer.text = "0:0:0"
        startTimer(false)

    }

    private fun initializeClickListener() {
        binding.ivZero.setOnClickListener(this)
        binding.ivOne.setOnClickListener(this)
        binding.ivTwo.setOnClickListener(this)

        binding.ivThree.setOnClickListener(this)
        binding.ivFour.setOnClickListener(this)
        binding.ivFive.setOnClickListener(this)

        binding.ivSix.setOnClickListener(this)
        binding.ivSeven.setOnClickListener(this)
        binding.ivEight.setOnClickListener(this)
    }

    private fun setVisibility(position: Int) {
        if (isStarted && !isGameOver && gameState[position] == 0) {
            counter++
            if (currentPlayer == 1) {

                gameState[position] = 1
                when (position) {

                    0 -> binding.ivZero.setImageResource(R.drawable.green)
                    1 -> binding.ivOne.setImageResource(R.drawable.green)
                    2 -> binding.ivTwo.setImageResource(R.drawable.green)

                    3 -> binding.ivThree.setImageResource(R.drawable.green)
                    4 -> binding.ivFour.setImageResource(R.drawable.green)
                    5 -> binding.ivFive.setImageResource(R.drawable.green)

                    6 -> binding.ivSix.setImageResource(R.drawable.green)
                    7 -> binding.ivSeven.setImageResource(R.drawable.green)
                    8 -> binding.ivEight.setImageResource(R.drawable.green)
                }



                currentPlayer = 2
            } else {

                gameState[position] = 2
                when (position) {

                    0 -> binding.ivZero.setImageResource(R.drawable.red)
                    1 -> binding.ivOne.setImageResource(R.drawable.red)
                    2 -> binding.ivTwo.setImageResource(R.drawable.red)

                    3 -> binding.ivThree.setImageResource(R.drawable.red)
                    4 -> binding.ivFour.setImageResource(R.drawable.red)
                    5 -> binding.ivFive.setImageResource(R.drawable.red)

                    6 -> binding.ivSix.setImageResource(R.drawable.red)
                    7 -> binding.ivSeven.setImageResource(R.drawable.red)
                    8 -> binding.ivEight.setImageResource(R.drawable.red)

                }

                currentPlayer = 1
            }

            checkWinner(currentPlayer)
        } else if (gameState[position] != 0) {

        } else if (isGameOver)
            Toast.makeText(this, "Game is over. Please restart the game", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "Please start the game", Toast.LENGTH_SHORT).show()
    }

    private fun checkWinner(currentPlayer: Int) {

        for (position in Positions) {
            if (gameState[position[0]] == gameState[position[1]] &&
                gameState[position[1]] == gameState[position[2]] &&
                gameState[position[0]] != 0
            ) {
                startTimer(false)
                isGameOver = true
                if (currentPlayer == 1)
                    binding.tvWinner.text = "Red has won the game"
                else
                    binding.tvWinner.text = "Green has won the game"
            } else if (counter == 9) {
                startTimer(false)
                binding.tvWinner.text = "Game is drawn"
            }
        }

    }

    override fun onClick(view: View) {

        when (view.id) {

            R.id.ivZero -> setVisibility(0)
            R.id.ivOne -> setVisibility(1)
            R.id.ivTwo -> setVisibility(2)

            R.id.ivThree -> setVisibility(3)
            R.id.ivFour -> setVisibility(4)
            R.id.ivFive -> setVisibility(5)

            R.id.ivSix -> setVisibility(6)
            R.id.ivSeven -> setVisibility(7)
            R.id.ivEight -> setVisibility(8)

        }

    }

    private fun startTimer(startTimer: Boolean) {

        if (startTimer) {
            var timerSecond: Long = 0
            handler = Handler(Looper.myLooper()!!)
            runnable = object : Runnable {
                override fun run() {
                    timerSecond += 1
                    if (timerSecond % 2 == 0L)
                        binding.tvTimer.setTextColor(
                            ContextCompat.getColor(
                                this@MainActivity,
                                R.color.teal_200
                            )
                        )
                    else
                        binding.tvTimer.setTextColor(
                            ContextCompat.getColor(
                                this@MainActivity,
                                R.color.purple_500
                            )
                        )

                    binding.tvTimer.text = getTime(timerSecond)
                    handler?.postDelayed(this, 1000)
                }
            }
            handler?.postDelayed(runnable!!, 1000)
        }

        if (!startTimer && handler != null && runnable != null) {
            handler?.removeCallbacks(runnable!!)
        }

    }

    private fun getTime(totalSecs: Long): String {
        val hours = totalSecs / 3600;
        val minutes = (totalSecs % 3600) / 60;
        val seconds = totalSecs % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}