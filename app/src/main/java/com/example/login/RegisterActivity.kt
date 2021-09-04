package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth= FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()
        continue1.setOnClickListener {
            if(checking()){
                var email=email_register.text.toString()
                var password=password_register.text.toString()
                var name=name.text.toString()
                var phone=phone.text.toString()


                var user= hashMapOf(
                    "name" to name,
                    "phone" to phone,
                    "email" to email
                )
                val Users= db.collection("USERS")
                val query=Users.whereEqualTo("email",email).get()
                    .addOnSuccessListener{
                        task->
                        if(task.isEmpty){
                            auth.createUserWithEmailAndPassword(email,password)
                                .addOnCompleteListener {
                                        task->
                                    if(task.isSuccessful){
                                        Users.document(email).set(user)
                                        val intent=Intent(this,LogedInActivity::class.java)
                                        intent.putExtra("email",email)
                                        startActivity(intent)
                                        finish()
                                    }
                                    else{
                                        Toast.makeText(this,"user is not created",Toast.LENGTH_LONG).show()
                                    }
                                }
                        }
                        else{
                            Toast.makeText(this,"Already register",Toast.LENGTH_LONG).show()
                            val intent=Intent(this,MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
        }

    }}
    private fun checking():Boolean{
        if(name.text.toString().trim{it<=' '}.isNotEmpty()&&
            phone.text.toString().trim{it<=' '}.isNotEmpty()&&
            email_register.text.toString().trim{it<=' '}.isNotEmpty()&&
            password_register.text.toString().trim{it<=' '}.isNotEmpty()) return true

        return false
    }
}