package firebasedatabase

import firebasedatabase.firebaserecycler.UserHabits
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
import cycle.gear.googlesignin2.LoginActivity
import cycle.gear.googlesignin2.R
import kotlinx.android.synthetic.main.activity_fire_database.*

class FireDatabase : AppCompatActivity() {
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
        checkUserHasData()
        userHabitsBtn.setOnClickListener {
        Intent(this,UserHabits::class.java).also {
        startActivity(it)
        }
        }

    }

    private fun checkUserHasData() {
    myRef.child(user.uid).addValueEventListener(object: ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
        val data:Boolean=snapshot.hasChild("userdata")
        if (data){loadData()}
        else
        {
         getDataFromAct()
        }
        }

        override fun onCancelled(error: DatabaseError) {
        toast(error.toString())
        }
    })
    }

    private fun loadData() {
    myRef.child(user.uid).addValueEventListener(object: ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
        val username=snapshot.child("userdata").child("username").value
        val userAge=snapshot.child("userdata").child("userage").value
        if (username!=null && userAge!= null)
        {
         userName_txt.text=username.toString()
         userAge_txt.text=userAge.toString()
        }
        }

        override fun onCancelled(error: DatabaseError) {
        toast(error.message)
        }
    })
    }

    private fun getDataFromAct() {
    Intent(this, GetUserData::class.java).also {
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