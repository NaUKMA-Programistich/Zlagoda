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
import zlagoda.ukma.edu.ua.data.cheque.ChequeRepository
import zlagoda.ukma.edu.ua.data.employee.EmployeeRepository
import zlagoda.ukma.edu.ua.db.*
import zlagoda.ukma.edu.ua.di.Injection
import java.text.SimpleDateFormat
import java.util.*

@Composable
internal fun CheckDetailedDescription(
    onClose: () -> Unit,
    chequeRepository: ChequeRepository = Injection.chequeCardRepository
) {
    var checkNumberStr by remember { mutableStateOf("") }
    var data by remember { mutableStateOf(emptyList<GetCheckDetailedDescription>()) }


    LaunchedEffect(checkNumberStr) {
        chequeRepository.getCheckDetailedDescription(
            checkNumber = checkNumberStr
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
                text = "Cheque details by check number",
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
            OutlinedTextField(
                value = checkNumberStr,
                onValueChange = { checkNumberStr = it },
                label = { Text("checkNumber") },
                modifier = Modifier.width(250.dp).padding(5.dp)
            )
        }
        Row(modifier = Modifier.fillMaxWidth()){
            Text(text = "Main Check Info")
        }
        MainCheckData(null)
        if(data.isNotEmpty())
            MainCheckData(data[0])
        Row(modifier = Modifier.fillMaxWidth()){
            Text(text = "All Sales Info")
        }
        CheckDetailedDescriptionItem(null)
        LazyColumn {
            items(data) {
                CheckDetailedDescriptionItem(it)
            }
        }
    }
}

@Composable
fun MainCheckData(
    data: GetCheckDetailedDescription?
){
    Row(
        modifier = Modifier.fillMaxWidth().padding(20.dp)
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = data?.chequeNumber ?: "Cheque number"
        )

        Text(
            modifier = Modifier.weight(1f),
            text = data?.idEmployee ?: "idEmployee"
        )

        Text(
            modifier = Modifier.weight(1f),
            text = data?.cardNumber ?: "Card Number"
        )

        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateString = format.format(data?.printDate ?: Date())

        Text(
            modifier = Modifier.weight(1f),
            text = dateString ?: "PrintDate"
        )

        Text(
            modifier = Modifier.weight(1f),
            text = data?.sumTotal?.toString() ?: "sumTotal"
        )

        Text(
            modifier = Modifier.weight(1f),
            text = data?.vat?.toString() ?: "vat"
        )
    }
}

@Composable
fun CheckDetailedDescriptionItem(
    data: GetCheckDetailedDescription?
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(20.dp)
    ) {


        Text(
            modifier = Modifier.weight(1f),
            text = data?.upc?: "upc"
        )
        Text(
            modifier = Modifier.weight(1f),
            text = data?.productNumber?.toString()?: "productNumber"
        )
        Text(
            modifier = Modifier.weight(1f),
            text = data?.sellingPrice?.toString()?: "sellingPrice"
        )
        Text(
            modifier = Modifier.weight(1f),
            text = data?.productName?: "productName"
        )
        Text(
            modifier = Modifier.weight(1f),
            text = data?.characteristics?: "characteristics"
        )

    }
}