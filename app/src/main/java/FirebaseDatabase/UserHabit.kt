package FirebaseDatabase

data class UserHabit(var habitName:String, var habitPriority:Int, var description:String) {

    override fun toString(): String {
        return "UserHabits(habitName='$habitName', habitPriority=$habitPriority)"
    }

}