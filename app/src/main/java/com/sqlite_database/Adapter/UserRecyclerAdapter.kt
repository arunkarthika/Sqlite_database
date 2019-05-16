package com.sqlite_database.Adapter


import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.sqlite_database.Activity.User_list
import com.sqlite_database.Model.User
import com.sqlite_database.R
import com.sqlite_database.Database.DataBaseHelper





class UsersRecyclerAdapter(private val listUsers: ArrayList<User>,private val context1:Context) : RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        // inflating recycler item view
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler, parent, false)

        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.textViewName.text = listUsers[position].name
        holder.textViewEmail.text = listUsers[position].email
        holder.textViewPassword.text = listUsers[position].password
        holder.purpose.text = listUsers[position].purpose




        holder.purpose.setOnClickListener(View.OnClickListener {

            val yourDataBaseHelper = DataBaseHelper(context =context1  )
            yourDataBaseHelper.deleteUser(user = listUsers[position])
           listUsers.removeAt(position)
            notifyItemRemoved(position)



        })


    }

    override fun getItemCount(): Int {
        return listUsers.size
    }


    /**
     * ViewHolder class
     */
    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var textViewName: AppCompatTextView
        var textViewEmail: AppCompatTextView
        var textViewPassword: AppCompatTextView
        var purpose: AppCompatTextView
        var layout: LinearLayout

        init {
            textViewName = view.findViewById<View>(R.id.textViewName) as AppCompatTextView
            textViewEmail = view.findViewById<View>(R.id.textViewEmail) as AppCompatTextView
            textViewPassword = view.findViewById<View>(R.id.textViewPassword) as AppCompatTextView
            purpose = view.findViewById<View>(R.id.prupose_recycler) as AppCompatTextView
            layout = view.findViewById<View>(R.id.layout) as LinearLayout
        }
    }


}