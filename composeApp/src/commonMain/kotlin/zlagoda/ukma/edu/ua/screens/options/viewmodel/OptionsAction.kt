package zlagoda.ukma.edu.ua.screens.options.viewmodel

import zlagoda.ukma.edu.ua.db.*

sealed class OptionsAction {
    object GoToLogin: OptionsAction()

    data class DzhosGroup(val data: List<GetSalesSummaryByCategory>): OptionsAction()
    data class DzhosNot(val data: List<GetProductsNotSoldAndNotPromotionalAndPriceGreatThan>): OptionsAction()

    data class DubovikGroup(val data: List<GetSalesInfoForAllEmployees>): OptionsAction()
    data class DubovikNot(val data: List<Employee>): OptionsAction()

    data class MelnykGroup(val data: List<GetListSellersRankAndTheirTopCategoriesRank>): OptionsAction()

    data class MelnykNot(val data: List<String>): OptionsAction()

}
