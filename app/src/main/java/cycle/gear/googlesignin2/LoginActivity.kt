 package cycle.gear.googlesignin2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

 class LoginActivity : AppCompatActivity() {
     private val RC_SIGN_IN=1
     private lateinit var auth: FirebaseAuth
     private lateinit var mGoogleSignInClient: GoogleSignInClient
     private lateinit var gso:GoogleSignInOptions
     private lateinit var listener:FirebaseAuth.AuthStateListener
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth=FirebaseAuth.getInstance()
        listener=FirebaseAuth.AuthStateListener {

        }
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient=GoogleSignIn.getClient(this,gso)

        findViewById<View>(R.id.sign_in_btn).setOnClickListener {
            signInGoogle()
        }

    }

     private fun signInGoogle()
     {
       val signInIntent:Intent=mGoogleSignInClient.signInIntent
       startActivityForResult(signInIntent,RC_SIGN_IN)
     }

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         super.onActivityResult(requestCode, resultCode, data)
         if (requestCode==RC_SIGN_IN)
         {
           val task:Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
           handleResult(task)
         }
     }

     private fun handleResult(task: Task<GoogleSignInAccount>) {
         try {
         val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
         updateUI(account)
         }
         catch (e: ApiException)
         {
          Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show()
         }
     }

     private fun updateUI(account: GoogleSignInAccount?) {
     if (account!=null)
     {
      Intent(this,MainActivity::class.java).also {
          startActivity(it)
          finish()
      }
     }
     else{
     Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
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