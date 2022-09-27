package com.example.aplicacio.Model

import android.graphics.Bitmap

/**
 * Objecte que utilitzem per a guardar la informació durant l'execució. Majortiràriament utilitzada
 * per a poder enviar els bitmaps entre les pantalles degut a errors provocats per les funcions
 * putExtra i getExtra de la classe Intent. Aqui es guarden tots els valors que trobem en el model
 * a més de les imatges, les seves seleccions, les seves mascares, etc...
 * Aqui trobarem els setters i els getters de cada variable.
 */
object bitmapSingleton{

    init {
        println("Singleton class invoked.")
    }

    private lateinit var originalWidth: Number
    private lateinit var originalHeight: Number
    private lateinit var bitmap: Bitmap

    private lateinit var grain: Bitmap
    private lateinit var necrotic: Bitmap
    private lateinit var infected: Bitmap

    private lateinit var selectedGrain: Bitmap
    private lateinit var selectedNecrotic: Bitmap
    private lateinit var selectedInfected: Bitmap

    private lateinit var NHC: String
    private lateinit var DoB: String
    private lateinit var name: String
    private lateinit var patologies: String

    private var nhcEntry: Int = 0
    private var nhcEntryCreation: Int = 0

    private lateinit var region: String
    private lateinit var treatment: String
    private lateinit var description: String

    private lateinit var eminaResult: Number

    private lateinit var emina: Emina
    private lateinit var barthel: Barthel

    private lateinit var barthelResult: Number

    fun storeBitmap(bitmap: Bitmap){
        bitmapSingleton.bitmap = bitmap
    }

    fun storeGrainBitmap(grain: Bitmap){
        bitmapSingleton.grain = grain
    }

    fun storeNecroticBitmap(necrotic: Bitmap){
        bitmapSingleton.necrotic = necrotic
    }

    fun storeInfectedBitmap(infected: Bitmap){
        bitmapSingleton.infected = infected
    }

    fun storeSelectedGrainBitmap(selectedGrain: Bitmap){
        bitmapSingleton.selectedGrain = selectedGrain
    }

    fun storeSelectedNecroticBitmap(seletedNecrotic: Bitmap){
        selectedNecrotic = seletedNecrotic
    }

    fun storeSelectedInfectedBitmap(selectedInfected: Bitmap){
        bitmapSingleton.selectedInfected = selectedInfected
    }

    fun storeWidth(originalWidth: Number){
        bitmapSingleton.originalWidth = originalWidth
    }

    fun storeHeight(originalHeight: Number){
        bitmapSingleton.originalHeight = originalHeight
    }

    fun storeNHC(NHC: String){
        bitmapSingleton.NHC = NHC
    }

    fun storeDoB(DoB: String){
        bitmapSingleton.DoB = DoB
    }

    fun storeName(name: String){
        bitmapSingleton.name = name
    }

    fun storePatologies(patologies: String){
        bitmapSingleton.patologies = patologies
    }

    fun storeNHCEntry(nhcEntry: Int){
        bitmapSingleton.nhcEntry = nhcEntry
    }

    fun storeNHCEntryCreation(nhcEntryCreation: Int){
        bitmapSingleton.nhcEntryCreation = nhcEntryCreation
    }

    fun storeRegion(region: String){
        bitmapSingleton.region = region
    }

    fun storeTreatment(treatment: String){
        bitmapSingleton.treatment = treatment
    }

    fun storeDesc(desc: String){
        description = desc
    }

    fun storeEmina(emina: Emina, eminaResult: Number){
        this.emina = emina
        this.eminaResult = eminaResult
    }

    fun storeBarthel(barthel :Barthel, barthelResult: Number){
        this.barthel = barthel
        this.barthelResult = barthelResult

    }

    @JvmName("getBitmap1")
    fun getBitmap(): Bitmap {
        return bitmap
    }

    fun getGrainBitmap(): Bitmap{
        return grain
    }

    fun getNecroticBitmap(): Bitmap{
        return necrotic
    }

    fun getInfectedBitmap(): Bitmap{
        return infected
    }

    fun getSelectedGrainBitmap(): Bitmap{
        return selectedGrain
    }

    fun getSelectedNecroticBitmap(): Bitmap{
        return selectedNecrotic
    }

    fun getSelectedInfectedBitmap(): Bitmap{
        return selectedInfected
    }

    fun getWidth(): Number{

        return originalWidth

    }
    fun getHeight(): Number{

        return originalHeight

    }

    fun getDoB(): String{

        return DoB

    }

    fun getNHC(): String{

        return NHC

    }

    fun getName(): String{

        return name

    }

    fun getPatologies(): String{

        return patologies

    }

    fun getNHCEntries(): Int{
        return nhcEntry
    }

    fun getNHCEntriesCreation(): Int{
        return nhcEntryCreation
    }

    fun getEmina(): Emina{
        return emina
    }

    fun getBarthel(): Barthel{
        return barthel
    }

    fun getEminaResult(): Number{
        return eminaResult
    }

    fun getBarthelResult(): Number {
        return barthelResult
    }


    fun getRegion(): String{

        return region

    }

    fun getTreatment(): String{

        return treatment

    }

    fun getDesc(): String{

        return description

    }


}
