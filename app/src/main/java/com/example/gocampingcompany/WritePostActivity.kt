package com.example.gocampingcompany

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.gocampingcompany.databinding.ActivityWritePostBinding
import com.example.gocampingcompany.post.PostModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.time.LocalDateTime

class WritePostActivity : AppCompatActivity() {

    lateinit var startActivityLauncher : ActivityResultLauncher<Intent>
    val storage = Firebase.storage
    private val db = Firebase.firestore
    var imageFileUri : Uri? = null
    private val auth : FirebaseAuth = Firebase.auth

    private val binding by lazy {
        ActivityWritePostBinding.inflate(layoutInflater)
    }

    private val time = System.currentTimeMillis()
    @SuppressLint("NewApi")
    private val writeDateDetail = LocalDateTime.now().toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        startActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                when(it.resultCode) {
                    RESULT_OK -> {
                        val selectedImageUri : Uri? = it.data?.data
                        if (selectedImageUri != null) {
                            binding.imageView11.setImageURI(selectedImageUri)
                            imageFileUri = selectedImageUri
                            Log.d("photo uri =", "${imageFileUri}")
                            //upLoadPhoto()

                        } else {
                            Toast.makeText(this, "????????? ???????????? ???????????????.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        ininAddPhotoButton()
        postButton()
    }

    @SuppressLint("NewApi")
    private fun postButton() {
        binding.addPostButton.setOnClickListener {
            binding.progressBar.visibility = VISIBLE
            upLoadPhoto()
            upLoadPost()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun upLoadPost() {
        val title = binding.postTitleTextView.text.toString()
        val content = binding.postContentTextView.text.toString()
        val id = "${auth.currentUser?.uid}"
        val name = auth.currentUser?.email?.split("@")?.get(0).toString()
//        val time = "${System.currentTimeMillis()}"
        val uri = imageFileUri.toString()
        val year = LocalDateTime.now().year
        val month = LocalDateTime.now().monthValue
        val day = LocalDateTime.now().dayOfMonth
        val writeDate = "${year}${month}${day}"
        //val writeDateDetail = LocalDateTime.now().toString()
        val email = auth.currentUser?.email

        Log.d("uri", "${uri}")

        val post = hashMapOf(
            "title" to title,
            "content" to content,
            "imageUri" to uri,
            "id" to id,
            "name" to name,
            "writeDate" to "${writeDate}",
            "time" to time.toString(),
            "email" to email,
            "writeDateDetail" to writeDateDetail
        )
        //db.collection("post").document("${System.currentTimeMillis()}${auth.currentUser?.uid}")
        db.collection("post").document("${writeDateDetail}${auth.currentUser?.uid}")
            .set(post)
            .addOnSuccessListener {
                Log.d("succees", "success db")
                Toast.makeText(this, "post ??????", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Log.d("faild", "faild db")
            }

    }

    @SuppressLint("NewApi")
    private fun upLoadPhoto() {
        val title = binding.postTitleTextView.text.toString()
        val content = binding.postContentTextView.text.toString()
        val id = "${auth.currentUser?.uid}"
        val name = auth.currentUser?.email?.split("@")?.get(0).toString()
//        val time = "${System.currentTimeMillis()}"
        val uri = imageFileUri.toString()
        val year = LocalDateTime.now().year
        val month = LocalDateTime.now().monthValue
        val day = LocalDateTime.now().dayOfMonth
        val writeDate = "${year}${month}${day}"
        //val writeDateDetail = LocalDateTime.now().toString()

        val day2 = LocalDateTime.now().dayOfYear
        val email = auth.currentUser?.email

        val fileName = "${writeDateDetail}${email}${name}.png"
        Log.d("writeDateDetail","${writeDateDetail}")

        imageFileUri?.let {
            storage.reference.child("post/image").child(fileName)
                .putFile(it)
                .addOnSuccessListener {
                    Log.d("upload", "??????")
                    Toast.makeText(this, "upload ??????", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Log.d("photo upload", "fail")
                    Toast.makeText(this, "photo upload fail", Toast.LENGTH_SHORT).show()
                }
        }

    }

    private fun ininAddPhotoButton() {
        binding.addPhotoButton.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED -> {
                    // ????????? ??? ???????????? ??????????????? ?????? ?????? ??????
                    Toast.makeText(this,"?????? ????????????.", Toast.LENGTH_SHORT).show()
                    navigatePhotos()
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    // ????????? ?????? ?????? ??? ?????? ?????? ?????????
                    showPermissionContextPopup()
                }
                else -> {
                    requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1000)
                }
            }
        }
    }

    private fun navigatePhotos() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityLauncher.launch(intent)
    }

    private fun showPermissionContextPopup() {
            AlertDialog.Builder(this)
                .setTitle("????????? ???????????????.")
                .setMessage("??????????????? ????????? ???????????? ?????? ????????? ???????????????.")
                .setPositiveButton("????????????", { _, _ ->
                    requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1000)
                })
                .setNegativeButton("????????????", { _, _ -> })
                .create()
                .show()

    }
}









































