package cycle.gear.googlesignin2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase

class FireDatabase : AppCompatActivity() {
    private lateinit var firedb:FirebaseDatabase
    private var myRef=FirebaseDatabase.getInstance().getReference("Users")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fire_database)
    }
}