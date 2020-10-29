package FirebaseDatabase.FirebaseRecycler

import FirebaseDatabase.UserHabit
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import cycle.gear.googlesignin2.R
import kotlinx.android.synthetic.main.activity_user_habits.*

class UserHabits : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_habits)
        val exampleHabits=dummyTeastHabit(25)

        habit_RecyclerView.adapter=HabitsAdapter(exampleHabits)
        habit_RecyclerView.layoutManager=LinearLayoutManager(this)
        habit_RecyclerView.setHasFixedSize(true)
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