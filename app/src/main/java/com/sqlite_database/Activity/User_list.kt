package com.sqlite_database.Activity

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.sqlite_database.Adapter.UsersRecyclerAdapter
import com.sqlite_database.Database.DataBaseHelper
import com.sqlite_database.Model.User
import com.sqlite_database.R
import java.text.FieldPosition


class User_list : AppCompatActivity() {

    private val activity = this@User_list
    private lateinit var textViewName: AppCompatTextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        supportActionBar!!.title = ""
        initViews()
        initObjects()



    }

    /**
     * This method is to initialize views
     */
    private fun initViews() {
        textViewName = findViewById<View>(R.id.textViewName) as AppCompatTextView
        recyclerViewUsers = findViewById<View>(R.id.recyclerViewUsers) as RecyclerView
    }

    /**
     * This method is to initialize objects to be used
     */
companion object{
        lateinit var databaseHelper: DataBaseHelper
        private lateinit var usersRecyclerAdapter: UsersRecyclerAdapter
        private lateinit var recyclerViewUsers: RecyclerView
        private lateinit var listUsers: ArrayList<User>


    }

     fun initObjects() {
        listUsers = ArrayList()
        usersRecyclerAdapter = UsersRecyclerAdapter(listUsers,this)

        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerViewUsers.layoutManager = mLayoutManager
        recyclerViewUsers.itemAnimator = DefaultItemAnimator()
        recyclerViewUsers.setHasFixedSize(true)
        recyclerViewUsers.adapter = usersRecyclerAdapter
        databaseHelper = DataBaseHelper(activity)
         databaseHelper.getAllUser()
         listUsers.clear()
        listUsers.addAll(databaseHelper.getAllUser())


        val emailFromIntent = intent.getStringExtra("EMAIL")
        textViewName.text = emailFromIntent


    }



    /**
     * This class is to fetch all user records from SQLite
     */


}