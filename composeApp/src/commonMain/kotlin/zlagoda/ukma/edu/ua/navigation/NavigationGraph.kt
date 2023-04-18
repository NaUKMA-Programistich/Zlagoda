package zlagoda.ukma.edu.ua.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ru.alexgladkov.odyssey.compose.extensions.bottomNavigation
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.extensions.tab
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.tabs.TabColors
import ru.alexgladkov.odyssey.compose.navigation.tabs.TabContent
import ru.alexgladkov.odyssey.compose.navigation.tabs.TabDefaults
import zlagoda.ukma.edu.ua.screens.category.CategoriesScreen
import zlagoda.ukma.edu.ua.screens.login.LoginScreen

@Composable
internal fun RootComposeBuilder.NavigationGraph() {
    screen(NavigationRoute.Login.name) {
        LoginScreen()
    }
    bottomNavigation(NavigationRoute.Main.name) {
        tab(content = OrdersTab(), colors = TabColors()) {
            screen(NavigationRoute.Orders.name) {
                Text("Orders")
            }
        }
        tab(content = ProductsTab(), colors = TabColors()) {
            screen(NavigationRoute.Products.name) {
                Text("Products")
            }
        }
        tab(content = CategoriesTab(), colors = TabColors()) {
            screen(NavigationRoute.Categories.name) {
                CategoriesScreen()
            }
        }
    }
}

@Composable
private fun OrdersTab(): TabContent {
    return TabDefaults.content("Orders", vector = null)
}

@Composable
private fun ProductsTab(): TabContent {
    return TabDefaults.content("Products", vector = null)
}

@Composable
private fun CategoriesTab(): TabContent {
    return TabDefaults.content("Categories", vector = null)
}

@Composable
private fun TabColors(): TabColors {
    return TabDefaults.tabColors(
        selectedTextColor = Color.Black,
        selectedIconColor = Color.Black,
        unselectedTextColor = Color.Gray,
        unselectedIconColor = Color.Gray,
    )
}


internal enum class NavigationRoute {
    Login,
    Main,
    Orders,
    Products,
    Categories
}