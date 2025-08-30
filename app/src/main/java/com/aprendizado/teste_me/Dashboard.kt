package com.aprendizado.teste_me

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Dashboard : AppCompatActivity() {
    lateinit var buttonLogout: Button
    lateinit var nome: TextView
    lateinit var mensagemDashboard: TextView
    lateinit var email: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)

        nome = findViewById(R.id.nomeUsuario)
        mensagemDashboard = findViewById(R.id.titulo)
        email = findViewById(R.id.emailUsuario)
        buttonLogout = findViewById(R.id.buttonLogout)

        val usuario = intent.getParcelableExtra<Usuario>("usuario")
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val primeiroNome =
            usuario?.nome?.split(" ")?.firstOrNull()?.replaceFirstChar { it.uppercase() } ?: ""

        usuario?.let {
            mensagemDashboard.text = "Bem-vindo, $primeiroNome"
            nome.text = "Nome: ${it.nome}"
            email.text = "Email: ${it.email}"
        }

        buttonLogout.setOnClickListener {
            var gestor = GerenciadorDeUsuarios(this)
            gestor.logout(usuario)
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }
}