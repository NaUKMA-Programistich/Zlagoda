package zlagoda.ukma.edu.ua.utils.authorization

import zlagoda.ukma.edu.ua.db.Employee

object Authorization {

    private var currentUser: Employee? = null
    fun updateCurrentUser(user: Employee?) {
        currentUser = user
    }

    fun currentUserHasRole(role: String): Boolean {
        return currentUser?.empl_role == role
    }

}