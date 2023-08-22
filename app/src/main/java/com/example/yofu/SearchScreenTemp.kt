package com.example.yofu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yofu.accountUI.JobCard
import com.example.yofu.accountUI.jobCardEmployer

@Composable
fun SearchScreen(
    navController: NavController,
    searchViewModel: SearchScreenTempViewModel = viewModel<SearchScreenTempViewModel>()
) {
    val vacancies = searchViewModel.vacancies.collectAsState().value
    val searchText by searchViewModel.searchText.collectAsState()
    val isSearching by searchViewModel.isSearching.collectAsState()

   Column(
       modifier = Modifier
           .padding(12.dp)
   ) {
       Row(
           modifier = Modifier
               .clickable {
                   navController.navigate(Screen.Search.name)
               }
       ) {
           Icon(
               imageVector = Icons.Default.Search,
               contentDescription = "Icon",
               tint = Color.Gray
           )
           Spacer(Modifier.width(15.dp))
//           TextField(
//               value = searchText,
//               onValueChange = searchViewModel::onSearchTextChange,
//               modifier = Modifier
//                   .fillMaxWidth(),
//               fontFamily = normalFont,
//               style = TextStyle(
//                   fontSize = 15.sp,
//                   fontWeight = FontWeight.Normal,
//                   fontStyle = FontStyle.Normal,
//               ),
//               placeholder = {
//                   Text(
//                       text = "Searching..."
//                   )
//               }
//           )
           TextField(
               value = searchText,
               onValueChange = searchViewModel::onSearchTextChange,
               modifier = Modifier.fillMaxWidth(),
               placeholder = {
                   Text(
                       text = "Search"
                   )
               }
           )
       }

       Spacer(modifier = Modifier.height(16.dp))

       LazyColumn(
           modifier = Modifier
               .fillMaxWidth()
               .background(Color(0xFFF6F7F9))
       ) {
           items(vacancies) { vacancy ->
               Row(
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(10.dp, 0.dp, 10.dp, 0.dp)
               ) {
                   JobCard(content = vacancy, navController = navController)
               }
           }
       }
   }

}
