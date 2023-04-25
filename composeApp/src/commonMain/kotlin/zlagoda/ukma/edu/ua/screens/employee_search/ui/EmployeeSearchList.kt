package zlagoda.ukma.edu.ua.screens.employee_search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import zlagoda.ukma.edu.ua.db.EmployeeSearchData


@Composable
internal fun EmployeeSearchList(
    employeeData: List<EmployeeSearchData>,
    onSearch: (String) -> Unit
) {
    var employeeSurname by remember { mutableStateOf("") }

    LaunchedEffect(employeeSurname) {
        onSearch(employeeSurname)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = employeeSurname,
            onValueChange = { employeeSurname = it },
            label = { Text("Search EmployeeData by SurName") },
            modifier = Modifier.padding(5.dp)
        )
    }
    LazyColumn {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .background(color = MaterialTheme.colors.onSecondary, shape = RoundedCornerShape(20.dp))
                    .padding(vertical = 10.dp, horizontal = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Phone Number",
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    fontSize = 19.sp
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = "City",
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    fontSize = 19.sp
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Street",
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    fontSize = 19.sp
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Zip code",
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    fontSize = 19.sp
                )
            }
        }
        items(employeeData) { data ->
            EmployeeDataSearchItem(
                data = data,
            )
        }
    }
}

@Composable
internal fun EmployeeDataSearchItem(
    data: EmployeeSearchData,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .background(color = MaterialTheme.colors.onSecondary, shape = RoundedCornerShape(20.dp))
            .padding(vertical = 10.dp, horizontal = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = data.phone_number
        )
        Text(
            modifier = Modifier.weight(1f),
            text = data.city
        )
        Text(
            modifier = Modifier.weight(1f),
            text = data.street
        )
        Text(
            modifier = Modifier.weight(1f),
            text = data.zip_code
        )
    }
}