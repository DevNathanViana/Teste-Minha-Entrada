package com.aprendizado.teste_me.ui

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
import com.aprendizado.teste_me.R
import com.aprendizado.teste_me.manager.GerenciadorDeUsuarios
import com.aprendizado.teste_me.model.ResultadoCadastro
import com.aprendizado.teste_me.model.Usuario

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

            when (manager.cadastrar(usuario)) {
                ResultadoCadastro.SUCESSO -> {
                    Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(Intent(this, Login::class.java))
                    finish()
                }

                ResultadoCadastro.CAMPOS_VAZIOS -> {
                    Toast.makeText(
                        this,
                        "Preencha todos os campos",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

                ResultadoCadastro.NOME_INVALIDO -> {
                    Toast.makeText(this, "Nome deve ter no mínimo 2 caracteres", Toast.LENGTH_SHORT)
                        .show()
                }

                ResultadoCadastro.EMAIL_INVALIDO -> {
                    Toast.makeText(this, "Email inválido!", Toast.LENGTH_SHORT).show()
                }

                ResultadoCadastro.SENHA_INVALIDA -> {
                    Toast.makeText(
                        this,
                        "Senha deve ter no mínimo 6 caracteres",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                ResultadoCadastro.EMAIL_DUPLICADO -> {
                    Toast.makeText(this, "Email já cadastrado!", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }

}