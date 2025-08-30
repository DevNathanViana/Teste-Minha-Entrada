package com.aprendizado.teste_me.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Usuario(
    val nome: String,
    val email: String,
    val senha: String,
    var logado: Boolean = false
) : Parcelable