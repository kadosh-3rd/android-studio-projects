package com.example.quizdojo

/* Main Activity */

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    // Declare UI elements
    private lateinit var questionText: TextView
    private lateinit var timerText: TextView
    private lateinit var scoreText: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var optionButtons: Array<Button>
    private lateinit var backButton: Button

    // Map of themes to questions
    private val themeToQuestions = mapOf(
        "Mathematics" to listOf(
            Question("What is 5 + 3?", listOf("6", "8", "7", "9"), 1),
            Question("Pythagoras' Theorem", listOf("h/2", "(B*h)/2L", "(B*h)/2", "Does not exist"), 2),
            Question("What is the square root of 81?", listOf("5", "6", "9", "8"), 2),
            Question("If 5x+3=18, what is the value of x?", listOf("2", "3", "4", "5"),1),
            Question("What is the area of a triangle with a base of 5 cm and a height of 10 cm?",
                listOf("50 cm²", "25 cm²", "30 cm²", "15 cm²"), 1)
        ),
        "Computer Science" to listOf(
            Question("What does CPU stand for?", listOf("Central Processing Unit", "Control Panel Unit", "Compute Power Unit", "Core Processing Unit"), 0),
            Question("Which language is known as the backbone of web development?", listOf("JavaScript", "Python", "Ruby", "Java"), 0),
            Question("Which data structure uses the LIFO (Last In, First Out) principle?",
                listOf("Queue", "Array", "Stack", "Linked List"),2
            ),
            Question("What is the time complexity of binary search in a sorted array?",listOf("O(n)", "O(1)","O(log n)", "O(n log n)"),2),
            Question("What is the main purpose of an operating system?",
                listOf("To manage hardware resources and provide an environment for software to run", "To compile and execute code", "To provide internet access", "To create and manage databases"),
                0)
        ),
        "Geography" to listOf(
            Question("What is the capital of Australia?", listOf("Sydney", "Canberra", "Melbourne", "Brisbane"), 1),
            Question("What is the capital of France?", listOf("Paris", "Berlin", "Madrid", "Rome"), 0),
            Question("Which continent is the largest?", listOf("Africa", "Asia", "Europe", "Antarctica"), 1),
            Question("Which is the largest continent by land area?", listOf("Asia", "Europe", "North America", "South America"), 0),
            Question("Which one of these regions is not part of Rwanda?", listOf("Musanze", "Kigali", "Nyanga", "Gisenyi"),2)
        ),
        "Law" to listOf(
            Question("What is the first article of the US Constitution?", listOf("Legislative Powers", "Judicial Powers", "Executive Powers", "Bill of Rights"), 0),
            Question("Which legal system is used in the UK?", listOf("Civil Law", "Common Law", "Roman Law", "Customary Law"), 1),
            Question("What is the primary source of law in the United States?", listOf("Civil Law", "The Constitution", "Family Law", "Immigration Law"), 1),
            Question("Which legal principle means \"let the decision stand,\" referring to the obligation of courts to follow historical cases when making a ruling?",
                listOf("Habeas Corpus", "Mens Rea", "Res Ipsa Loquitur", "Stare Decisis"),3
            ),
            Question("What is the term for a legal proceeding in which a case is brought before a higher court for review of the lower court's judgment?",
                listOf("Trial", "Arbitration", "Mediation", "Appeal"),3
            )
        )
    )

    // Initialize variables
    private var currentQuestions: List<Question> = emptyList()
    private var currentQuestionIndex = 0
    private var score = 0
    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI elements
        questionText = findViewById(R.id.questionText)
        timerText = findViewById(R.id.timerText)
        scoreText = findViewById(R.id.scoreText)
        progressBar = findViewById(R.id.progressBar)
        backButton = findViewById(R.id.backText)

        // Initialize option buttons
        optionButtons = arrayOf(
            findViewById(R.id.option1),
            findViewById(R.id.option2),
            findViewById(R.id.option3),
            findViewById(R.id.option4)
        )

        // Get selected theme from intent
        val theme = intent.getStringExtra("THEME")
        currentQuestions = themeToQuestions[theme] ?: emptyList()

        // Set onClick listener for back button interrupting the quiz
        backButton.setOnClickListener {
            val intent = Intent(this, ThemeSelectionActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }

        // Set onClick listeners for each option button
        optionButtons.forEachIndexed { index, button ->
            button.setOnClickListener { checkAnswer(index) }
        }

        loadQuestion()
    }

    // Load the next question
    private fun loadQuestion() {
        if (currentQuestionIndex >= currentQuestions.size) {
            // Navigate to ScoreActivity when the quiz ends
            val intent = Intent(this, ScoreActivity::class.java)
            intent.putExtra("SCORE", score)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
            return
        }

        // Update the UI with the current question
        val currentQuestion = currentQuestions[currentQuestionIndex]
        questionText.text = currentQuestion.question
        currentQuestion.options.forEachIndexed { index, option ->
            optionButtons[index].text = option
        }

        // Add fade-in animation to the question text
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.duration = 500
        questionText.startAnimation(fadeIn)

        // Reset timer and progress bar
        progressBar.progress = 100
        startTimer()
    }

    // Start the timer
    private fun startTimer() {
        timer = object : CountDownTimer(30000, 100) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                progressBar.progress = (millisUntilFinished / 100).toInt()
                timerText.text = "Time: ${millisUntilFinished / 1000} sec"
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                timerText.text = "Time's up!"
                moveToNextQuestion()
            }
        }.start()
    }

    // Check on the selected answer
    @SuppressLint("SetTextI18n")
    private fun checkAnswer(selectedIndex: Int) {
        timer.cancel()
        val currentQuestion = currentQuestions[currentQuestionIndex]

        if (selectedIndex == currentQuestion.correctOption) {
            // Update score for a correct answer
            score += 10
            scoreText.text = "Score: $score"
            optionButtons[selectedIndex].setBackgroundColor(
                ContextCompat.getColor(this, android.R.color.holo_green_light)
            )

            // Add a bounce animation to the score text
            val animator = ObjectAnimator.ofFloat(scoreText, "scaleX", 1.0f, 1.2f, 1.0f)
            animator.duration = 300
            animator.start()
        } else {
            // Highlight correct answer and selected answer
            optionButtons[selectedIndex].setBackgroundColor(
                ContextCompat.getColor(this, android.R.color.holo_red_light)
            )
            optionButtons[currentQuestion.correctOption].setBackgroundColor(
                ContextCompat.getColor(this, android.R.color.holo_green_light)
            )
        }

        // Wait for a moment before moving to the next question
        optionButtons.forEach { it.isClickable = false }
        optionButtons[0].postDelayed({
            resetOptionButtons()
            moveToNextQuestion()
        }, 1000)
    }

    // Reset option buttons
    private fun resetOptionButtons() {
        optionButtons.forEach {
            it.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
            it.isClickable = true
        }
    }

    private fun moveToNextQuestion() {
        currentQuestionIndex++
        loadQuestion()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::timer.isInitialized) timer.cancel()
    }
}
