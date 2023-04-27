package zlagoda.ukma.edu.ua.screens.options.viewmodel

import zlagoda.ukma.edu.ua.db.Employee
import zlagoda.ukma.edu.ua.db.GetSalesInfoForAllEmployees
import zlagoda.ukma.edu.ua.db.GetSalesSummaryByCategory

sealed class OptionsAction {
    object GoToLogin: OptionsAction()

    data class DzhosGroup(val data: List<GetSalesSummaryByCategory>): OptionsAction()

    data class DubovikGroup(val data: List<GetSalesInfoForAllEmployees>): OptionsAction()
    data class DubovikNot(val data: List<Employee>): OptionsAction()
}
