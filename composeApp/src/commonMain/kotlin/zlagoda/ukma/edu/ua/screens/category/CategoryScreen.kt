package zlagoda.ukma.edu.ua.screens.category

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import zlagoda.ukma.edu.ua.screens.category.ui.CategoryViewItem
import zlagoda.ukma.edu.ua.screens.category.ui.CategoryViewList
import zlagoda.ukma.edu.ua.screens.category.viewmodel.CategoryState
import zlagoda.ukma.edu.ua.screens.category.viewmodel.CategoryViewModel

@Composable
internal fun CategoriesScreen() {
    StoredViewModel(factory = { CategoryViewModel() }) { viewModel ->
        val navController = LocalRootController.current
        val viewState by viewModel.viewStates().observeAsState()
        val viewAction by viewModel.viewActions().observeAsState()

        when (val state = viewState) {
            is CategoryState.CategoryList -> CategoryViewList(
                state = state,
                onEvent = { viewModel.obtainEvent(it) }
            )
            is CategoryState.CategoryItem -> CategoryViewItem(
                state = state,
                onEvent = { viewModel.obtainEvent(it) }
            )
        }

//        when (viewAction) {
//            LoginAction.GoToMainScreen -> {
//                navController.push(NavigationRoute.Main.name)
//            }
//            null -> {}
//        }
    }
}