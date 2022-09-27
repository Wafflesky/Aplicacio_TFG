package com.example.aplicacio.Model

/**
 * Classe relacionada amb les dades que ha introduit l'usuari sobre l'escala Emina. Aqui ens guardem
 * els valors que ha introduït i el recompte de punts per a calcular el grau de risc d'acord
 * amb els valors establerts per a l'escala
 */
data class Emina(
    val activity: String = "",
    val incontinency: String = "",
    var mentalStatus: String = "",
    val mobility: String = "",
    val nutrition: String = "",
    val eminaResult: String = "")