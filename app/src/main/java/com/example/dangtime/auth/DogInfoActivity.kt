package com.example.dangtime.auth

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.dangtime.R
import com.example.dangtime.chat.FriendVO
import com.example.dangtime.post.HomeActivity
import com.example.dangtime.util.FBAuth
import com.example.dangtime.util.FBdatabase
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class DogInfoActivity : AppCompatActivity() {

    private var imageUri: Uri? = null
    lateinit var imgRegistration: CircleImageView

    private var mCurrentPhotoPath: String? = null
    val REQUEST_TAKE_PHOTO = 10
    val REQUEST_PERMISSION = 11
    lateinit var file: File
    lateinit var trimLocation: String

    //이미지 등록
    private val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                imageUri = result.data?.data //이미지 경로 원본
                imgRegistration.setImageURI(imageUri) //이미지 뷰를 바꿈
                Log.d("이미지", "성공")
            } else {
                Log.d("이미지", "실패")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dog_info)

        val imgDogBack = findViewById<ImageView>(R.id.imgDogBack)
        val etRegisterDogName = findViewById<EditText>(R.id.etRegisterDogName)
        val etRegisterDogNick = findViewById<EditText>(R.id.etRegisterDogNick)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvRegisterAdd = findViewById<TextView>(R.id.tvRegisterAdd)

        val address = intent.getStringExtra("address")
        val splitLocation = address!!.split(" ").asReversed()

        if (splitLocation.contains("(") && splitLocation.contains(")")) {
            trimLocation = splitLocation[0].substring(
                1,
                splitLocation[0].length - 1
            )
        } else {
            trimLocation = splitLocation[0].substring(
                0,
                splitLocation[0].length - 1
            )
        }

        Log.d("맵 스플릿", trimLocation)

        tvRegisterAdd.setText(address)

        imgRegistration = findViewById(R.id.imgRegistration)
        var profileCheck = false

//        사진추가
        val photoLl = findViewById<LinearLayout>(R.id.photoLl)
        photoLl.visibility = View.GONE
        var photo = false
        imgRegistration.setOnClickListener {
            if (photo) {
                photo = false
                photoLl.visibility = View.GONE
            } else {
                photo = true
                photoLl.visibility = View.VISIBLE
            }
        }
        val btnGallary = findViewById<Button>(R.id.btnGallary)
        btnGallary.setOnClickListener {
            val intentImage = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI
            )
            getContent.launch(intentImage)
            profileCheck = true

            photo = false
            photoLl.visibility = View.GONE
        }

        checkPermission()
        val btnCamera = findViewById<Button>(R.id.btnCamera)

        btnCamera.setOnClickListener {
            captureCamera()

            photo = false
            photoLl.visibility = View.GONE
        }

        tvRegisterAdd.setText(address)

        btnRegister.setOnClickListener {
            val email = intent.getStringExtra("email")!!
            val intent = Intent(this@DogInfoActivity, HomeActivity::class.java)

            val dogName = etRegisterDogName.text.toString()
            val dogNick = etRegisterDogNick.text.toString()
            val uid = FBAuth.getUid()

            if (!profileCheck) {
                Toast.makeText(this, "프로필사진을 등록해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                val name = "$dogNick $dogName"

                FirebaseStorage.getInstance()
                    .reference.child("userImages").child("$uid/photo").putFile(imageUri!!)
                    .addOnSuccessListener {
                        var userProfile: Uri? = null
                        FirebaseStorage.getInstance().reference.child("userImages")
                            .child("$uid/photo").downloadUrl
                            .addOnSuccessListener {
                                userProfile = it
                                val friend = FriendVO(
                                    email,
                                    name,
                                    userProfile.toString(),
                                    uid
                                )
                                Log.d("dogino4", friend.toString())
                                FBdatabase.getUserInfo().child(uid).setValue(friend)
                            }
                    }
                var key = FBdatabase.getMemberRef().child(uid).key.toString()
                FBdatabase.getMemberRef().child(key)
                    .setValue(MemberVO(uid, trimLocation!!, dogName!!, dogNick!!))

                startActivity(intent)
            }
        }

        imgDogBack.setOnClickListener {
            finish()
        }
    }

    //권한 확인
    fun checkPermission() {
        val permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val permissionRead =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        val permissionWrite =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        //권한이 없으면 권한 요청
        if (permissionCamera != PackageManager.PERMISSION_GRANTED || permissionRead != PackageManager.PERMISSION_GRANTED || permissionWrite != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.CAMERA
                )
            ) {
                Toast.makeText(this, "이 앱을 실행하기 위해 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), REQUEST_PERMISSION
            )
        }
    }

    @Throws(IOException::class)
    fun createImageFile(): File? { // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_$timeStamp.jpg"
        var imageFile: File? = null
        val storageDir = File(
            Environment.getExternalStorageDirectory().toString() + "/Pictures",
            "Wimmy"
        )
        if (!storageDir.exists()) {
            Log.i("mCurrentPhotoPath1", storageDir.toString())
            storageDir.mkdirs()
        }
        imageFile = File(storageDir, imageFileName)
        mCurrentPhotoPath = imageFile.absolutePath
        return imageFile
    }

    private fun captureCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {

            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
                Log.e("captureCamera Error", ex.toString())
                return
            }
            if (photoFile != null) { // getUriForFile의 두 번째 인자는 Manifest provier의 authorites와 일치해야 함
                val providerURI = FileProvider.getUriForFile(this, packageName, photoFile)
                imageUri = providerURI;
                // 인텐트에 전달할 때는 FileProvier의 Return값인 content://로만!!, providerURI의 값에 카메라 데이터를 넣어 보냄
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI)
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_TAKE_PHOTO -> {
                if (resultCode == RESULT_OK) {

                    try {
                        galleryAddPic();


                    } catch (e: Exception) {
                        Log.e("REQUEST_TAKE_PHOTO", e.toString());
                    }

                } else {
                    Toast.makeText(this@DogInfoActivity, "사진찍기를 취소하였습니다.", Toast.LENGTH_SHORT)
                        .show();
                }

            }
        }
    }

    private fun galleryAddPic() {
        val mediaScanIntent: Intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        // 해당 경로에 있는 파일을 객체화(새로 파일을 만든다는 것으로 이해하면 안 됨)
        val f: File = File(mCurrentPhotoPath);
        val contentUri: Uri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
        imgRegistration.setImageURI(imageUri);
        Toast.makeText(this, "사진이 앨범에 저장되었습니다.", Toast.LENGTH_SHORT).show();
    }

}