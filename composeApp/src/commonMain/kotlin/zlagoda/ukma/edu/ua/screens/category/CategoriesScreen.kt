package zlagoda.ukma.edu.ua.screens.category

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.AlertConfiguration
import zlagoda.ukma.edu.ua.core.composable.ComposableLoading
import zlagoda.ukma.edu.ua.core.ktx.isManager
import zlagoda.ukma.edu.ua.core.ktx.isSeller
import zlagoda.ukma.edu.ua.db.Employee
import zlagoda.ukma.edu.ua.screens.category.ui.CategoryItemDialog
import zlagoda.ukma.edu.ua.screens.category.ui.CategoryViewList
import zlagoda.ukma.edu.ua.screens.category.viewmodel.CategoryAction
import zlagoda.ukma.edu.ua.screens.category.viewmodel.CategoryState
import zlagoda.ukma.edu.ua.screens.category.viewmodel.CategoryViewModel

@Composable
internal fun CategoriesScreen() {
    StoredViewModel(factory = { CategoryViewModel() }) { viewModel ->
        val modalController = LocalRootController.current.findModalController()
        val alertConfiguration = AlertConfiguration(maxHeight = 0.7f, maxWidth = 0.8f, cornerRadius = 4)
        val viewState by viewModel.viewStates().observeAsState()
        val viewAction by viewModel.viewActions().observeAsState()

        when (val state = viewState) {
            is CategoryState.CategoryList -> CategoryViewList(
                state = state,
                onEvent = { viewModel.obtainEvent(it) }
            )

            is CategoryState.Loading -> ComposableLoading()
            is CategoryState.NotSupport -> {
                Text(text = "Not supported")
            }
        }

        when (val action = viewAction) {
            is CategoryAction.OpenEditCategoryDialog -> modalController.present(alertConfiguration) { key ->
                CategoryItemDialog(
                    category = action.category,
                    onCloseClick = { modalController.popBackStack(key) },
                    onEvent = { viewModel.obtainEvent(it) }
                )
            }
            is CategoryAction.OpenNewCategoryDialog -> modalController.present(alertConfiguration) { key ->
                CategoryItemDialog(
                    onCloseClick = { modalController.popBackStack(key) },
                    onEvent = { viewModel.obtainEvent(it) }
                )
            }
            null -> {}
        }
    }
}