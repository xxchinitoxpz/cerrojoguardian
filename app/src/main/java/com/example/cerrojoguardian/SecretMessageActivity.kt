package com.example.cerrojoguardian

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecretMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secret_message)

        val messageText = findViewById<TextView>(R.id.secretMessageText)
        val appStatusText = findViewById<TextView>(R.id.appStatusText)
        val appStatusDetail = findViewById<TextView>(R.id.appStatusDetail)

        messageText.text = "ENTRASTE AL LUGAR SECRETO"

        // Verificar si la app "Operación Cerrojo" está instalada
        val isInstalled = AppChecker.isOperacionCerrojoInstalled(this)
        
        if (isInstalled) {
            appStatusText.text = "✓ App 'Operación Cerrojo' INSTALADA"
            appStatusText.setTextColor(getColor(R.color.teal_200))
            appStatusDetail.text = "Package: com.hackathon.operacioncerrojo"
        } else {
            appStatusText.text = "✗ App 'Operación Cerrojo' NO INSTALADA"
            appStatusText.setTextColor(getColor(R.color.orange))
            appStatusDetail.text = "No se encontró en el dispositivo"
        }

        // Esperar 5 segundos para que el usuario pueda leer la información
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 5000)
    }
}

