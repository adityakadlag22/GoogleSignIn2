package firebasedatabase.firebaserecycler

import firebasedatabase.UserHabit
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import cycle.gear.googlesignin2.R
import kotlinx.android.synthetic.main.habitlistitem.view.*

class HabitsAdapter2(internal var context: Context) :
    RecyclerView.Adapter<HabitsAdapter2.ViewHolder>() {
    internal var userList: MutableList<UserHabit>

    val lastItemID: String?
        get() = userList[userList.size - 1].habitName


    fun addAll(newhabits: List<UserHabit>) {
        val init = userList.size
        userList.clear()
        userList.addAll(newhabits)
        notifyItemRangeChanged(init, userList.size)
    }

    fun clearall() {
        userList.clear()
    }


    init {
        this.userList = ArrayList()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var habitName: TextView
        internal var habitDesc: TextView
        internal var habitPrior: TextView

        init {
            habitName = itemView.user_Habit
            habitPrior = itemView.habit_priority
            habitDesc = itemView.habitdesc
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.habitlistitem, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = userList[position]
        holder.habitName.text = currentItem.habitName
        holder.habitDesc.text = currentItem.description
        holder.habitPrior.text = currentItem.habitPriority

        holder.itemView.setOnClickListener {
            val key = currentItem.key

        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }


}