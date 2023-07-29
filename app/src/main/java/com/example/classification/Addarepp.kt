package com.example.classification

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class Addarepp : AppCompatActivity() {

    lateinit var preponame : TextInputLayout
    lateinit var prepostate : TextInputLayout
    lateinit var prepoage: TextInputLayout
    lateinit var prepohone : TextInputLayout
    lateinit var doctorcomment : TextInputLayout
    lateinit var addrepo: Button
    lateinit var DB:DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_addarepp)

        preponame= findViewById(R.id.preponame)
        prepostate= findViewById(R.id.prepostate)
        prepohone= findViewById(R.id.prepophone)
        prepoage= findViewById(R.id.prepoage)
        doctorcomment= findViewById(R.id.doctorcomment)
        addrepo= findViewById(R.id.addrepo)
        DB = DBHelper(this)

        addrepo.setOnClickListener {
            if(checkvalidation()) {

                var name = preponame.editText?.text.toString()
                var state = prepostate.editText?.text.toString()
                var age = prepoage.editText?.text.toString().toInt()
                var dcomment = doctorcomment.editText?.text.toString()
                var pphone = prepohone.editText?.text.toString()
//                insertrepo(name, state, pphone, dcomment, age)
                    val insert: Boolean = DB.insertpatient(name,pphone,state,dcomment,age)
                if (insert == true)
                {
                    Toast.makeText(this, "Inserted successfully", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this, MainFunctions::class.java)
                    startActivity(intent)
                }
                else
                {
                    Toast.makeText(this, "Insert Process failed", Toast.LENGTH_SHORT).show()
                }
            }
            onAddclick?.onaddclick()
            }
        }

    private fun checkvalidation(): Boolean {
        var isvalid=true
        if(preponame.editText?.text.toString().isBlank())
        {
            preponame.error="Please Enter Valid Name"
            isvalid=false
        }
        else
        {
            preponame.error= null
        }
        if(prepostate.editText?.text.toString().isBlank())
        {
            prepostate.error="Please Enter Valid State"
            isvalid=false
        }
        else
        {
            prepostate.error= null
        }
        return isvalid
    }

    var onAddclick:OnAddclick?= null
    interface OnAddclick{
        fun onaddclick()
    }

}