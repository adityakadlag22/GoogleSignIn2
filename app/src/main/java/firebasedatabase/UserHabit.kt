package firebasedatabase


data class UserHabit(var key:String="",var habitName: String="", var habitPriority: String="", var description: String="") {


//
//    constructor(key:String?=null, habitName: String="", habitPriority: String="", description: String=""){
//     this.habitName=habitName
//     this.habitPriority=habitPriority
//     this.description=description
//     this.key=key
//    }

    override fun toString(): String {
        return "UserHabits(habitName='$habitName', habitPriority=$habitPriority)"
    }


}