package cycle.gear.googlesignin2

import Utils.toast
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FireDatabase : AppCompatActivity() {
    private lateinit var firedb:FirebaseDatabase
    private var myRef=FirebaseDatabase.getInstance().getReference("Users")
    private lateinit var mAuth:FirebaseAuth
    private val TAG = "FireDatabase"
    private lateinit var user: FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fire_database)
        mAuth = FirebaseAuth.getInstance()
        checkUser()
        user=mAuth.currentUser!!
        CheckUserHasData()
    }

    private fun CheckUserHasData() {
    myRef.child(user.uid).addValueEventListener(object: ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
        val data:Boolean=snapshot.hasChild("userdata")
        if (data){toast("User Has Data")}
        else
        {
         GetDataFromActivity()
        }
        }

        override fun onCancelled(error: DatabaseError) {
        toast(error.toString())
        }
    })
    }

    private fun GetDataFromActivity() {
    Intent(this,GetUserData::class.java).also {
        startActivity(it)
    }
    }

    private fun checkUser() {
        val user1 = mAuth.currentUser
        Handler().postDelayed({
            if (user1 != null) {
                Log.d(TAG, "checkUser: ${user1.displayName} ")
                user=user1
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