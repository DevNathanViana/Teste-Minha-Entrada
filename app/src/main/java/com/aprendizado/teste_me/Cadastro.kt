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

class Cadastro : AppCompatActivity() {

    lateinit var buttonEntrar: Button
    lateinit var username: EditText
    lateinit var useremail: EditText
    lateinit var usersenha: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cadastro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val cliqueAqui = findViewById<TextView>(R.id.cliqueAqui)

        cliqueAqui.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

        buttonEntrar = findViewById(R.id.buttonEntrar)
        username = findViewById(R.id.nomeValue)
        useremail = findViewById(R.id.emailValue)
        usersenha = findViewById(R.id.senhaValue)

        buttonEntrar.setOnClickListener {
            val usuario = Usuario(
                nome = username.text.toString(),
                email = useremail.text.toString(),
                senha = usersenha.text.toString(),
                logado = false
            )

            val manager = GerenciadorDeUsuarios(this)

            if (usuario.senha.length < 6) {
                Toast.makeText(this, "Senha deve ter no mínimo 6 caracteres", Toast.LENGTH_SHORT)
                    .show()
            } else if (manager.cadastrar(usuario)) {
                Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Email já cadastrado!", Toast.LENGTH_SHORT).show()
            }
        }

    }
}