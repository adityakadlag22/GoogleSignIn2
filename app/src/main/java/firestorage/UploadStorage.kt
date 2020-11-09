package firestorage

import Utils.toast
import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import cycle.gear.googlesignin2.LoginActivity
import cycle.gear.googlesignin2.MainActivity
import cycle.gear.googlesignin2.R
import kotlinx.android.synthetic.main.activity_upload_storage.*


class UploadStorage : AppCompatActivity() {
    private lateinit var chooserFilePath: Uri
    var downImgUrl: String = ""
    private val TAG = "UploadStorage"
    private var myRef = FirebaseDatabase.getInstance().getReference("Users")
    private lateinit var mAuth: FirebaseAuth
    private lateinit var user: FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_storage)
        initAll()
        chooseBtn.setOnClickListener {
            openFileChooser()
        }
        uploadBtn.setOnClickListener {
            uploadImageFile2()
        }
    }

    private fun initAll() {
        //Auth
        mAuth = FirebaseAuth.getInstance()
        checkUser()
        user = mAuth.currentUser!!
    }

    private fun openFileChooser() {
        val i = Intent()
        i.type = "*/*"
        i.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(i, "Pick"), 111)
    }



    private fun uploadImageFile2() {
        val pd = ProgressDialog(this)
        pd.setTitle("Uploading")
        pd.show()
        val id = myRef.child("userhabits").push().key
        val imageRef = FirebaseStorage.getInstance().reference.child("userImages").child(id.toString())
        val uploadtask = imageRef.putFile(chooserFilePath)
        val task = uploadtask.continueWithTask { task ->
            if (!task.isSuccessful) {
                toast("error ${task.exception.toString()}")
            }
            imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val demourl = task.result
                downImgUrl = demourl!!.toString()
                myRef.child(user.uid).child("userdata").child("images").child(id.toString())
                    .setValue(downImgUrl)
                uploadBtn.visibility=View.GONE
                Intent(this,MainActivity::class.java).also {
                    startActivity(it)
                }
                pd.dismiss()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111 && resultCode == Activity.RESULT_OK && data != null) {
            chooserFilePath = data.data!!
            val cR: ContentResolver = this.contentResolver
            val type = cR.getType(chooserFilePath)
            Log.d(TAG, "onActivityResult: Data is $type")
            when (type) {
                "image/jpeg" -> {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, chooserFilePath)
                    storageImgView.visibility = View.VISIBLE
                    storageVidView.visibility = View.GONE
                    storageImgView.setImageBitmap(bitmap)
                    uploadBtn.visibility = View.VISIBLE

                }
                "audio/mpeg" -> {
                    toast("Working on Music")
                }
                "video/mp4" -> {
                    storageImgView.visibility = View.GONE
                    storageVidView.visibility = View.VISIBLE
                    storageVidView.setVideoURI(chooserFilePath)
                    val mControl = MediaController(this)
                    storageVidView.setMediaController(mControl)
                    mControl.setAnchorView(storageVidView)
                    uploadBtn.visibility = View.VISIBLE
                    if (!storageVidView.isPlaying) {
                        storageVidView.start()
                    }
                }
                "application/pdf" -> {
                    toast("Working on pdf")
                }

            }


        }
    }

    private fun checkUser() {
        val user1 = mAuth.currentUser
        Handler().postDelayed({
            if (user1 != null) {
                Log.d(TAG, "checkUser: ${user1.displayName} ")
                user = user1
            } else {
                Toast.makeText(this, "No User", Toast.LENGTH_SHORT).show()
                Intent(this, LoginActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
        }, 2000)
    }

}