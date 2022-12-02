package com.example.sketchbook

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.net.URI

class CreateAccount : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var profilePic: ImageView

    // campos do form ---------------
    private lateinit var email: EditText
    private lateinit var endereco: EditText
    private lateinit var telefone: EditText
    private lateinit var senha: EditText
    private lateinit var profilePicture: Uri
    // -----------------------------

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val response: Intent? = result.data
            Log.d("PDM", response?.data?.path.toString())

            profilePic.setImageURI(response?.data)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        auth = FirebaseAuth.getInstance()

        profilePic = findViewById(R.id.profileImage)
        email = findViewById(R.id.editTextNewEmail)
        endereco = findViewById(R.id.editTextAddress)
        telefone = findViewById(R.id.editTextPhone)
        senha = findViewById(R.id.editTextNewPassword)
    }

    fun getImage(v: View) {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        resultLauncher.launch(gallery)
    }

    fun createAccount(v: View) {
        auth.createUserWithEmailAndPassword(email.text.toString(), senha.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    setUserAditionalData(user?.uid.toString())
                    Toast.makeText(baseContext, "Conta criada com sucesso!!!",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(baseContext, "Não foi possível criar a conta.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun setUserAditionalData(uid: String) {
        val mydb = FirebaseDatabase.getInstance().reference
        val usuarios = mydb.child("usuarios")

        if (!endereco.text.isNullOrEmpty()) {
            usuarios.child(uid).child("endereço").setValue(endereco.text.toString())
        }
        if (!telefone.text.isNullOrEmpty()) {
            usuarios.child(uid).child("telefone").setValue(telefone.text.toString())
        }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun goToLogin(v: View){
        val intent = Intent(this, MainActivity::class.java).apply{}
        startActivity(intent)
    }
}