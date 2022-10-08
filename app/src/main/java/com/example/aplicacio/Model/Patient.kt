package com.example.aplicacio.Model

/**
 * Classe relacionada amb les dades del pacient. Aqui trobarem referencies a altres classes del
 * model com el Barthel, l'Emina o el BruiseData. La informació que trobem aqui ja te aplicat tots
 * els getters i els setters i el seu constructor i per això no hi ha la necesitat de crear-los.
 */
data class Patient(
    var DoB: String = "",
    var name: String = "",
    val patologies: String = "",
    val entryNumber: Int = 0,
    val bruiseData: BruiseData = BruiseData(),
    val barthel: Barthel = Barthel(),
    val emina: Emina = Emina(),
    val necroticImage: String = "",
    val grainImage: String = "",
    val infectedImage: String = "",
    val originalImage: String = "",
    val dataEnregistrament: String = ""
)

