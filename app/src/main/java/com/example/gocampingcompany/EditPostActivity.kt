package com.example.gocampingcompany

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.gocampingcompany.databinding.ActivityEditPostBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.time.LocalDateTime

class EditPostActivity : AppCompatActivity() {

    lateinit var startActivityLauncher : ActivityResultLauncher<Intent>
    val storage = Firebase.storage
    private val db = Firebase.firestore
    var imageFileUri : Uri? = null
    private val auth : FirebaseAuth = Firebase.auth
    val storageRef = storage.reference

    private val time = System.currentTimeMillis()

    private val binding by lazy {
        ActivityEditPostBinding.inflate(layoutInflater)
    }

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val name = intent.getStringExtra("name")
        val writeDate = intent.getStringExtra("writeDate")
        val email = intent.getStringExtra("email")
        val writeDateDetail = intent.getStringExtra("writeDateDetail")

        binding.titleTextView.setText("${title}")
        binding.contentTextView.setText("${content}")

        storageRef.child("post/image").child("${writeDateDetail}${email}${name}.png").downloadUrl
            .addOnSuccessListener {
                Glide.with(binding.imageView.context)
                    .load(it)
                    .into(binding.imageView)
            }
            .addOnFailureListener {
                Log.d("no image","no image")
            }


        startActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                when(it.resultCode) {
                    RESULT_OK -> {
                        val selectedImageUri : Uri? = it.data?.data
                        if (selectedImageUri != null) {
                            binding.imageView.setImageURI(selectedImageUri)
                            imageFileUri = selectedImageUri
                            Log.d("photo uri =", "${imageFileUri}")

                        } else {
                            Toast.makeText(this, "????????? ???????????? ???????????????.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        ininAddPhotoButton()
        binding.editPostButton.setOnClickListener {
            upLoadPhoto()
            upLoadPost()
        }

    }

//    @SuppressLint("NewApi")
//    private fun postButton() {
//        binding.editPostButton.setOnClickListener {
//            upLoadPhoto()
//            upLoadPost()
//        }
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun upLoadPost() {
        val id = "${auth.currentUser?.uid}"
        val uri = imageFileUri.toString()
        val title = binding.titleTextView.text.toString()
        val content = binding.contentTextView.text.toString()
        val name = intent.getStringExtra("name")
        val writeDate = intent.getStringExtra("writeDate")
        val email = intent.getStringExtra("email")
        val writeDateDetail = intent.getStringExtra("writeDateDetail")

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
        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val name = intent.getStringExtra("name")
        val writeDate = intent.getStringExtra("writeDate")
        val email = intent.getStringExtra("email")
        val writeDateDetail = intent.getStringExtra("writeDateDetail")

        val fileName = "${writeDateDetail}${email}${name}.png"

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
        binding.editPostImageButton.setOnClickListener {
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





































