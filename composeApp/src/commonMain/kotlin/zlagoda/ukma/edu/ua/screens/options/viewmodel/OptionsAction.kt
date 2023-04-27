package zlagoda.ukma.edu.ua.screens.options.viewmodel

import zlagoda.ukma.edu.ua.db.GetSalesSummaryByCategory

sealed class OptionsAction {
    object GoToLogin: OptionsAction()

    data class DzhosGroup(val data: List<GetSalesSummaryByCategory>): OptionsAction()
}
