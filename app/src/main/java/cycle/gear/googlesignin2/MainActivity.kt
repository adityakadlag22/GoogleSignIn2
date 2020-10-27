package cycle.gear.googlesignin2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var listener:FirebaseAuth.AuthStateListener
    private lateinit var User: FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth= FirebaseAuth.getInstance()
        listener=FirebaseAuth.AuthStateListener {
            val user=auth.currentUser
            Handler().postDelayed({
                if (user==null)
                {
                    Intent(this,LoginActivity::class.java).also {
                        startActivity(it)
                        finish()
                    }
                }
                else{Toast.makeText(this,"Has User",Toast.LENGTH_SHORT).show()}
            },2000)

        }
    }

    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(listener)
    }

    override fun onStop() {
        super.onStop()
        auth.removeAuthStateListener(listener)
    }

}