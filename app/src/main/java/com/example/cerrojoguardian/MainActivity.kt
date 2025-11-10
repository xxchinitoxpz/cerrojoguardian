package com.example.cerrojoguardian

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var displayTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private var currentNumber: String = ""
    private var previousNumber: String = ""
    private var operation: String = ""
    private var shouldResetDisplay: Boolean = false
    private var secretCode: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("CerrojoGuardianPrefs", Context.MODE_PRIVATE)
        secretCode = sharedPreferences.getString("secret_code", "") ?: ""

        // Verificar si la app "Operación Cerrojo" está instalada
        // Verifica tanto la versión release como debug
        val isInstalled = AppChecker.isOperacionCerrojoInstalled(this)
        
        // Guardar el estado en SharedPreferences para uso posterior
        sharedPreferences.edit()
            .putBoolean("target_app_installed", isInstalled)
            .apply()
        
        // Puedes usar esta información como necesites
        // Por ejemplo, mostrar un mensaje o cambiar el comportamiento de la app
        if (isInstalled) {
            // La app "Operación Cerrojo" está instalada
            // Aquí puedes hacer lo que necesites cuando está instalada
        } else {
            // La app "Operación Cerrojo" NO está instalada
            // Aquí puedes hacer lo que necesites cuando NO está instalada
        }

        displayTextView = findViewById(R.id.displayTextView)

        // Botones de números
        findViewById<Button>(R.id.btn0).setOnClickListener { appendNumber("0") }
        findViewById<Button>(R.id.btn1).setOnClickListener { appendNumber("1") }
        findViewById<Button>(R.id.btn2).setOnClickListener { appendNumber("2") }
        findViewById<Button>(R.id.btn3).setOnClickListener { appendNumber("3") }
        findViewById<Button>(R.id.btn4).setOnClickListener { appendNumber("4") }
        findViewById<Button>(R.id.btn5).setOnClickListener { appendNumber("5") }
        findViewById<Button>(R.id.btn6).setOnClickListener { appendNumber("6") }
        findViewById<Button>(R.id.btn7).setOnClickListener { appendNumber("7") }
        findViewById<Button>(R.id.btn8).setOnClickListener { appendNumber("8") }
        findViewById<Button>(R.id.btn9).setOnClickListener { appendNumber("9") }

        // Botón de punto decimal
        findViewById<Button>(R.id.btnDecimal).setOnClickListener { appendDecimal() }

        // Botones de operaciones
        findViewById<Button>(R.id.btnAdd).setOnClickListener { setOperation("+") }
        findViewById<Button>(R.id.btnSubtract).setOnClickListener { setOperation("-") }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { setOperation("*") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { setOperation("/") }

        // Botón de igual
        findViewById<Button>(R.id.btnEquals).setOnClickListener { calculate() }

        // Botón de limpiar
        findViewById<Button>(R.id.btnClear).setOnClickListener { clear() }
    }

    private fun appendNumber(number: String) {
        if (shouldResetDisplay) {
            currentNumber = ""
            shouldResetDisplay = false
        }
        currentNumber += number
        updateDisplay()
    }

    private fun appendDecimal() {
        if (shouldResetDisplay) {
            currentNumber = ""
            shouldResetDisplay = false
        }
        if (currentNumber.isEmpty()) {
            currentNumber = "0."
        } else if (!currentNumber.contains(".")) {
            currentNumber += "."
        }
        updateDisplay()
    }

    private fun setOperation(op: String) {
        if (currentNumber.isEmpty()) return

        if (previousNumber.isNotEmpty() && operation.isNotEmpty() && !shouldResetDisplay) {
            calculate()
        } else {
            previousNumber = currentNumber
        }

        operation = op
        shouldResetDisplay = true
    }

    private fun calculate() {
        // Detectar si se ingresó la clave secreta (número de 4 dígitos sin operación)
        if (previousNumber.isEmpty() && operation.isEmpty() && currentNumber.length == 4) {
            if (currentNumber == secretCode) {
                // Clave correcta - mostrar mensaje secreto
                showSecretMessage()
                return
            }
        }

        if (previousNumber.isEmpty() || currentNumber.isEmpty() || operation.isEmpty()) {
            return
        }

        val prev = previousNumber.toDoubleOrNull() ?: return
        val curr = currentNumber.toDoubleOrNull() ?: return

        val result = when (operation) {
            "+" -> prev + curr
            "-" -> prev - curr
            "*" -> prev * curr
            "/" -> {
                if (curr == 0.0) {
                    displayTextView.text = "Error"
                    clear()
                    return
                } else {
                    prev / curr
                }
            }
            else -> return
        }

        // Formatear el resultado para eliminar decimales innecesarios
        currentNumber = if (result % 1 == 0.0) {
            result.toInt().toString()
        } else {
            result.toString()
        }

        previousNumber = ""
        operation = ""
        shouldResetDisplay = true
        updateDisplay()
    }

    private fun showSecretMessage() {
        val intent = Intent(this, SecretMessageActivity::class.java)
        startActivity(intent)
        clear()
    }

    private fun clear() {
        currentNumber = ""
        previousNumber = ""
        operation = ""
        shouldResetDisplay = false
        updateDisplay()
    }

    private fun updateDisplay() {
        displayTextView.text = if (currentNumber.isEmpty()) "0" else currentNumber
    }
}

