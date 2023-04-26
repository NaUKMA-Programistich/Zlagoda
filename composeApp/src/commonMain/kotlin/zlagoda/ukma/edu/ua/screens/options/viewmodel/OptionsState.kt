package zlagoda.ukma.edu.ua.screens.options.viewmodel

import zlagoda.ukma.edu.ua.db.Employee

sealed class OptionsState {
    data class EntryDisplay(
        val employee: Employee
    ) : OptionsState()
    object Loading : OptionsState()
}
