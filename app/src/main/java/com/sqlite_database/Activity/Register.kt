package com.sqlite_database.Activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatTextView
import android.util.Log
import android.view.View
import com.sqlite_database.Database.DataBaseHelper
import com.sqlite_database.Model.User
import com.sqlite_database.R
import com.sqlite_database.Utils.InputValidation


class Register : AppCompatActivity(), View.OnClickListener {

    private val activity = this@Register

    private lateinit var nestedScrollView: NestedScrollView

    private lateinit var textInputLayoutName: TextInputLayout
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutPassword: TextInputLayout
    private lateinit var textInputLayoutConfirmPassword: TextInputLayout
    private lateinit var purpose: TextInputEditText

    private lateinit var textInputEditTextName: TextInputEditText
    private lateinit var textInputEditTextEmail: TextInputEditText
    private lateinit var textInputEditTextPassword: TextInputEditText
    private lateinit var textInputEditTextConfirmPassword: TextInputEditText

    private lateinit var appCompatButtonRegister: AppCompatButton
    private lateinit var appCompatTextViewLoginLink: AppCompatTextView

    private lateinit var inputValidation: InputValidation
    private lateinit var databaseHelper: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        // hiding the action bar
        supportActionBar!!.hide()

        // initializing the views
        initViews()

        // initializing the listeners
        initListeners()

        // initializing the objects
        initObjects()
    }

    /**
     * This method is to initialize views
     */
    private fun initViews() {
        nestedScrollView = findViewById<View>(R.id.nestedScrollView) as NestedScrollView

        textInputLayoutName = findViewById<View>(R.id.textInputLayoutName) as TextInputLayout
        textInputLayoutEmail = findViewById<View>(R.id.textInputLayoutEmail) as TextInputLayout
        textInputLayoutPassword = findViewById<View>(R.id.textInputLayoutPassword) as TextInputLayout
        textInputLayoutConfirmPassword = findViewById<View>(R.id.textInputLayoutConfirmPassword) as TextInputLayout

        textInputEditTextName = findViewById<View>(R.id.textInputEditTextName) as TextInputEditText
        textInputEditTextEmail = findViewById<View>(R.id.textInputEditTextEmail) as TextInputEditText
        textInputEditTextPassword = findViewById<View>(R.id.textInputEditTextPassword) as TextInputEditText
        textInputEditTextConfirmPassword = findViewById<View>(R.id.textInputEditTextConfirmPassword) as TextInputEditText
        purpose = findViewById<View>(R.id.purpose) as TextInputEditText

        appCompatButtonRegister = findViewById<View>(R.id.appCompatButtonRegister) as AppCompatButton

        appCompatTextViewLoginLink = findViewById<View>(R.id.appCompatTextViewLoginLink) as AppCompatTextView

    }

    /**
     * This method is to initialize listeners
     */
    private fun initListeners() {
        appCompatButtonRegister!!.setOnClickListener(this)
        appCompatTextViewLoginLink!!.setOnClickListener(this)

    }

    /**
     * This method is to initialize objects to be used
     */
    private fun initObjects() {
        inputValidation = InputValidation(activity)
        databaseHelper = DataBaseHelper(activity)


    }


    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    override fun onClick(v: View) {
        when (v.id) {

            R.id.appCompatButtonRegister -> postDataToSQLite()

            R.id.appCompatTextViewLoginLink -> finish()
        }
    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private fun postDataToSQLite() {
        if (!inputValidation!!.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name))) {
            return
        }
        if (!inputValidation!!.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return
        }
        if (!inputValidation!!.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return
        }
        if (!inputValidation!!.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return
        }
        if (!inputValidation!!.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
            return
        }


        if(purpose.text?.length!!.equals(0)){
            Snackbar.make(nestedScrollView!!, "Purpose is empty", Snackbar.LENGTH_LONG).show()
            return
        }

        if (!databaseHelper!!.checkUser(textInputEditTextEmail!!.text.toString().trim())) {

            var user = User(name = textInputEditTextName!!.text.toString().trim(),
                email = textInputEditTextEmail!!.text.toString().trim(),
                password = textInputEditTextPassword!!.text.toString().trim(),
                purpose = purpose!!.text.toString().trim())

            Log.d("user",user.purpose+user.toString())

            databaseHelper!!.addUser(user)

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(nestedScrollView!!, getString(R.string.success_message), Snackbar.LENGTH_LONG).show()
            emptyInputEditText()


        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView!!, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show()
        }


    }

    /**
     * This method is to empty all input edit text
     */
    private fun emptyInputEditText() {
        textInputEditTextName!!.text = null
        textInputEditTextEmail!!.text = null
        textInputEditTextPassword!!.text = null
        textInputEditTextConfirmPassword!!.text = null
        purpose!!.text = null



        val inte=Intent(this,User_list::class.java)
        inte.putExtra("EMAIL", textInputEditTextEmail!!.text.toString().trim { it <= ' ' })
        startActivity(inte)



    }
}