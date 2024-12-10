package com.myjar.jarassignment.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.myjar.jarassignment.data.model.ComputerItem
import com.myjar.jarassignment.ui.vm.JarViewModel

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    viewModel: JarViewModel,
) {
    val navController = rememberNavController()
    val navigate = remember { mutableStateOf<String>("") }

    NavHost(modifier = modifier, navController = navController, startDestination = "item_list") {
        composable("item_list") {
            ItemListScreen(
                viewModel = viewModel,
                onNavigateToDetail = { selectedItem -> navigate.value = selectedItem },
                navigate = navigate,
                navController = navController
            )
        }
        composable("item_detail/{itemId}") { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId")
            ItemDetailScreen(itemId = itemId)
        }
    }
}

@Composable
fun ItemListScreen(
    viewModel: JarViewModel,
    onNavigateToDetail: (String) -> Unit,
    navigate: MutableState<String>,
    navController: NavHostController
) {
    val items = viewModel.listStringData.collectAsState()

    if (navigate.value.isNotBlank()) {
        val currRoute = navController.currentDestination?.route.orEmpty()
        if (!currRoute.contains("item_detail")) {
            navController.navigate("item_detail/${navigate.value}")
        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(items.value) { item ->
            ItemCard(
                item = item,
                onClick = { onNavigateToDetail(item.id) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ItemCard(item: ComputerItem, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Text(text = item.name, fontWeight = FontWeight.Bold, color = Color.White)
        if(item.data!=null){
            if (item.data.color !=null) {
                Text(text = "Color: ${item.data.color}", color = Color.White)
            }
            if (item.data.capacity !=null) {
                Text(text = "Capacity: ${item.data.capacity}", color = Color.White)
            }
            if (item.data.price !=null) {
                Text(text = "Price: ${item.data.price}", color = Color.White)
            }
            if (item.data.capacityGB !=null) {
                Text(text = "Capacity GB: ${item.data.capacityGB}", color = Color.White)
            }
            if (item.data.screenSize !=null) {
                Text(text = "ScreenSize: ${item.data.screenSize}", color = Color.White)
            }
            if (item.data.description !=null) {
                Text(text = "Description: ${item.data.description}", color = Color.White)
            }
            if (item.data.generation !=null) {
                Text(text = "Generation: ${item.data.generation}", color = Color.White)
            }
            if (item.data.strapColour !=null) {
                Text(text = "Strap Color: ${item.data.strapColour}", color = Color.White)
            }
            if (item.data.caseSize !=null) {
                Text(text = "Case Size: ${item.data.caseSize}", color = Color.White)
            }
            if (item.data.cpuModel !=null) {
                Text(text = "CPU Model: ${item.data.cpuModel}", color = Color.White)
            }
            if (item.data.hardDiskSize !=null) {
                Text(text = "HardDisk Size: ${item.data.hardDiskSize}", color = Color.White)
            }
        }
    }
}


@Composable
fun ItemDetailScreen(itemId: String?) {
    // Fetch the item details based on the itemId
    // Here, you can fetch it from the ViewModel or repository
    Text(
        text = "Item Details for ID: $itemId",
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
}
