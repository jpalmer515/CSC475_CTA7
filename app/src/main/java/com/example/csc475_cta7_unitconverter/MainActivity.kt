package com.example.csc475_cta7_unitconverter

import android.os.Bundle
import android.widget.MediaController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.csc475_cta7_unitconverter.ui.theme.CSC475_CTA7_UnitConverterTheme
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CSC475_CTA7_UnitConverterTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                    ) {
                        MainMenu(viewModel = MainViewModel())
                    }
                }
            }
        }
    }
}

class MainViewModel : ViewModel() {
    val menuItems = listOf(
        "Temperature Converter",
        "Weight Converter",
        "Distance Converter",
        "Volume Converter",
        "Speed Converter",
        "Exit",
    )
}

@Composable
fun MainMenu(viewModel: com.example.csc475_cta7_unitconverter.MainViewModel) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "mainScreen") {
        composable("mainScreen") { MainConverterScreen(viewModel, navController) }
        composable("tempConverter") { TempConverterScreen(navController) }
//        composable("weightConverter") { WeightConverterScreen(navController) }
//        composable("distConverter") { DistanceConverterScreen(navController) }
//        composable("volumeConverter") { VolumeConverterScreen(navController) }
//        composable("speedConverter") { SpeedConverterScreen(navController) }
    }
}

//Menu Landing Page
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainConverterScreen(viewModel: MainViewModel, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Basic Unit Converter") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            viewModel.menuItems.forEach { item ->
                MenuButtons(item) {
                    when (item) {
                        "Temperature Converter" -> navController.navigate("tempConverter")
                        "Weight Converter" -> navController.navigate("weightConverter")
                        "Distance Converter" -> navController.navigate("distConverter")
                        "Volume Converter" -> navController.navigate("volumeConverter")
                        "Speed Converter" -> navController.navigate("speedConverter")
                    }
                }
            }
            MenuButtons("Account Information") {
                navController.navigate("accountInformation")
            }
        }
    }
}

//Button Setup
@Composable
fun MenuButtons(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors()
    ) {
        Text(text, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TempConverterScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Temperature Converter") },
                actions = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.Blue)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun WeightConverterScreen(navController: NavController) {
//
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DistanceConverterScreen(navController: NavController) {
//
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun VolumeConverterScreen(navController: NavController) {
//
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SpeedConverterScreen(navController: NavController) {
//
//}

//Previews of Each Converter Page
@Preview(showBackground = true)
@Composable
fun MenuPreview() {
    CSC475_CTA7_UnitConverterTheme {
        MainMenu(viewModel = MainViewModel())
    }
}

@Preview(showBackground = true)
@Composable
fun TempConverterPreview() {
    CSC475_CTA7_UnitConverterTheme {
        TempConverterScreen(viewModel = MainViewModel())
    }
}

//@Preview(showBackground = true)
//@Composable
//fun WeightConverterPreview() {
//    CSC475_CTA7_UnitConverterTheme {
//        WeightConverterScreen(viewModel = MainViewModel())
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DistanceConverterPreview() {
//    CSC475_CTA7_UnitConverterTheme {
//        DistanceConverterScreen(viewModel = MainViewModel())
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun VolumeConverterPreview() {
//    CSC475_CTA7_UnitConverterTheme {
//        VolumeConverterScreen(viewModel = MainViewModel())
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun SpeedConverterPreview() {
//    CSC475_CTA7_UnitConverterTheme {
//        SpeedConverterScreen(viewModel = MainViewModel())
//    }
//}