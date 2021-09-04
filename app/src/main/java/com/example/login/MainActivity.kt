package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth :FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth= FirebaseAuth.getInstance()

        register.setOnClickListener {
            val intent=Intent(this,RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
        login.setOnClickListener {
            if(checking()){
                var email=email.text.toString()
                var password=password.text.toString()

                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener {
                        task->
                        if(task.isSuccessful){
                            val intent=Intent(this,LogedInActivity::class.java)
                            intent.putExtra("email",email)
                            startActivity(intent)
                            finish()
//                            Toast.makeText(this,"Login Successful",Toast.LENGTH_LONG).show()
                        }
                        else{
                            Toast.makeText(this,"Login failed",Toast.LENGTH_LONG).show()
                        }
                    }
            }
            else{
                Toast.makeText(this,"Please provide emailaddress & password",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checking(): Boolean {
      if(email.text.toString().trim{it<=' '}.isNotEmpty()
          && password.text.toString().trim{it<=' '}.isNotEmpty()) return true
     else return false
    }
}