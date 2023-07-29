package com.example.classification

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.classification.gpdao.Patientclass
import com.google.android.material.textfield.TextInputLayout


class Editandview : AppCompatActivity() {

    lateinit var reportt: Patientclass
    lateinit var editedname: TextInputLayout
    lateinit var editedstate: TextInputLayout
    lateinit var editedphone: TextInputLayout
    lateinit var editedage: TextInputLayout
    lateinit var editedcomment: TextInputLayout
    lateinit var savedchanges: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.classification.R.layout.activity_editandview)

        reportt = (intent.getSerializableExtra("editandviewrepo") as? Patientclass)!!
        showdate(reportt)

        savedchanges.setOnClickListener {
            val intent = Intent(this, ListReports::class.java)
            startActivity(intent)
        }






//        savedchanges.setOnClickListener {
//
//            var name = editedname.editText?.text.toString()
//            var state = editedstate.editText?.text.toString()
//            var phone = editedphone.editText?.text.toString()
//            var age = editedage.editText?.text.toString().toInt()
//            var comment = editedcomment.editText?.text.toString()
//
//
//
//            if (!checkvalidation()) return@setOnClickListener
////            updatereport(name, state, editedphone = phone, comment, age)
//            finish()
//        }
    }

    private fun showdate(report: Patientclass) {
        editedname = findViewById(com.example.classification.R.id.editedname)
        editedstate = findViewById(com.example.classification.R.id.editedstate)
        editedphone = findViewById(com.example.classification.R.id.editedphone)
        editedage = findViewById(com.example.classification.R.id.editedage)
        editedcomment = findViewById(com.example.classification.R.id.editedcomment)
        savedchanges = findViewById(com.example.classification.R.id.savechangesbutton)


        editedname.editText?.setText(report.patientname.toString())
        editedstate.editText?.setText(report.patientstate.toString())
        editedphone.editText?.setText(report.patientphone.toString())
        editedage.editText?.setText(report.patientage.toString())
        editedcomment.editText?.setText(report.doctorcomment.toString())

    }

    //
//    private fun updatereport(
//        editedname: String,
//        editedstate: String,
//        editedphone: String,
//        editedcomment: String,
//        editedage: Int
//    ) {
//        reportt = Patientclass(
//            patientage = editedage,
//            patientname = editedname,
//            patientphone = editedphone,
//            doctorcomment = editedcomment,
//            patientstate = editedstate
//        )
        //
//        Mydatabase.getinstance(context = this).gpreportdao().updatepatient(reportt)
//        Toast.makeText(applicationContext,"Report Is Updated Successfully", Toast.LENGTH_SHORT).show()
//        onBackPressed()
//
//    }
//
        fun checkvalidation(): Boolean {
            var isvalid = true
            if (editedname.editText?.text.toString().isBlank()) {
                editedname.error = "Please Enter Valid Name"
                isvalid = false
            } else {
                editedname.error = null
            }
            if (editedstate.editText?.text.toString().isBlank()) {
                editedstate.error = "Please Enter Valid state"
                isvalid = false
            } else {
                editedstate.error = null
            }

            if (editedphone.editText?.text.toString().isBlank()) {
                editedphone.error = "Please Enter Valid phone number"
                isvalid = false
            } else {
                editedphone.error = null
            }


            if (editedage.editText?.text.toString().isBlank()) {
                editedage.error = "Please Enter Valid age"
                isvalid = false
            } else {
                editedage.error = null
            }

            return isvalid
        }

    }

