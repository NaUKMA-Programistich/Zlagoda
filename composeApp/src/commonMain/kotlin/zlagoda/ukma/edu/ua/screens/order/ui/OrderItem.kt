package zlagoda.ukma.edu.ua.screens.order.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import zlagoda.ukma.edu.ua.db.GetChequesData
import zlagoda.ukma.edu.ua.screens.employee.viewmodel.EmployeeEvent
import zlagoda.ukma.edu.ua.screens.order.viewmodel.OrderEvent
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun OrderCard(user: Employee, getChequesDataList : List<GetChequesData>, onEvent: (OrderEvent) -> Unit){
    Column (modifier = Modifier.size(550.dp,370.dp).padding(12.dp).clip(RoundedCornerShape(18.dp)).background(
        MaterialTheme.colors.onSecondary),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ){


        Column(modifier = Modifier.weight(3f).padding(6.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally){

            val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val dateString = format.format(getChequesDataList[0].printDate)

            Column(modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(modifier = Modifier.padding(4.dp),text = "Cheque: $dateString", fontSize = 20.sp , fontWeight = FontWeight.Medium)
                Text(modifier = Modifier.padding(8.dp),text = getChequesDataList[0].chequeNumber , fontSize = 16.sp , fontWeight = FontWeight.Medium)
                Text(modifier = Modifier.padding(8.dp),text = "Total price: ${getChequesDataList.sumOf { it.sellingPrice }}" , fontSize = 16.sp , fontWeight = FontWeight.Medium)
            }


            Column(modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(modifier = Modifier.padding(6.dp), text = "Employee: " + getChequesDataList[0].empl_name + " " + getChequesDataList[0].empl_surname, fontSize = 17.sp , fontWeight = FontWeight.Medium)
            }


            LazyColumn(modifier = Modifier.weight(1f)) {
                item{
                    Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically){
                        Text(modifier = Modifier.weight(1f).padding(4.dp),text = "Name" , fontSize = 16.sp , fontWeight = FontWeight.Medium)
                        Text(modifier = Modifier.weight(1f).padding(4.dp),text = "Amount" , fontSize = 16.sp , fontWeight = FontWeight.Medium)
                        Text(modifier = Modifier.weight(1f).padding(4.dp),text = "Price" , fontSize = 16.sp , fontWeight = FontWeight.Medium)
                        Text(modifier = Modifier.weight(1f).padding(4.dp),text = "Description" , fontSize = 16.sp , fontWeight = FontWeight.Medium)
                    }

                }

                items(getChequesDataList) { data ->
                    Divider(modifier = Modifier.fillMaxWidth())
                     Row(modifier = Modifier.fillMaxWidth().padding(8.dp),
                     verticalAlignment = Alignment.CenterVertically,
                     horizontalArrangement = Arrangement.SpaceBetween){
                         Text(modifier = Modifier.weight(1f).padding(8.dp),text = data.productName , fontSize = 15.sp , fontWeight = FontWeight.Medium)
                         Text(modifier = Modifier.weight(1f).padding(8.dp),text = data.productNumber.toString() , fontSize = 15.sp , fontWeight = FontWeight.Medium)
                         Text(modifier = Modifier.weight(1f).padding(8.dp),text = data.sellingPrice.toString()  , fontSize = 15.sp , fontWeight = FontWeight.Medium)
                         Text(modifier = Modifier.weight(1f).padding(8.dp),text = data.characteristics , fontSize = 15.sp , fontWeight = FontWeight.Medium)
                     }

                }
            }


        }
        if (user.isManager()) {
            Row(
                modifier = Modifier.weight(0.8f).width(220.dp).padding(15.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onEvent(OrderEvent.DeleteOrder(getChequesDataList[0].chequeNumber)) },
                    modifier = Modifier.background(color = delete_button_color, shape = CircleShape).size(55.dp)
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete cheque")
                }
            }
        }
    }
}

