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
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.net.URI
import java.util.*

class CreateAccount : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var storage: FirebaseStorage
    private lateinit var profilePic: ImageView

    // campos do form ---------------
    private lateinit var email: EditText
    private lateinit var endereco: EditText
    private lateinit var telefone: EditText
    private lateinit var senha: EditText
    private var profilePicture: Uri? = null
    // -----------------------------

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val response: Intent? = result.data
            Log.d("PDM", response?.data?.path.toString())

            profilePic.setImageURI(response?.data)
            profilePicture = response?.data
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        //Inicializando e referenciando firebase storage
        auth = FirebaseAuth.getInstance()
        storage = Firebase.storage

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
        if (profilePicture == null) {
            Toast.makeText(baseContext, "É necessária uma foto de perfil para criar um usuário.",
                Toast.LENGTH_SHORT).show()
            return
        }

        //Cria conta (email/senha) com método do firebase
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

    //Carrega outras informações setadas do usuário
    private fun setUserAditionalData(uid: String) {
        val mydb = FirebaseDatabase.getInstance().reference
        val usuarios = mydb.child("usuarios")

        val storageRef = storage.reference
        val imgFolder = storageRef.child("userPictures")
        val imgName = UUID.randomUUID().toString()
        val imgRef = imgFolder.child(imgName)

        val uploadTask = imgRef.putFile(profilePicture!!)

        uploadTask.addOnFailureListener { task ->
            Log.w("PDM", "deu erro: " + task.message)
            Toast.makeText(baseContext, "Erro:" + task.message,
                Toast.LENGTH_LONG).show()
        }.addOnSuccessListener { taskSnapshot ->
            if (!endereco.text.isNullOrEmpty()) {
                usuarios.child(uid).child("endereço").setValue(endereco.text.toString())
            }
            if (!telefone.text.isNullOrEmpty()) {
                usuarios.child(uid).child("telefone").setValue(telefone.text.toString())
            }

            usuarios.child(uid).child("perfilImg").setValue(imgName)
        }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    //Inicia atividade de login
    fun goToLogin(v: View){
        val intent = Intent(this, MainActivity::class.java).apply{}
        startActivity(intent)
    }
}