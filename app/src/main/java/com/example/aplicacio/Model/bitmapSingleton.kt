package com.example.aplicacio.Model

import android.graphics.Bitmap

object bitmapSingleton{

    init {
        println("Singleton class invoked.")
    }

    private lateinit var originalWidth: Number
    private lateinit var originalHeight: Number
    private lateinit var bitmap: Bitmap
    private lateinit var result: Bitmap

    private lateinit var grain: Bitmap
    private lateinit var necrotic: Bitmap
    private lateinit var infected: Bitmap

    private lateinit var selectedGrain: Bitmap
    private lateinit var selectedNecrotic: Bitmap
    private lateinit var selectedInfected: Bitmap

    private lateinit var age: Number
    private lateinit var gender: String
    private lateinit var location: String

    private lateinit var NHC: String
    private lateinit var DoB: String
    private lateinit var name: String
    private lateinit var patologies: String

    private var nhcEntry: Int = 0
    private var nhcEntryCreation: Int = 0

    private lateinit var region: String
    private lateinit var treatment: String
    private lateinit var description: String

    private lateinit var mental: String
    private lateinit var mobility: String
    private lateinit var incontinency: String
    private lateinit var nutrition: String
    private lateinit var activity: String
    private lateinit var emina: Number

    private lateinit var eat: String
    private lateinit var bath: String
    private lateinit var dress: String
    private lateinit var tiding: String
    private lateinit var deposition: String
    private lateinit var bladder: String
    private lateinit var bathroom: String
    private lateinit var move: String
    private lateinit var deambulate: String
    private lateinit var stair: String
    private lateinit var barthel: Number

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

    fun storeMental(mental: String){
        bitmapSingleton.mental = mental
    }

    fun storeMobility(mobility: String){
        bitmapSingleton.mobility = mobility
    }

    fun storeIncontinceny(inc: String){
        incontinency = inc
    }

    fun storeNutrition(nutrition: String){
        bitmapSingleton.nutrition = nutrition
    }

    fun storeActivity(act: String){
        activity = act
    }

    fun storeEat(eat: String){
        bitmapSingleton.eat = eat
    }

    fun storeBath(bath: String){
        bitmapSingleton.bath = bath
    }

    fun storeDress(dress: String){
        bitmapSingleton.dress = dress
    }

    fun storeTiding(tid: String){
        tiding = tid
    }

    fun storeDeposition(dep: String){
        deposition = dep
    }

    fun storeBladder(bladder: String){
        bitmapSingleton.bladder = bladder
    }

    fun storeBathroom(br: String){
        bathroom = br
    }

    fun storeMove(move: String){
        bitmapSingleton.move = move
    }

    fun storeDeambulate(deambulate: String){
        bitmapSingleton.deambulate = deambulate
    }
    fun storeStair(stair: String){
        bitmapSingleton.stair = stair
    }

    fun storeEmina(emina: Number){
        bitmapSingleton.emina = emina
    }

    fun storeBarthel(barthel: Number){
        bitmapSingleton.barthel = barthel
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

    //TODO: All gets del formulari

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

    fun getRegion(): String{

        return region

    }

    fun getTreatment(): String{

        return treatment

    }

    fun getDesc(): String{

        return description

    }

    fun getStatus(): String{

        return mental

    }

    fun getMobility(): String{

        return mobility

    }

    fun getIncontinency(): String{

        return incontinency

    }

    fun getNutrition(): String{

        return nutrition

    }

    fun getActivity(): String{

        return activity

    }

    fun getEat(): String{

        return eat

    }

    fun getBath(): String{

        return bath

    }

    fun getDress(): String{

        return dress

    }

    fun getTiding(): String{

        return tiding

    }

    fun getDeposition(): String{

        return deposition

    }

    fun getBladder(): String{

        return bladder

    }

    fun getBathroom(): String{

        return bathroom

    }

    fun getMove(): String{

        return move

    }

    fun getDeambulate(): String{

        return deambulate

    }

    fun getStair(): String{

        return stair

    }

    fun getEmina(): Number{

        return emina

    }

    fun getBarthel(): Number{

        return barthel

    }



}
