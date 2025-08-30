package com.aprendizado.teste_me

import android.content.Context
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

    fun cadastrar(usuario: Usuario): Boolean {
        val lista = getUsuarios()

        if (lista.any { it.email == usuario.email }) return false

        lista.add(usuario)
        salvarUsuarios(lista)
        return true
    }

    fun login(email: String, senha: String): Usuario? {
        val lista = getUsuarios()
        val usuarioEncontrado = lista.find { it.email == email && it.senha == senha }

        return if (usuarioEncontrado != null) {
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
