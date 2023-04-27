package zlagoda.ukma.edu.ua.screens.cheque_details.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import zlagoda.ukma.edu.ua.core.composable.DropDownItem
import zlagoda.ukma.edu.ua.core.composable.ItemWithDropdown
import zlagoda.ukma.edu.ua.data.cheque.ChequeRepository
import zlagoda.ukma.edu.ua.data.employee.EmployeeRepository
import zlagoda.ukma.edu.ua.db.Cheque
import zlagoda.ukma.edu.ua.db.Employee
import zlagoda.ukma.edu.ua.db.GetAllChecksInfoBySellerWithProductsInDateRange
import zlagoda.ukma.edu.ua.di.Injection

@Composable
internal fun DetailsBySellerForToday(
    onClose: () -> Unit,
    employeeRepository: EmployeeRepository = Injection.employeeRepository,
    chequeRepository: ChequeRepository = Injection.chequeCardRepository
) {
    var sellers by remember { mutableStateOf(emptyList<Employee>()) }
    var selecetedSellerIndex by remember { mutableStateOf(0) }
    var data by remember { mutableStateOf(emptyList<Cheque>()) }

    LaunchedEffect(key1 = true) {
        employeeRepository.getAllSellers().collectLatest {
            sellers = it
        }
    }

    LaunchedEffect(sellers, selecetedSellerIndex) {
        chequeRepository.getAllChequesBySellerForToday(
            idEmployee = if (sellers.isNotEmpty()) sellers[selecetedSellerIndex].id_of_employee else ""
        ).collectLatest {
            data = it
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Cheques by seller for today",
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
        }
        ChequeItem(null)
        LazyColumn {
            items(data) {
                ChequeItem(it)
            }
        }
    }
}

@Composable
fun ChequeItem(
    data: Cheque?
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(20.dp)
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = data?.chequeNumber?: "Cheque number"
        )
        Divider(modifier = Modifier.fillMaxHeight().width(1.dp),)
        Text(
            modifier = Modifier.weight(1f),
            text = data?.idEmployee?: "ID employee"
        )
        Divider(modifier = Modifier.fillMaxHeight().width(1.dp),)
        Text(
            modifier = Modifier.weight(1f),
            text = data?.printDate?.toString()?: "Date"
        )
        Divider(modifier = Modifier.fillMaxHeight().width(1.dp),)
        Text(
            modifier = Modifier.weight(1f),
            text = data?.sumTotal?.toString()?: "Sum total"
        )
        Divider(modifier = Modifier.fillMaxHeight().width(1.dp),)
        Text(
            modifier = Modifier.weight(1f),
            text = data?.vat?.toString()?: "Vat"
        )
    }
}

private fun List<Employee>.toDropDownItems(): List<DropDownItem> {
    return List(this.size) { DropDownItem(
        id = it.toLong(),
        text = "${this[it].empl_surname} ${this[it].empl_name}" )
    }
}