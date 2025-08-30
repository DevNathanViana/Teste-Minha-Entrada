package com.aprendizado.teste_me.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.aprendizado.teste_me.manager.GerenciadorDeUsuarios
import com.aprendizado.teste_me.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Handler(Looper.getMainLooper()).postDelayed({
            var gerenciador = GerenciadorDeUsuarios(this)
            var usuarioLogado = gerenciador.temUsuarioLogado()
            if (usuarioLogado != null) {
                val intent = Intent(this, Dashboard::class.java)
                intent.putExtra("usuario", usuarioLogado)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000)
    }


    override fun onResume()
    {
        super.onResume()
    }
}