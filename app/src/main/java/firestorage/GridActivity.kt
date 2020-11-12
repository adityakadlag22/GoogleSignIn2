package firestorage

import Utils.toast
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference

import cycle.gear.googlesignin2.LoginActivity
import cycle.gear.googlesignin2.R
import firestorage.models.modelcontent

import kotlinx.android.synthetic.main.activity_grid.*

class GridActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private val TAG = "GridActivity"
    private lateinit var user: FirebaseUser
    private val fullList = ArrayList<modelcontent>()
    private val imageList = ArrayList<modelcontent>()
    private val videoList = ArrayList<modelcontent>()
    private val adapter = ContentAdap(imageList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid)
        initAll()
        loadAllItems()
        loadAllItems2()
        addBoth()

    }

    private fun loadAllItems() {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference.child("userImage")
            .child(user.uid)
        val listAllTasks: Task<ListResult> = storageRef.listAll()
        listAllTasks.addOnCompleteListener { result ->
            val images: List<StorageReference> = result.result!!.items
            images.forEachIndexed { _, item ->
                item.downloadUrl.addOnSuccessListener {
                    imageList.add(modelcontent(it.toString(), "image"))

                }.addOnCompleteListener {
                    content_RecyclerView.adapter = adapter
                    content_RecyclerView.layoutManager = LinearLayoutManager(this)
                    Log.d(TAG, "loadAllItems: images loaded")
                }
            }
        }
    }

    private fun loadAllItems2() {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference.child("userVideo")
            .child(user.uid)
        val listAllTasks: Task<ListResult> = storageRef.listAll()
        listAllTasks.addOnCompleteListener { result ->
            val videos: List<StorageReference> = result.result!!.items
            videos.forEachIndexed { _, item ->
                item.downloadUrl.addOnSuccessListener {
                    videoList.add(modelcontent(it.toString(), "video"))

                }.addOnCompleteListener {
                    toast("vid loaded")
                }
            }
        }
    }

    private fun addBoth() {
        fullList.addAll(imageList)
        fullList.addAll(videoList)

        content_RecyclerView.adapter = ContentAdap(fullList, this)
        content_RecyclerView.layoutManager = GridLayoutManager(this, 2)
    }

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