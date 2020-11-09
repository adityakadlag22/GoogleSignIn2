package firestorage

import Utils.toast
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import com.google.firebase.database.FirebaseDatabase

import cycle.gear.googlesignin2.LoginActivity
import cycle.gear.googlesignin2.R

import firestorage.models.modelcontent

class GridActivity : AppCompatActivity() {
    private var myRef = FirebaseDatabase.getInstance().getReference("Users")
    private lateinit var mAuth: FirebaseAuth
    private val TAG = "GridActivity"
    private lateinit var user: FirebaseUser
    val contentList = ArrayList<modelcontent>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid)
        initAll()
        loadAllItems()

    }

    fun loadAllItems() {

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