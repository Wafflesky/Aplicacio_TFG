package com.example.aplicacio.Model

/**
 * Classe relacionada amb les dades que ha introduit l'usuari sobre l'escala Barthel. Aqui ens guardem
 * els valors que ha introduït i el recompte de punts per a calcular el grau de dependència que te
 * el pacient.
 */

data class Barthel(
    val bath: String = "",
    val bathroom: String = "",
    var bladder: String = "",
    val deambulate: String = "",
    val deposition: String = "",
    val dress: String = "",
    val eat: String = "",
    val move: String = "",
    val stair: String = "",
    val tiding: String = "",
    val barthelResult: String = "")