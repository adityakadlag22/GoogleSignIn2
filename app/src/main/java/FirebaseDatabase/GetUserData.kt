package FirebaseDatabase

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
import kotlinx.android.synthetic.main.activity_get_user_data.*

class GetUserData : AppCompatActivity() {
    private var myRef = FirebaseDatabase.getInstance().getReference("Users")
    private lateinit var auth: FirebaseAuth
    private val TAG = "GetUserData"
    private lateinit var user: FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_user_data)
        auth = FirebaseAuth.getInstance()
        checkUser()
        submitData_btn.setOnClickListener {
            val name = userName_Edit.text.toString()
            val age = userAge_Edit.text.toString()
            if (name.isEmpty() || age.isEmpty()) {
                toast("Fill The Fields")
            } else {
                val uid = user.uid
                myRef.child(uid).child("userdata")
                    .child("username").setValue(name)

                myRef.child(uid).child("userdata")
                    .child("userage").setValue(age).addOnCompleteListener {
                        if (it.isSuccessful)
                        {
                         SendUserToDataActivity()
                        }
                    }
            }
        }
    }

    private fun SendUserToDataActivity() {
        Intent(this, FireDatabase::class.java).also {
            startActivity(it)
            toast("Data Added")
            finish()

        }
    }

    private fun checkUser() {
        val user1 = auth.currentUser
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