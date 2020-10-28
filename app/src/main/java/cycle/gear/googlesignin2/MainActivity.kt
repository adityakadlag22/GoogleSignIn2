package cycle.gear.googlesignin2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth



class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        Handler().postDelayed({
            if (user != null) {
                Toast.makeText(this, user.toString(), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No User", Toast.LENGTH_SHORT).show()
                Intent(this,LoginActivity::class.java).also {
                startActivity(it)
                finish()
                }
            }
        }, 2000)

    }


}