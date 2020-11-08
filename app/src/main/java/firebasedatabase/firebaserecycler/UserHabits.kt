package firebasedatabase.firebaserecycler

import firebasedatabase.UserHabit
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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

import cycle.gear.googlesignin2.LoginActivity
import cycle.gear.googlesignin2.R
import kotlinx.android.synthetic.main.activity_user_habits.*
import kotlinx.android.synthetic.main.layout_habits_dialog.view.*
import java.util.*
import kotlin.collections.ArrayList

class UserHabits : AppCompatActivity() {
    private var myRef = FirebaseDatabase.getInstance().getReference("Users")
    private lateinit var mAuth: FirebaseAuth
    var adapter = HabitsAdapter2(this)
    private lateinit var user: FirebaseUser
    private val TAG = "UserHabits"
    var habitList = ArrayList<UserHabit>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_habits)
        init()





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

        getAllHabits()
        habits_FloatingActionBtn.setOnClickListener {
            openDialog()
        }

        val touchhelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or
                    ItemTouchHelper.DOWN, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val source = viewHolder.adapterPosition
                val targetposition = target.adapterPosition
                Collections.swap(habitList, source, targetposition)
                adapter.notifyItemMoved(source, targetposition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        val habit = habitList[position]
                        val key = habit.key
                        myRef.child(user.uid).child("userhabits").child(key).removeValue()
                        habitList.removeAt(position)
                        //adapter.notifyItemRemoved(position)
                        getAllHabits()

                    }
                    ItemTouchHelper.RIGHT -> {


                    }
                }
            }

        })
        touchhelper.attachToRecyclerView(habit_RecyclerView)
    }

    private fun init() {
        //Auth
        mAuth = FirebaseAuth.getInstance()
        //Toolbar
        setSupportActionBar(toolBar_habitsAct)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        //User
        checkUser()
        user = mAuth.currentUser!!
        //Recycler View
        habit_RecyclerView.adapter = adapter
        habit_RecyclerView.layoutManager = LinearLayoutManager(this)
        habit_RecyclerView.setHasFixedSize(true)

        habit_RecyclerView.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )

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

            if (habitname.isEmpty() || habitdesc.isEmpty() || habitprior.isEmpty()) {
                toast("Fill The Fields")
            } else {

                val id = myRef.child("userhabits").push().key
                val habit = UserHabit(
                    id.toString(),
                    habitName = habitname.toString(),
                    habitprior.toString(),
                    habitdesc.toString()
                )
                myRef.child(user.uid).child("userhabits").child(id.toString()).setValue(habit)

                getAllHabits()
            }
        }
        mDialog.cancelBtnHabit.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }


    private fun getAllHabits() {
        habitList.clear()
        myRef.child(user.uid).child("userhabits")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChildren()) {
                        for (snap in snapshot.children) {
                            habitList.add(snap.getValue(UserHabit::class.java)!!)
                        }
                        adapter.addAll(habitList)
                        adapter.notifyDataSetChanged()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    toast(error.toString())
                }

            })
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