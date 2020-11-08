package firestorage

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import cycle.gear.googlesignin2.R
import kotlinx.android.synthetic.main.activity_upload_storage.*


class UploadStorage : AppCompatActivity() {
    private lateinit var chooserFilePath: Uri
    private val TAG = "UploadStorage"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_storage)

        chooseBtn.setOnClickListener {
            openFileChooser()
        }
    }

    private fun openFileChooser() {
        val i = Intent()
        i.type = "*/*"
        i.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(i, "Pick"), 111)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111 && resultCode == Activity.RESULT_OK && data != null) {
            chooserFilePath = data.data!!
            val cR: ContentResolver = this.contentResolver
            val type = cR.getType(chooserFilePath)
            Log.d(TAG, "onActivityResult: Data is $type")
            when(type)
            {
                "image/jpeg" ->{
                    val bitmap=MediaStore.Images.Media.getBitmap(contentResolver,chooserFilePath)
                    storageImgView.visibility= View.VISIBLE
                    storageVidView.visibility=View.GONE
                    storageImgView.setImageBitmap(bitmap)

                }
                "audio/mpeg"-> {


                }
                "video/mp4"-> {
                    storageImgView.visibility= View.GONE
                    storageVidView.visibility=View.VISIBLE
                    storageVidView.setVideoURI(chooserFilePath)
                    val mControl=MediaController(this)
                    storageVidView.setMediaController(mControl)
                    mControl.setAnchorView(storageVidView)
                    if (!storageVidView.isPlaying)
                    {
                      storageVidView.start()
                    }
                }
                "application/pdf"->{

                }

            }


        }
    }


}