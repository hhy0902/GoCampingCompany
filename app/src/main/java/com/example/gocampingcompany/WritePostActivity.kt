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
                            Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
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
        val writeDate = LocalDateTime.now()
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
            "email" to email
        )
        //db.collection("post").document("${System.currentTimeMillis()}${auth.currentUser?.uid}")
        db.collection("post").document("${writeDate}${auth.currentUser?.uid}")
            .set(post)
            .addOnSuccessListener {
                Log.d("succees", "success db")
                Toast.makeText(this, "post 성공", Toast.LENGTH_SHORT).show()
                finish()

            }
            .addOnFailureListener {
                Log.d("faild", "faild db")
            }

    }

    private fun upLoadPhoto() {
        val name = auth.currentUser?.email?.split("@")?.get(0).toString()
//        val time = "${System.currentTimeMillis()}"
        val fileName = "${name}${time}.png"
        imageFileUri?.let {
            storage.reference.child("post/image").child(fileName)
                .putFile(it)
                .addOnSuccessListener {
                    Log.d("upload", "성공")
                    Toast.makeText(this, "upload 성공", Toast.LENGTH_SHORT).show()


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
                    // 권한이 잘 부여되면 갤러리에서 사진 선택 기능
                    Toast.makeText(this,"권한 있습니다.", Toast.LENGTH_SHORT).show()
                    navigatePhotos()
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    // 교육용 팝업 확인 후 권한 팝업 띄우기
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
                .setTitle("권한이 필요합니다.")
                .setMessage("갤러리에서 사진을 불러오기 위해 권한이 필요합니다.")
                .setPositiveButton("동의하기", { _, _ ->
                    requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1000)
                })
                .setNegativeButton("취소하기", { _, _ -> })
                .create()
                .show()

    }
}









































