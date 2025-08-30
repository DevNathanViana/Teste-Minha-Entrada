package com.aprendizado.teste_me.manager

import android.content.Context
import android.util.Patterns
import com.aprendizado.teste_me.model.ResultadoCadastro
import com.aprendizado.teste_me.model.Usuario
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GerenciadorDeUsuarios(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences("usuarios", Context.MODE_PRIVATE)
    private val gson = Gson()

    private fun getUsuarios(): MutableList<Usuario> {
        val json = sharedPreferences.getString("usuarios_lista", "[]")
        val type = object : TypeToken<MutableList<Usuario>>() {}.type
        return gson.fromJson(json, type)
    }

    private fun salvarUsuarios(lista: MutableList<Usuario>) {
        val editor = sharedPreferences.edit()
        val json = gson.toJson(lista)
        editor.putString("usuarios_lista", json)
        editor.apply()
    }

    fun validaEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun cadastrar(usuario: Usuario): ResultadoCadastro {
        when {
            usuario.nome.isNullOrEmpty() || usuario.senha.isNullOrEmpty() || usuario.email.isNullOrEmpty() -> return ResultadoCadastro.CAMPOS_VAZIOS
            usuario.senha.length < 6 -> return ResultadoCadastro.SENHA_INVALIDA
            usuario.nome.length < 2 -> return ResultadoCadastro.NOME_INVALIDO
            !validaEmail(usuario.email) -> return ResultadoCadastro.EMAIL_INVALIDO
            getUsuarios().any { it.email == usuario.email } -> return ResultadoCadastro.EMAIL_DUPLICADO
            else -> {
                val lista = getUsuarios()
                lista.add(usuario)
                salvarUsuarios(lista)
                return ResultadoCadastro.SUCESSO
            }
        }
    }

    fun login(email: String, senha: String): Usuario? {
        val lista = getUsuarios()
        val usuarioEncontrado = lista.find { it.email == email && it.senha == senha }

        return if (usuarioEncontrado != null) {
            usuarioEncontrado.logado = true
            salvarUsuarios(lista)
            usuarioEncontrado
        } else {
            null
        }
    }

    fun temUsuarioLogado(): Usuario? {
        val lista = getUsuarios()
        val usuarioLogado = lista.find { it.logado }
        return usuarioLogado
    }

    fun logout(usuario: Usuario?): Unit {
        val lista = getUsuarios()
        val usuarioLogado = lista.find { it.logado }
        if (usuarioLogado != null) {
            usuarioLogado.logado = false
            salvarUsuarios(lista)
        }
    }
}