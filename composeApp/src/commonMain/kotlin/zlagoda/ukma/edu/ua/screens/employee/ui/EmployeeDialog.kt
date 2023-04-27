package zlagoda.ukma.edu.ua.screens.employee.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zlagoda.ukma.edu.ua.core.composable.ItemWithDropdown
import zlagoda.ukma.edu.ua.core.ktx.toDate
import zlagoda.ukma.edu.ua.core.ktx.toStr
import zlagoda.ukma.edu.ua.db.Employee
import zlagoda.ukma.edu.ua.screens.employee.viewmodel.EmployeeEvent
import zlagoda.ukma.edu.ua.screens.products.ui.toDropDownItems
import zlagoda.ukma.edu.ua.utils.authorization.Authorization
import zlagoda.ukma.edu.ua.utils.validation.isBDayValid
import zlagoda.ukma.edu.ua.utils.validation.isPhoneNumberValid
import zlagoda.ukma.edu.ua.utils.validation.isStartDateValid
import zlagoda.ukma.edu.ua.utils.validation.isZipCodeValid
import java.time.Instant
import java.util.Date
import java.util.UUID

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EmployeeItem (
    employee: Employee = Employee("", "","","","",0.0, Date(), Date(),"","","","", "", ""),
    onCloseClick: () -> Unit,
    onEvent:  (EmployeeEvent) -> Unit
){

    val empl_nameState = remember { mutableStateOf(employee.empl_name) }
    val empl_surnameState = remember { mutableStateOf(employee. empl_surname) }
    val empl_patronymicState = remember { mutableStateOf("") }
    if (employee.empl_patronymic!=null)
        empl_patronymicState.value = employee.empl_patronymic

    val employeeRoleMap = mapOf<Long, String>(0L to "Manager", 1L to "Seller")

    var empl_role_indexState by remember { mutableStateOf(if (employee.empl_role == employeeRoleMap[0]) 0L else 1L) }

    val salaryState = remember { mutableStateOf(employee.salary) }
    val date_of_birthState = remember { mutableStateOf(employee.date_of_birth.toStr()) }
    val date_of_startState = remember { mutableStateOf(employee.date_of_start.toStr()) }
    val phone_numberState = remember { mutableStateOf(employee.phone_number) }
    val cityState = remember { mutableStateOf(employee. city) }
    val streetState = remember { mutableStateOf(employee.street) }
    val zip_codeState = remember { mutableStateOf(employee.zip_code) }
    val loginState = remember { mutableStateOf(employee.login) }


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
        LazyColumn(modifier = Modifier.weight(1f).padding(15.dp)) {
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
                ItemWithDropdown(
                    modifier = Modifier.fillMaxWidth().padding(5.dp),
                    value = employeeRoleMap[empl_role_indexState]!!,
                    dropdownItems = employeeRoleMap.toDropDownItems(),
                    onItemClick = { empl_role_indexState = it.id }
                )
                OutlinedTextField(
                    value = salaryState.value.toString(),
                    onValueChange = { salaryState.value = it.toDouble() },
                    label = { Text("Salary") },
                    modifier = Modifier.fillMaxWidth().padding(5.dp)
                )
                OutlinedTextField(
                    value = date_of_birthState.value,
                    onValueChange = { date_of_birthState.value = it },
                    label = { Text("Date of birth (yyyy-mm-dd)") },
                    modifier = Modifier.fillMaxWidth().padding(5.dp)
                )
                OutlinedTextField(
                    value = date_of_startState.value,
                    onValueChange = { date_of_startState.value = it },
                    label = { Text("Date of start (yyyy-mm-dd)") },
                    modifier = Modifier.fillMaxWidth().padding(5.dp)
                )
                OutlinedTextField(
                    value = phone_numberState.value,
                    onValueChange = { phone_numberState.value = it },
                    label = { Text("Phone (length with + <=13)") },
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
                OutlinedTextField(
                    value = loginState.value,
                    onValueChange = { loginState.value = it },
                    label = { Text("Login") },
                    modifier = Modifier.fillMaxWidth().padding(5.dp)
                )
            }
        }




        Column(modifier = Modifier.fillMaxWidth().weight(0.3f), verticalArrangement = Arrangement.SpaceAround, horizontalAlignment = Alignment.CenterHorizontally) {

            if (!date_of_birthState.value.toDate().isBDayValid())
                Text("Wrong BDate format")
            else if (!date_of_startState.value.toDate().isStartDateValid())
                Text("Wrong Date of start format")
            else if (!phone_numberState.value.isPhoneNumberValid())
                Text("Wrong phone format")
            else if (salaryState.value < 0)
                Text("Salary cant be less then 0")
            else if (!zip_codeState.value.isZipCodeValid())
                Text("Zip code must consist of numbers")
            else
                Text("")


            Button(
                modifier = Modifier.padding(15.dp),
                onClick = {
                    if (isValidEmployeeForm(
                            date_of_birthState,
                            date_of_startState,
                            phone_numberState,
                            salaryState,
                            zip_codeState
                        )
                    ) {
                        onEvent(
                            EmployeeEvent.SaveEmployee(
                                Employee(
                                    id_of_employee = employee.id_of_employee.ifBlank { UUID.randomUUID().toString() },
                                    empl_surname = empl_surnameState.value,
                                    empl_name = empl_nameState.value,
                                    empl_patronymic = empl_patronymicState.value,
                                    empl_role = employeeRoleMap[empl_role_indexState]!!,
                                    salary = salaryState.value,
                                    date_of_birth = date_of_birthState.value.toDate()!!,
                                    date_of_start = date_of_startState.value.toDate()!!,
                                    phone_number = phone_numberState.value,
                                    city = cityState.value,
                                    street = streetState.value,
                                    zip_code = zip_codeState.value,
                                    login = loginState.value,
                                    password = employee.password
                                )
                            )
                        )
                        onCloseClick()
                    }
                },
            ) {
                Text("Save")
            }
        }
    }
}

fun isValidEmployeeForm(
    date_of_birthState: MutableState<String>,
    date_of_startState: MutableState<String>,
    phone_numberState: MutableState<String>,
    salaryState: MutableState<Double>,
    zip_codeState: MutableState<String>
) : Boolean {
    return (date_of_birthState.value.toDate().isBDayValid() && date_of_startState.value.toDate().isStartDateValid()
            && phone_numberState.value.isPhoneNumberValid() && salaryState.value > 0 && zip_codeState.value.isZipCodeValid())


}

