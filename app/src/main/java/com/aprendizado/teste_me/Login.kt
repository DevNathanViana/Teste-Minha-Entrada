package com.aprendizado.teste_me

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Login : AppCompatActivity() {

    lateinit var buttonEntrar: Button
    lateinit var useremail: EditText
    lateinit var usersenha: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val cliqueAqui = findViewById<TextView>(R.id.cliqueAqui)

        cliqueAqui.setOnClickListener {
            val intent = Intent(this, Cadastro::class.java)
            startActivity(intent)
            finish()
        }

        buttonEntrar = findViewById(R.id.button)
        useremail = findViewById(R.id.emailValue)
        usersenha = findViewById(R.id.senhaValue)

        buttonEntrar.setOnClickListener {
            var email = useremail.text.toString()
            var senha = usersenha.text.toString()

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val gerenciador = GerenciadorDeUsuarios(this)
            val usuarioLogado = gerenciador.login(email, senha)

            if (usuarioLogado != null) {
                val intent = Intent(this, Dashboard::class.java)
                intent.putExtra("usuario", usuarioLogado)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "E-mail ou senha incorretos", Toast.LENGTH_SHORT).show()
            }

        }
    }

}