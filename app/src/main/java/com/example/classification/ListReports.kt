package com.example.classification

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.classification.gpdao.Patientclass

class ListReports : AppCompatActivity() {
//    lateinit var listrecyclarview: RecyclerView
//    lateinit var adapter: ReportAdapter
//    var reports: ArrayList<Patientclass> = ArrayList();
    lateinit var list: ArrayList<Patientclass?>
    lateinit var DB: DBHelper
    lateinit var listapater: ReportAdapter
    lateinit var listRV: RecyclerView
    var errorr= "errror"

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_list_reports)
        listRV = findViewById(com.example.classification.R.id.listrecyclerview)
        list = ArrayList()
        DB = DBHelper(this)
        val res: Cursor = DB.getAllData()

        if(res.getCount() == 0){
            Log.e("cursor is empty",errorr)
        }
        while (res.moveToNext()){
            list.add(Patientclass(res.getInt(res.getColumnIndex("patientage")), //age1
                res.getString(res.getColumnIndex("patientname")), // paientstate //name2
                res.getString(res.getColumnIndex("patientphone")), // paientstate //name2  //paientphone //phone3
                res.getString(res.getColumnIndex("patientstate")), // paientstate //name2 //patientage //state4
                res.getString(res.getColumnIndex("doctorcomm")) // paientstate //name2
                //docname //comm5
            ));
        }
//        list = DB.getAllData()
        listapater = ReportAdapter(list as ArrayList<Patientclass>?, this)

        val linearLayoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        listRV.setLayoutManager(linearLayoutManager)

        // setting our adapter to recycler view.
        // setting our adapter to recycler view.
        listRV.setAdapter(listapater)
        listapater.onedititemcliclklistener = object : ReportAdapter.Onedititemcliclklistener {
            override fun oneditclick(repo: Patientclass) {
                val intent = Intent(this@ListReports,Editandview::class.java)
                intent.putExtra("editandviewrepo",repo)
                startActivity(intent)
            }
        }
    }


    //    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_list_reports, container, false)
//
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        listrecyclarview = view.findViewById(R.id.listrecyclerview)
//        adapter= ReportAdapter(list = reports)
//        listrecyclarview.adapter=adapter
//        adapter.onedititemcliclklistener= object : ReportAdapter.Onedititemcliclklistener{
//            override fun oneditclick(repo: Patientclass) {
//                val intent = Intent(applicationContext,Editandview::class.java)
//                intent.putExtra("editandviewrepo",repo)
//                startActivity(intent)
//            }
//        }
//    }
    //}
//    fun refreshlists() {
//        adapter.list =
//            Mydatabase.getinstance(applicationContext).gpreportdao().getallreports().toMutableList()
//        adapter.notifyDataSetChanged()
//    }
}



