package com.example.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_loged_in.*

class LogedInActivity : AppCompatActivity() {
    private lateinit var db:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loged_in)
        val sharedpref=this.getPreferences(Context.MODE_PRIVATE)?:return
        val islogin=sharedpref.getString("email","1")
        logout.setOnClickListener {
            sharedpref.edit().remove("email").apply()
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        if(islogin=="1"){

            val email=intent.getStringExtra("email")
            if(email!=null){
                settext(email)
                with(sharedpref.edit()){
                    putString("email",email)
                    apply()
                }
            }
            else{
                val intent=Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }else{
            settext(islogin)
        }
    }
    private fun settext(email:String?){
        db= FirebaseFirestore.getInstance()
       if(email!=null){
        db.collection("USERS").document(email).get()
            .addOnSuccessListener {
                tasks->
                name_log.text=tasks.get("name").toString()
                phone_log.text=tasks.get("phone").toString()
                email_log.text=tasks.get("email").toString()
            }
    }
    }
}