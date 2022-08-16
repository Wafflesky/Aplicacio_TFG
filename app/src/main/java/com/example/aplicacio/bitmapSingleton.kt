package com.example.aplicacio

import android.graphics.Bitmap
import java.util.*

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

    private lateinit var NHC: Number
    private lateinit var DoB: String
    private lateinit var name: String
    private lateinit var patologies: String

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
        this.bitmap = bitmap
    }

    fun storeCanvasBitmap(result: Bitmap){
        this.result = result
    }

    fun storeGrainBitmap(grain: Bitmap){
        this.grain = grain
    }

    fun storeNecroticBitmap(necrotic: Bitmap){
        this.necrotic = necrotic
    }

    fun storeInfectedBitmap(infected: Bitmap){
        this.infected = infected
    }

    fun storeSelectedGrainBitmap(selectedGrain: Bitmap){
        this.selectedGrain = selectedGrain
    }

    fun storeSelectedNecroticBitmap(seletedNecrotic: Bitmap){
        this.selectedNecrotic = seletedNecrotic
    }

    fun storeSelectedInfectedBitmap(selectedInfected: Bitmap){
        this.selectedInfected = selectedInfected
    }

    fun storeAge(age: Number){
        this.age = age
    }

    fun storeGender(gender: String){
        this.gender = gender
    }

    fun storeWidth(originalWidth: Number){
        this.originalWidth = originalWidth
    }

    fun storeHeight(originalHeight: Number){
        this.originalHeight = originalHeight
    }

    fun storeLocation(location: String){

        this.location = location

    }

    fun storeNHC(NHC: Number){
        this.NHC = NHC
    }

    fun storeDoB(DoB: String){
        this.DoB = DoB
    }

    fun storeName(name: String){
        this.name = name
    }

    fun storePatologies(patologies: String){
        this.patologies = patologies
    }

    fun storeRegion(region: String){
        this.region = region
    }

    fun storeTreatment(treatment: String){
        this.treatment = treatment
    }

    fun storeDesc(desc: String){
        this.description = desc
    }

    fun storeMental(mental: String){
        this.mental = mental
    }

    fun storeMobility(mobility: String){
        this.mobility = mobility
    }

    fun storeIncontinceny(inc: String){
        this.incontinency = inc
    }

    fun storeNutrition(nutrition: String){
        this.nutrition = nutrition
    }

    fun storeActivity(act: String){
        this.activity = act
    }

    fun storeEat(eat: String){
        this.eat = eat
    }

    fun storeBath(bath: String){
        this.bath = bath
    }

    fun storeDress(dress: String){
        this.dress = dress
    }

    fun storeTiding(tid: String){
        this.tiding = tid
    }

    fun storeDeposition(dep: String){
        this.deposition = dep
    }

    fun storeBladder(bladder: String){
        this.bladder = bladder
    }

    fun storeBathroom(br: String){
        this.bathroom = br
    }

    fun storeMove(move: String){
        this.move = move
    }

    fun storeDeambulate(deambulate: String){
        this.deambulate = deambulate
    }
    fun storeStair(stair: String){
        this.stair = stair
    }

    fun storeEmina(emina: Number){
        this.emina = emina
    }

    fun storeBarthel(barthel: Number){
        this.barthel = barthel
    }

    @JvmName("getBitmap1")
    fun getBitmap(): Bitmap {
        return bitmap
    }

    fun getCanvasBitmap(): Bitmap{
        return result
    }

    fun getGrainBitmap(): Bitmap{
        return this.grain
    }

    fun getNecroticBitmap(): Bitmap{
        return this.necrotic
    }

    fun getInfectedBitmap(): Bitmap{
        return this.infected
    }

    fun getSelectedGrainBitmap(): Bitmap{
        return this.selectedGrain
    }

    fun getSelectedNecroticBitmap(): Bitmap{
        return this.selectedNecrotic
    }

    fun getSelectedInfectedBitmap(): Bitmap{
        return this.selectedInfected
    }

    fun getAge(): Number{
        return this.age
    }

    fun getGender(): String{

        return this.gender

    }

    fun getLocation(): String{

        return this.location

    }

    fun getWidth(): Number{

        return this.originalWidth

    }
    fun getHeight(): Number{

        return this.originalHeight

    }

    //TODO: All gets del formulari

    fun getDoB(): String{

        return this.DoB

    }

    fun getNHC(): Number{

        return this.NHC

    }

    fun getName(): String{

        return this.name

    }

    fun getPatologies(): String{

        return this.patologies

    }

    fun getRegion(): String{

        return this.region

    }

    fun getTreatment(): String{

        return this.treatment

    }

    fun getDesc(): String{

        return this.description

    }

    fun getStatus(): String{

        return this.mental

    }

    fun getMobility(): String{

        return this.mobility

    }

    fun getIncontinency(): String{

        return this.incontinency

    }

    fun getNutrition(): String{

        return this.nutrition

    }

    fun getActivity(): String{

        return this.activity

    }

    fun getEat(): String{

        return this.eat

    }

    fun getBath(): String{

        return this.bath

    }

    fun getDress(): String{

        return this.dress

    }

    fun getTiding(): String{

        return this.tiding

    }

    fun getDeposition(): String{

        return this.deposition

    }

    fun getBladder(): String{

        return this.bladder

    }

    fun getBathroom(): String{

        return this.bathroom

    }

    fun getMove(): String{

        return this.move

    }

    fun getDeambulate(): String{

        return this.deambulate

    }

    fun getStair(): String{

        return this.stair

    }

    fun getEmina(): Number{

        return this.emina

    }

    fun getBarthel(): Number{

        return this.barthel

    }



}
