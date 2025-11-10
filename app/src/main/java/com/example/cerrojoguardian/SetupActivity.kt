package com.example.cerrojoguardian

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SetupActivity : AppCompatActivity() {

    private lateinit var codeDisplay: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private var enteredCode: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)

        sharedPreferences = getSharedPreferences("CerrojoGuardianPrefs", Context.MODE_PRIVATE)

        codeDisplay = findViewById(R.id.codeDisplay)

        // Botones numéricos
        findViewById<Button>(R.id.btnNum0).setOnClickListener { addDigit("0") }
        findViewById<Button>(R.id.btnNum1).setOnClickListener { addDigit("1") }
        findViewById<Button>(R.id.btnNum2).setOnClickListener { addDigit("2") }
        findViewById<Button>(R.id.btnNum3).setOnClickListener { addDigit("3") }
        findViewById<Button>(R.id.btnNum4).setOnClickListener { addDigit("4") }
        findViewById<Button>(R.id.btnNum5).setOnClickListener { addDigit("5") }
        findViewById<Button>(R.id.btnNum6).setOnClickListener { addDigit("6") }
        findViewById<Button>(R.id.btnNum7).setOnClickListener { addDigit("7") }
        findViewById<Button>(R.id.btnNum8).setOnClickListener { addDigit("8") }
        findViewById<Button>(R.id.btnNum9).setOnClickListener { addDigit("9") }

        // Botón borrar
        findViewById<Button>(R.id.btnDelete).setOnClickListener { deleteDigit() }

        // Botón confirmar
        findViewById<Button>(R.id.btnConfirm).setOnClickListener { confirmCode() }
    }

    private fun addDigit(digit: String) {
        if (enteredCode.length < 4) {
            enteredCode += digit
            updateDisplay()
            
            // Si ya tiene 4 dígitos, validar automáticamente
            if (enteredCode.length == 4) {
                confirmCode()
            }
        }
    }

    private fun deleteDigit() {
        if (enteredCode.isNotEmpty()) {
            enteredCode = enteredCode.dropLast(1)
            updateDisplay()
        }
    }

    private fun updateDisplay() {
        val display = "*".repeat(enteredCode.length) + "_".repeat(4 - enteredCode.length)
        codeDisplay.text = display.replace("", " ").trim()
    }

    private fun confirmCode() {
        if (enteredCode.length != 4) {
            Toast.makeText(this, "Ingresa 4 dígitos", Toast.LENGTH_SHORT).show()
            return
        }

        // Guardar la clave en SharedPreferences
        sharedPreferences.edit()
            .putString("secret_code", enteredCode)
            .putBoolean("is_setup_complete", true)
            .apply()

        Toast.makeText(this, "Clave guardada correctamente", Toast.LENGTH_SHORT).show()
        
        // Ir a la calculadora
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

