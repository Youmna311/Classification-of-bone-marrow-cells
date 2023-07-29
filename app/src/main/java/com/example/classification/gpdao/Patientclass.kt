package com.example.classification.gpdao

import android.content.Context
import androidx.room.Entity
import java.io.Serializable
import java.util.*


data class Patientclass (
    val patientage : Int? = null,
    val patientname : String? = null,
    val patientphone : String? = null,
    val patientstate : String? = null,
    val doctorcomment : String? = null
):Serializable





//@Entity
//data class Doctorclass (
//    @PrimaryKey(autoGenerate = true)
//    val doctorid : Int? = null,
//    val doctorusername : String? = null,
//    val doctorpassword : String? = null,
//) : Serializable
//
//data class Doctorwithpathients(
//    @Embedded val doctt : Doctorclass,
//    @Relation(
//        parentColumn = "doctorid",
//        entityColumn = "doctor_patientid"
//    )
//    val pathients : MutableList<Patientclass>
//
//)



