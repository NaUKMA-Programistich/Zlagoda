package zlagoda.ukma.edu.ua.screens.employee.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.useResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import zlagoda.ukma.edu.ua.core.theme.add_button_color
import zlagoda.ukma.edu.ua.core.theme.delete_button_color
import zlagoda.ukma.edu.ua.core.theme.edit_button_color
import zlagoda.ukma.edu.ua.db.Employee
import zlagoda.ukma.edu.ua.screens.employee.viewmodel.EmployeeEvent
import zlagoda.ukma.edu.ua.screens.employee.viewmodel.EmployeeState
import zlagoda.ukma.edu.ua.screens.login.viewmodel.LoginViewModel
import zlagoda.ukma.edu.ua.screens.login.viewmodel.LoginViewModel.Companion.isManager
import zlagoda.ukma.edu.ua.screens.login.viewmodel.LoginViewModel.Companion.isSeller
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.imageio.ImageIO


@Composable
fun EmployeeViewList(
    state: EmployeeState.EmployeeList,
    onEvent: (EmployeeEvent) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        val scrollState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()


        if (isSeller())
            EmployeeCard(employee = LoginViewModel.user, onEvent = onEvent)
        else {
            LazyRow(
                modifier = Modifier.fillMaxWidth().padding(15.dp).draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        coroutineScope.launch {
                            scrollState.scrollBy(-delta)
                        }
                    },
                )
            ) {
                items(state.employees) { employee ->
                    EmployeeCard(employee = employee, onEvent = onEvent)
                }

            }
        }
        if (isManager()) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = add_button_color),
                    onClick = { onEvent(EmployeeEvent.CreateNewEmployee) }
                ) {
                    Text("Add New")
                }


                var checked by remember {
                    mutableStateOf(false)
                }
                Row(
                    modifier = Modifier.padding(start = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = "Only Sellers"
                    )

                    Checkbox(
                        colors = CheckboxDefaults.colors(
                            checkedColor = add_button_color,
                            uncheckedColor = edit_button_color
                        ),
                        checked = checked,
                        onCheckedChange = { checked_ ->
                            checked = checked_
                            if (checked)
                                onEvent(EmployeeEvent.SetSellerList)
                            else
                                onEvent(EmployeeEvent.SetAllEmployeeList)
                        }
                    )
                }


                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = add_button_color),
                    onClick = { onEvent(EmployeeEvent.SearchEmployeeData) }
                ) {
                    Text("Search")
                }
            }
        }
    }
}


@Composable
fun EmployeeCard(employee: Employee, onEvent: (EmployeeEvent) -> Unit){
    Column (modifier = Modifier.size(370.dp,520.dp).padding(12.dp).clip(RoundedCornerShape(18.dp)).background(MaterialTheme.colors.onSecondary),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
    ){

        val icon = painterResource("no_icn.jpg")

        Box(modifier = Modifier.weight(2f),
        contentAlignment = Alignment.Center){
            Image(
                painter = icon,
                contentDescription = "Icon",
                modifier = Modifier.size(120.dp).clip(RoundedCornerShape(80.dp))
            )
        }

        Column(modifier = Modifier.weight(3f).width(320.dp).padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally){

            Column(modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(modifier = Modifier.padding(4.dp),text = employee.empl_name + " " + employee.empl_surname, fontSize = 22.sp , fontWeight = FontWeight.Medium)
                if (employee.empl_patronymic!=null)
                    Text(text = employee.empl_patronymic, fontSize = 22.sp , fontWeight = FontWeight.Medium)
                Text(modifier = Modifier.padding(8.dp),text = employee.empl_role + " " + employee.salary + "$", fontSize = 18.sp , fontWeight = FontWeight.Medium)
            }


            Column(modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(modifier = Modifier.padding(6.dp), text = "Start Date: " + employee.date_of_start, fontSize = 16.sp , fontWeight = FontWeight.Medium)
                Text(text = "Birth Date: " + employee.date_of_birth, fontSize = 16.sp , fontWeight = FontWeight.Medium)
            }

            Column(modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(modifier = Modifier.padding(4.dp),text = employee.zip_code + " " + employee.street + " St., " + employee.city + " City", fontSize = 16.sp , fontWeight = FontWeight.Medium)
                Text(text = employee.phone_number, fontSize = 16.sp , fontWeight = FontWeight.Medium)
            }


        }
        if (isManager()) {
            Row(
                modifier = Modifier.weight(1.2f).width(220.dp).padding(15.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onEvent(EmployeeEvent.EditEmployee(employee)) },
                    modifier = Modifier.background(color = edit_button_color, shape = CircleShape).size(55.dp)
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit category")
                }
                IconButton(
                    onClick = { onEvent(EmployeeEvent.DeleteEmployee(employee)) },
                    modifier = Modifier.background(color = delete_button_color, shape = CircleShape).size(55.dp)
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete category")
                }
            }
        }
    }
}