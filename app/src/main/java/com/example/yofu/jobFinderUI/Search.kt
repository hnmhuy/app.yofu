package com.example.yofu.jobFinderUI

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yofu.accountUI.JobCard

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(
    navController: NavController,
    searchViewModel: SearchViewModel = viewModel<SearchViewModel>()
) {
    var textValue by remember {
        mutableStateOf("")
    }
    var active by remember{
        mutableStateOf(false
        )
    }
    var items = remember{
        mutableStateListOf(
            "banana",
            "ananas"
        )
    }

    val vacancies = searchViewModel.vacancies.collectAsState().value
    val searchText by searchViewModel.searchText.collectAsState()
    val isSearching by searchViewModel.isSearching.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
                            .background(
                            color = Color(0xFFF6F7F9)
        )
    ) {
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            query = textValue,
            onQueryChange = {

                textValue = it
            },
            onSearch = {
                if (textValue.isNotEmpty()) {
                    items.add(0, textValue)

                    searchViewModel.onSearchTextChange(textValue)
                }
                active = false
            },
            active = active,
            onActiveChange = {
                active = it
            },
            placeholder = {
                Text(text = "Search")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search icon"
                )
            },
            trailingIcon = {
                if (active) {
                    Icon(
                        modifier = Modifier.clickable {
                            if (textValue.isNotEmpty()) {
                                textValue = ""
                            } else {
                                active = false
                            }
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = "Search icon"
                    )
                }
            }
        ) {
            items.forEach {
                if (items.isNotEmpty()) {
                    Row() {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = "Search history"
                        )
                        Spacer(
                            modifier = Modifier.width(
                                10.dp
                            )
                        )
                        Text(text = it)
                    }
                }
            }
        }
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