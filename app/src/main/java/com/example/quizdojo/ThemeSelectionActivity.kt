package com.example.quizdojo

/* Theme Selection Activity */

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ThemeSelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme_selection)

        // Buttons for theme selection
        val mathematicsButton: Button = findViewById(R.id.mathematicsButton)
        val computerScienceButton: Button = findViewById(R.id.computerScienceButton)
        val geographyButton: Button = findViewById(R.id.geographyButton)
        val lawButton: Button = findViewById(R.id.lawButton)

        // Set onClick listeners for theme buttons
        mathematicsButton.setOnClickListener { selectTheme("Mathematics") }
        computerScienceButton.setOnClickListener { selectTheme("Computer Science") }
        geographyButton.setOnClickListener { selectTheme("Geography") }
        lawButton.setOnClickListener { selectTheme("Law") }
    }

    private fun selectTheme(theme: String) {
        // Pass the selected theme to MainActivity
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("THEME", theme)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
}
