package FirebaseDatabase.FirebaseRecycler

import FirebaseDatabase.UserHabit
import Utils.toast
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import cycle.gear.googlesignin2.LoginActivity
import cycle.gear.googlesignin2.R
import kotlinx.android.synthetic.main.activity_user_habits.*
import kotlinx.android.synthetic.main.layout_habits_dialog.view.*

class UserHabits : AppCompatActivity() {
    private var myRef = FirebaseDatabase.getInstance().getReference("Users")
    private lateinit var mAuth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private val TAG = "UserHabits"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_habits)
        mAuth= FirebaseAuth.getInstance()

        setSupportActionBar(toolBar_habitsAct)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        checkUser()
        val exampleHabits = dummyTestHabit(10)
        habit_RecyclerView.adapter = HabitsAdapter(exampleHabits)
        habit_RecyclerView.layoutManager = LinearLayoutManager(this)
        habit_RecyclerView.setHasFixedSize(true)

        habit_RecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    habits_FloatingActionBtn.visibility = View.INVISIBLE
                } else {
                    habits_FloatingActionBtn.visibility = View.VISIBLE
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        habits_FloatingActionBtn.setOnClickListener {
            openDialog()
        }
    }

    private fun openDialog() {
        val mDialog = LayoutInflater.from(this).inflate(R.layout.layout_habits_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialog)
            .setTitle("Add Habit")
        val mAlertDialog = mBuilder.show()
        mDialog.addBtnHabit.setOnClickListener {
            mAlertDialog.dismiss()
            val habitname = mDialog.userHabitName_edit.text
            val habitdesc = mDialog.userHabitDesc_edit.text
            val habitprior = mDialog.userHabitPrior_edit.text

            if (habitname.isEmpty() || habitdesc.isEmpty()||habitprior.isEmpty())
            {
              toast("Fill The Fields")  
            }    
            else
            {
                Log.d(TAG, "openDialog: done")
            }    
        }
        mDialog.cancelBtnHabit.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }

    private fun dummyTestHabit(size: Int): List<UserHabit> {
        val list = ArrayList<UserHabit>()
        for (i in 1 until size) {
            val item = UserHabit("habit $i", description = "desc $i", habitPriority = i)
            list += item
        }
        return list
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