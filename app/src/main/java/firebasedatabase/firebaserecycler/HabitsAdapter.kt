package firebasedatabase.firebaserecycler

import firebasedatabase.UserHabit
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cycle.gear.googlesignin2.R
import kotlinx.android.synthetic.main.habitlistitem.view.*

class HabitsAdapter(private val habitlist:List<UserHabit>) : RecyclerView.Adapter<HabitsAdapter.HabitViewHolder>() {


    class HabitViewHolder(item:View): RecyclerView.ViewHolder(item){
    var habititle:TextView=item.user_Habit
    var habitprior:TextView=item.habit_priority
    var habitDesc:TextView=item.habitdesc

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.habitlistitem
        ,parent,false)

        return HabitViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val currentItem=habitlist[position]
        holder.habititle.text=currentItem.habitName
        holder.habitDesc.text=currentItem.description
        holder.habitprior.text=currentItem.habitPriority.toString()
    }

    override fun getItemCount()= habitlist.size
}