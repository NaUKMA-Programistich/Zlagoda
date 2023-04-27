package zlagoda.ukma.edu.ua.screens.cheque_details.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import zlagoda.ukma.edu.ua.core.composable.CategoryDropdown
import zlagoda.ukma.edu.ua.core.composable.DropDownItem
import zlagoda.ukma.edu.ua.core.composable.ItemWithDropdown
import zlagoda.ukma.edu.ua.core.ktx.isManager
import zlagoda.ukma.edu.ua.core.ktx.isSeller
import zlagoda.ukma.edu.ua.core.ktx.toStr
import zlagoda.ukma.edu.ua.data.employee.EmployeeRepository
import zlagoda.ukma.edu.ua.db.Category
import zlagoda.ukma.edu.ua.db.Employee
import zlagoda.ukma.edu.ua.db.Product
import zlagoda.ukma.edu.ua.di.Injection
import zlagoda.ukma.edu.ua.screens.products.ui.toDropDownItems
import zlagoda.ukma.edu.ua.screens.products.viewmodel.ProductsEvent
import zlagoda.ukma.edu.ua.screens.products_search.ui.ProductSearchItem
import zlagoda.ukma.edu.ua.utils.validation.InvalidModelException
import zlagoda.ukma.edu.ua.utils.validation.ProductValidator

@Composable
internal fun DetailsBySellerAndDateRangeDialog(
    onClose: () -> Unit,
    employeeRepository: EmployeeRepository = Injection.employeeRepository
) {
    var dateStartStr by remember { mutableStateOf("2001-01-01") }
    var dateEndStr by remember { mutableStateOf("2031-01-01") }
    var sellers by remember { mutableStateOf(emptyList<Employee>()) }
    var selecetedSellerIndex by remember { mutableStateOf(0) }

    LaunchedEffect(key1 = true) {
        employeeRepository.getAllSellers().collectLatest {
            sellers = it
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Cheques detals by seller and date range",
                style = MaterialTheme.typography.h5
            )
            IconButton(onClick = onClose) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close dialog")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (sellers.isNotEmpty()) {
                val seller = sellers[selecetedSellerIndex]
                val sellerFullName = "${seller.empl_surname} ${seller.empl_name}"
                ItemWithDropdown(
                    modifier = Modifier.width(200.dp),
                    value = sellerFullName,
                    dropdownItems = sellers.toDropDownItems(),
                    onItemClick = { selecetedSellerIndex = it.id.toInt() }
                )
            }
            OutlinedTextField(
                value = dateStartStr,
                onValueChange = { dateStartStr = it },
                label = { Text("Start Date (yyyy-mm-dd)") },
                modifier = Modifier.width(250.dp).padding(5.dp)
            )
            OutlinedTextField(
                value = dateEndStr,
                onValueChange = { dateEndStr = it },
                label = { Text("End Date (yyyy-mm-dd)") },
                modifier = Modifier.width(250.dp).padding(5.dp)
            )

        }
        LazyColumn {

        }
    }
}

private fun List<Employee>.toDropDownItems(): List<DropDownItem> {
    return List(this.size) { DropDownItem(
        id = it.toLong(),
        text = "${this[it].empl_surname} ${this[it].empl_name}" )
    }
}