package FirebaseDatabase

import android.os.Parcel
import android.os.Parcelable

class UserHabit(var habitName: String, var habitPriority: String, var description: String) {



    override fun toString(): String {
        return "UserHabits(habitName='$habitName', habitPriority=$habitPriority)"
    }


}