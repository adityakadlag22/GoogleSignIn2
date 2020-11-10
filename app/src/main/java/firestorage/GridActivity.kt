package firestorage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference

import cycle.gear.googlesignin2.LoginActivity
import cycle.gear.googlesignin2.R

import firestorage.models.modelcontent2
import kotlinx.android.synthetic.main.activity_grid.*

class GridActivity : AppCompatActivity() {
    private var myRef = FirebaseDatabase.getInstance().getReference("Users")
    private lateinit var mAuth: FirebaseAuth
    private val TAG = "GridActivity"
    private lateinit var user: FirebaseUser
    val imageList = ArrayList<modelcontent2>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid)
        initAll()
        loadAllItems()
//        loadAllItems2()

    }

    fun loadAllItems() {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference.child("userImage")
        val listAllTasks: Task<ListResult> = storageRef.listAll()
        listAllTasks.addOnCompleteListener { result ->
            val images: List<StorageReference> = result.result!!.items
            images.forEachIndexed { index, item ->
                item.downloadUrl.addOnSuccessListener {
                    imageList.add(modelcontent2(it.toString()))

                }.addOnCompleteListener {
                    content_RecyclerView.adapter = contentAdap(imageList, this)
                    content_RecyclerView.layoutManager = GridLayoutManager(this,2)

                }
            }
        }
    }
//    fun loadAllItems2() {
//        val storage = FirebaseStorage.getInstance()
//        val storageRef = storage.reference.child("userVideo")
//        val listAllTasks: Task<ListResult> = storageRef.listAll()
//        listAllTasks.addOnCompleteListener { result ->
//            val videos: List<StorageReference> = result.result!!.items
//            videos.forEachIndexed { index, item ->
//                item.downloadUrl.addOnSuccessListener {
//                    imageList.add(modelcontent2(it.toString()))
//
//                }.addOnCompleteListener {
//                    content_RecyclerView.adapter = contentAdap(imageList, this)
//                    content_RecyclerView.layoutManager = GridLayoutManager(this,2)
//
//                }
//            }
//        }
//    }

    private fun initAll() {
        mAuth = FirebaseAuth.getInstance()
        checkUser()
        user = mAuth.currentUser!!
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