package cycle.gear.googlesignin2

import Utils.toast
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var toggle:ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
      //  toolbar=findViewById(R.id.toolBarMain)
        setSupportActionBar(toolBarMain)
        checkUser()



        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle= ActionBarDrawerToggle(this,drawerLayoutMain,R.string.open,R.string.close)
        drawerLayoutMain.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener {
            when(it.itemId)
            {
              R.id.firebaseDatabase->{toast("Working on Firebase Database ")}

              R.id.fireFCM->{ toast("Working on Firebase FCM ")}


            }
            true
        }
    }

    private fun checkUser()
    {
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
        {
          return true
        }
        return super.onOptionsItemSelected(item)
    }
}
