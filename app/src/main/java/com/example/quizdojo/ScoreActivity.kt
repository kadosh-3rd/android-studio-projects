package com.example.quizdojo

/* Score Activity */

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ScoreActivity : AppCompatActivity() {
    private var iconResult = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        // Initialize UI elements
        val scoreText: TextView = findViewById(R.id.finalScoreText)
        val score = intent.getIntExtra("SCORE", 0)
        val backButton: Button = findViewById(R.id.backButton)
        val exitButton: Button = findViewById(R.id.exitButton)

        // Set the appropriate icon based on the score
        iconResult = when (score) {
            0 -> "\uD83D\uDC94 \uD83D\uDE2D"
            10 -> "\uD83E\uDD27 \uD83D\uDE13"
            20 -> "\uD83E\uDD15 \uD83E\uDD72"
            30 -> "\uD83D\uDE2E\u200D\uD83D\uDCA8 \uD83D\uDE42"
            40 -> "\uD83D\uDE0F"
            50 -> "\uD83C\uDF8A \uD83D\uDE0E"
            else -> ""
        }
        // Update the score text
        scoreText.text = "Your Score: $score $iconResult"

        // Set onClick listener for back button
        backButton.setOnClickListener {
            val intent = Intent(this, ThemeSelectionActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }

        // Set onClick listener for exit button
        exitButton.setOnClickListener {
            finishAffinity() // Close all activities and exit
        }

        // Add fade-in animation to the score text
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.duration = 500
        scoreText.startAnimation(fadeIn)
    }
}