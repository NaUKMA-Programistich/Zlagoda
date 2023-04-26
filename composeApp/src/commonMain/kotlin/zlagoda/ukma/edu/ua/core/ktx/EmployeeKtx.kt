package zlagoda.ukma.edu.ua.core.ktx

import zlagoda.ukma.edu.ua.db.Employee

fun Employee.isSeller(): Boolean = this.empl_role == "Seller"

fun Employee.isManager(): Boolean = this.empl_role == "Manager"