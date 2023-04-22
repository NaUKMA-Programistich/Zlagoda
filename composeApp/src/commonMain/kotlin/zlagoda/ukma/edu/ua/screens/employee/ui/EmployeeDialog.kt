package zlagoda.ukma.edu.ua.screens.employee.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zlagoda.ukma.edu.ua.db.Category
import zlagoda.ukma.edu.ua.db.Employee
import zlagoda.ukma.edu.ua.screens.category.viewmodel.CategoryEvent
import zlagoda.ukma.edu.ua.screens.employee.viewmodel.EmployeeEvent
import java.util.UUID

@Composable
fun EmployeeItem (
    employee: Employee = Employee("", "","","","",0.0,"","","","","",""),
    onCloseClick: () -> Unit,
    onEvent:  (EmployeeEvent) -> Unit
){


    val empl_nameState = remember { mutableStateOf(employee.empl_name) }
    val empl_surnameState = remember { mutableStateOf(employee. empl_surname) }
    val empl_patronymicState = remember { mutableStateOf("") }
    if (employee.empl_patronymic!=null)
        empl_patronymicState.value = employee.empl_patronymic
    val empl_roleState = remember { mutableStateOf(employee.empl_role) }
    val salaryState = remember { mutableStateOf(employee.salary) }
    val date_of_birthState = remember { mutableStateOf(employee.date_of_birth) }
    val date_of_startState = remember { mutableStateOf(employee.date_of_start) }
    val phone_numberState = remember { mutableStateOf(employee.phone_number) }
    val cityState = remember { mutableStateOf(employee. city) }
    val streetState = remember { mutableStateOf(employee.street) }
    val zip_codeState = remember { mutableStateOf(employee.zip_code) }



    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Employee: ${if (employee.id_of_employee != "") employee.empl_name else "New"}",
                style = MaterialTheme.typography.h5
            )
            IconButton(onClick = { onCloseClick() }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close dialog")
            }
        }
        LazyColumn(modifier = Modifier.weight(1f)) {
            item {
                OutlinedTextField(
                    value = empl_nameState.value,
                    onValueChange = { empl_nameState.value = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth().padding(5.dp)
                )
                OutlinedTextField(
                    value = empl_surnameState.value,
                    onValueChange = { empl_surnameState.value = it },
                    label = { Text("Surname") },
                    modifier = Modifier.fillMaxWidth().padding(5.dp)
                )
                OutlinedTextField(
                    value = empl_patronymicState.value,
                    onValueChange = { empl_patronymicState.value = it },
                    label = { Text("Patronymic") },
                    modifier = Modifier.fillMaxWidth().padding(5.dp)
                )
                OutlinedTextField(
                    value = empl_roleState.value,
                    onValueChange = { empl_roleState.value = it },
                    label = { Text("Role") },
                    modifier = Modifier.fillMaxWidth().padding(5.dp)
                )
                OutlinedTextField(
                    value = salaryState.value.toString(),
                    onValueChange = { salaryState.value = it.toDouble() },
                    label = { Text("Salary") },
                    modifier = Modifier.fillMaxWidth().padding(5.dp)
                )
                OutlinedTextField(
                    value = phone_numberState.value,
                    onValueChange = { phone_numberState.value = it },
                    label = { Text("Phone") },
                    modifier = Modifier.fillMaxWidth().padding(5.dp)
                )
                OutlinedTextField(
                    value = cityState.value,
                    onValueChange = { cityState.value = it },
                    label = { Text("City") },
                    modifier = Modifier.fillMaxWidth().padding(5.dp)
                )
                OutlinedTextField(
                    value = streetState.value,
                    onValueChange = { streetState.value = it },
                    label = { Text("Street") },
                    modifier = Modifier.fillMaxWidth().padding(5.dp)
                )
                OutlinedTextField(
                    value = zip_codeState.value,
                    onValueChange = { zip_codeState.value = it },
                    label = { Text("Zip") },
                    modifier = Modifier.fillMaxWidth().padding(5.dp)
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(
                onClick = {
                    onEvent(EmployeeEvent.SaveEmployee(Employee(
                        id_of_employee = if (employee.id_of_employee != "") employee.id_of_employee else UUID.randomUUID().toString(),
                        empl_surname = empl_nameState.value,
                        empl_name = empl_surnameState.value,
                        empl_patronymic = empl_patronymicState.value,
                        empl_role = empl_roleState.value,
                        salary = salaryState.value,
                        date_of_birth = "",
                        date_of_start = "",
                        phone_number = phone_numberState.value,
                        city = cityState.value,
                        street = streetState.value,
                        zip_code = zip_codeState.value,)))
                    onCloseClick()
                },
            ) {
                Text("Save")
            }
        }
    }
}