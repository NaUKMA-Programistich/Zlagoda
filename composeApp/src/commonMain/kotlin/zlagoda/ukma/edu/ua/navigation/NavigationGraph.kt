package zlagoda.ukma.edu.ua.navigation

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import com.adeo.kviewmodel.odyssey.ViewModelStore
import ru.alexgladkov.odyssey.compose.extensions.bottomNavigation
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.extensions.tab
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.BottomBarDefaults
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.MultiStackBuilder
import ru.alexgladkov.odyssey.compose.navigation.tabs.TabColors
import ru.alexgladkov.odyssey.compose.navigation.tabs.TabContent
import ru.alexgladkov.odyssey.compose.navigation.tabs.TabDefaults
import zlagoda.ukma.edu.ua.core.ktx.isManager
import zlagoda.ukma.edu.ua.db.Employee
import zlagoda.ukma.edu.ua.di.Injection
import zlagoda.ukma.edu.ua.screens.category.CategoriesScreen
import zlagoda.ukma.edu.ua.screens.cheque_details.OrderDetailsScreen
import zlagoda.ukma.edu.ua.screens.customer_cards.CustomerCardsScreen
import zlagoda.ukma.edu.ua.screens.login.LoginScreen
import zlagoda.ukma.edu.ua.screens.employee.EmployeeScreen
import zlagoda.ukma.edu.ua.screens.login.viewmodel.LoginViewModel
import zlagoda.ukma.edu.ua.screens.options.OptionsScreen
import zlagoda.ukma.edu.ua.screens.order.OrderScreen
import zlagoda.ukma.edu.ua.screens.products.ProductsScreen
import zlagoda.ukma.edu.ua.screens.store_storeProduct.StoreProductsScreen

@Composable
internal fun RootComposeBuilder.NavigationGraph() {
    screen(NavigationRoute.Login.name) {
        LoginScreen()
    }
    bottomNavigation(
        name = NavigationRoute.Main.name,
        colors = BottomBarDefaults.bottomColors(MaterialTheme.colors.onSecondary)
    ) {

        tab(content = ProfilesTab(), colors = TabColors()) {
            screen(NavigationRoute.Profiles.name) {
                EmployeeScreen()
            }
        }
        tab(content = OrdersTab(), colors = TabColors()) {
            screen(NavigationRoute.Orders.name) {
                OrderScreen()
            }
        }
        tab(content = OrderDetailsTab(), colors = TabColors()) {
            screen(NavigationRoute.Orders.name) {
                OrderDetailsScreen()
            }
        }
        tab(content = ProductsTab(), colors = TabColors()) {
            screen(NavigationRoute.Products.name) {
                ProductsScreen()
            }
        }
        tab(content = StoreProductsTab(), colors = TabColors()) {
            screen(NavigationRoute.StoreProducts.name) {
                StoreProductsScreen()
            }
        }
        tab(content = CategoriesTab(), colors = TabColors()) {
            screen(NavigationRoute.Categories.name) {
                CategoriesScreen()
            }
        }
        tab(content = CustomerCardsTab(), colors = TabColors()) {
            screen(NavigationRoute.CustomerCars.name) {
                CustomerCardsScreen()
            }
        }
        tab(content = OptionsTab(), colors = TabColors()) {
            screen(NavigationRoute.Options.name) {
                OptionsScreen()
            }
        }
    }
}


@Composable
private fun OrdersTab(): TabContent {
    return TabDefaults.content("Orders", vector = Icons.Default.ShoppingCart)
}

@Composable
private fun OrderDetailsTab(): TabContent {
    return TabDefaults.content("Order Details", vector = Icons.Default.ShoppingCart)
}

@Composable
private fun ProductsTab(): TabContent {
    return TabDefaults.content("Products", vector = Icons.Default.Menu)
}

@Composable
private fun StoreProductsTab(): TabContent {
    return TabDefaults.content("Store Products", vector = Icons.Default.Place)
}


@Composable
private fun CategoriesTab(): TabContent {
    return TabDefaults.content("Categories", vector = Icons.Default.List)
}

@Composable
private fun ProfilesTab(): TabContent {
    return TabDefaults.content("Profiles", vector = Icons.Default.Face)
}

@Composable
private fun CustomerCardsTab(): TabContent {
    return TabDefaults.content("Customer Cards", vector = Icons.Default.Person)
}

@Composable
private fun OptionsTab(): TabContent {
    return TabDefaults.content("Options", vector = Icons.Default.Settings)
}


@Composable
private fun TabColors(): TabColors {
    return TabDefaults.tabColors(
        selectedTextColor = Color.White,
        selectedIconColor = Color.White,
        unselectedTextColor = Color.Black,
        unselectedIconColor = Color.Black,
    )
}


internal enum class NavigationRoute {
    Login,
    Main,
    Orders,
    OrderDetails,
    Products,
    StoreProducts,
    Categories,
    Profiles,
    CustomerCars,
    Options
}