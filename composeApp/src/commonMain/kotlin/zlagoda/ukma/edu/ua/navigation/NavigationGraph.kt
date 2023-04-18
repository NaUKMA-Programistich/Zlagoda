package zlagoda.ukma.edu.ua.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.brands.WordpressSimple
import compose.icons.fontawesomeicons.regular.IdCard
import ru.alexgladkov.odyssey.compose.extensions.bottomNavigation
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.extensions.tab
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.tabs.TabColors
import ru.alexgladkov.odyssey.compose.navigation.tabs.TabContent
import ru.alexgladkov.odyssey.compose.navigation.tabs.TabDefaults
import zlagoda.ukma.edu.ua.screens.login.LoginScreen
import androidx.compose.ui.graphics.Color
import compose.icons.fontawesomeicons.Regular

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
    Products
}