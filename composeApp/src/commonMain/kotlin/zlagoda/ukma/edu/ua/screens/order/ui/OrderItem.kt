package zlagoda.ukma.edu.ua.screens.order.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import zlagoda.ukma.edu.ua.core.ktx.isManager
import zlagoda.ukma.edu.ua.core.ktx.toStr
import zlagoda.ukma.edu.ua.core.theme.delete_button_color
import zlagoda.ukma.edu.ua.core.theme.edit_button_color
import zlagoda.ukma.edu.ua.db.Employee
import zlagoda.ukma.edu.ua.screens.employee.viewmodel.EmployeeEvent
import zlagoda.ukma.edu.ua.screens.order.viewmodel.OrderEvent
import java.util.*

data class OrderItemData(
 val chequeNumber: String,
 val printDate: Date,
 val upc: String,
 val productNumber: Long,
 val sellingPrice: Double,
 val productName: String,
 val characteristics: String,
)


class OrderItem {


}

@Composable
fun OrderCard(user: Employee, employee: Employee, onEvent: (OrderEvent) -> Unit){
    Column (modifier = Modifier.size(370.dp,520.dp).padding(12.dp).clip(RoundedCornerShape(18.dp)).background(
        MaterialTheme.colors.onSecondary),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ){


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
                Text(modifier = Modifier.padding(6.dp), text = "Start Date: " + employee.date_of_start.toStr(), fontSize = 16.sp , fontWeight = FontWeight.Medium)
                Text(text = "Birth Date: " + employee.date_of_birth.toStr(), fontSize = 16.sp , fontWeight = FontWeight.Medium)
            }

            Column(modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(modifier = Modifier.padding(4.dp),text = employee.zip_code + " " + employee.street + " St., " + employee.city + " City", fontSize = 16.sp , fontWeight = FontWeight.Medium)
                Text(text = employee.phone_number, fontSize = 16.sp , fontWeight = FontWeight.Medium)
            }


        }
        if (user.isManager()) {
            Row(
                modifier = Modifier.weight(1.2f).width(220.dp).padding(15.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onEvent(OrderEvent.DeleteOrder("")) },
                    modifier = Modifier.background(color = delete_button_color, shape = CircleShape).size(55.dp)
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete cheque")
                }
            }
        }
    }
}

