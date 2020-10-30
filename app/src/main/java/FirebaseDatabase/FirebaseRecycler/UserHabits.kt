package FirebaseDatabase.FirebaseRecycler

import FirebaseDatabase.UserHabit
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import androidx.constraintlayout.widget.ConstraintSet.INVISIBLE
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cycle.gear.googlesignin2.R
import kotlinx.android.synthetic.main.activity_user_habits.*

class UserHabits : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_habits)
        val exampleHabits = dummyTeastHabit(10)

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

        }
    }

    private fun dummyTeastHabit(size: Int): List<UserHabit> {
        val list = ArrayList<UserHabit>()
        for (i in 0 until size) {
            val item = UserHabit("habit $i", description = "desc $i", habitPriority = i)
            list += item
        }
        return list
    }
}