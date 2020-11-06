package firebasedatabase

data class UserHabit(var habitName: String="", var habitPriority: String="", var description: String="") {



    override fun toString(): String {
        return "UserHabits(habitName='$habitName', habitPriority=$habitPriority)"
    }


}