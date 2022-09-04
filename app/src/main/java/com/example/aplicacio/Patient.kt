package com.example.aplicacio

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
    val infectedImage: String = ""
)

