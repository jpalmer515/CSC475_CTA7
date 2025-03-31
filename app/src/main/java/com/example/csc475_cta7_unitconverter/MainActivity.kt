package com.example.csc475_cta7_unitconverter

import android.os.Bundle
import android.widget.Button
import android.widget.MediaController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
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
        "Exit",
    )
}

@Composable
fun MainMenu(viewModel: com.example.csc475_cta7_unitconverter.MainViewModel) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "mainScreen") {
        composable("mainScreen") { MainConverterScreen(viewModel, navController) }
        composable("tempConverter") { TempConverterScreen(navController) }
        composable("weightConverter") { WeightConverterScreen(navController) }
        composable("distConverter") { DistanceConverterScreen(navController) }
        composable("volumeConverter") { VolumeConverterScreen(navController) }
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
                    }
                }
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

    var userInput by remember { mutableStateOf("") }
    var selectedUnit by remember { mutableStateOf("Fahrenheit") }
    var expanded by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf("") }

    val unitMap = mapOf(
        "Fahrenheit (°F)" to "Fahrenheit",
        "Celsius (°C)" to "Celsius",
        "Kelvin (K)" to "Kelvin"
    )

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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Enter Temperature")

            TextField(
                value = userInput,
                onValueChange = { userInput = it },
                label = { Text("Value") },
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .testTag("temperature_input")
            )

            Text("Select Unit", modifier = Modifier.padding(top = 8.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = selectedUnit,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Unit") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    unitMap.keys.forEach { displayUnit ->
                        DropdownMenuItem(
                            text = { Text(displayUnit) },
                            onClick = {
                                selectedUnit = displayUnit
                                expanded = false
                            }
                        )
                    }
                }
            }
            Button(
                onClick = {
                    val inputValue = userInput.toDoubleOrNull()
                    if (inputValue != null) {
                        result = when (selectedUnit) {
                            "Fahrenheit" -> "C: ${(inputValue - 32) * (5 / 9)}, K: ${((inputValue - 32) * (5 / 9)) + 273.15}"
                            "Celsius" -> "F: ${inputValue * (9 / 5) + 32}, K: ${inputValue + 273.15}"
                            "Kelvin" -> "F: ${(inputValue * (9 / 5) - 459.67)}, C: ${inputValue - 273.15}"
                            else -> {
                                result = "Invalid Input"
                            }
                        }.toString()
                    }
                },
                modifier = Modifier
                    .padding(8.dp)
                    .testTag("Convert Button")
            ) {
                Text("Convert")
            }
            Text(
                "Result: $result",
                modifier = Modifier
                    .padding(top = 16.dp)
                    .testTag("Result Text")
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeightConverterScreen(navController: NavController) {

    var userInput by remember { mutableStateOf("") }
    var selectedUnit by remember { mutableStateOf("Pounds") }
    var result by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Weight Converter") },
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
            Text("Enter Weight")
            TextField(
                value = userInput,
                onValueChange = { userInput = it },
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text(
                text = "Input in: $selectedUnit",
                modifier = Modifier
                    .padding(bottom = 8.dp),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )

            Row {
                listOf("Pounds", "Kilograms", "Grams").forEach { unit ->
                    Button(
                        onClick = { selectedUnit = unit },
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text(unit)
                    }
                }
            }

            Button(
                onClick = {
                    val inputValue = userInput.toDoubleOrNull()
                    if (inputValue != null) {
                        result = when (selectedUnit) {
                            "Pounds" -> "Kg: ${(inputValue / 2.2046)}, g: ${inputValue * 453.592}"
                            "Kilograms" -> "Lbs: ${inputValue * 2.2046}, g: ${inputValue * 1000}"
                            "Grams" -> "Lbs: ${inputValue * .0022046}, Kg: ${inputValue / 1000}"
                            else -> {
                                result = "Invalid Input"
                            }
                        }.toString()
                    }
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Convert")
            }
            Text(
                "Result: $result",
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DistanceConverterScreen(navController: NavController) {

    var userInput by remember { mutableStateOf("") }
    var selectedUnit by remember { mutableStateOf("Feet") }
    var result by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Distance Converter") },
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
            Text("Enter Length")
            TextField(
                value = userInput,
                onValueChange = { userInput = it },
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text(
                text = "Input in: $selectedUnit",
                modifier = Modifier
                    .padding(bottom = 8.dp),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )

            Row {
                listOf("Feet", "Meter", "Yard").forEach { unit ->
                    Button(
                        onClick = { selectedUnit = unit },
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text(unit)
                    }
                }
            }

            Button(
                onClick = {
                    val inputValue = userInput.toDoubleOrNull()
                    if (inputValue != null) {
                        result = when (selectedUnit) {
                            "Feet" -> "m: ${(inputValue * 0.3048)}, Yd: ${inputValue * 0.33}"
                            "Meters" -> "Ft: ${inputValue * 3.285}, Yd: ${inputValue * 1.0936}"
                            "Yards" -> "m: ${inputValue * 0.9144}, Ft: ${inputValue * 3}"
                            else -> {
                                result = "Invalid Input"
                            }
                        }.toString()
                    }
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Convert")
            }
            Text(
                "Result: $result",
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VolumeConverterScreen(navController: NavController) {

    var userInput by remember { mutableStateOf("") }
    var selectedUnit by remember { mutableStateOf("Cubic Feet") }
    var result by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Volume Converter") },
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
            Text("Enter Volume")
            TextField(
                value = userInput,
                onValueChange = { userInput = it },
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text(
                text = "Input in: $selectedUnit",
                modifier = Modifier
                    .padding(bottom = 8.dp),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )

            Row {
                listOf("Cubic Feet", "Cubic Meter", "Liter").forEach { unit ->
                    Button(
                        onClick = { selectedUnit = unit },
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text(unit)
                    }
                }
            }

            Button(
                onClick = {
                    val inputValue = userInput.toDoubleOrNull()
                    if (inputValue != null) {
                        result = when (selectedUnit) {
                            "Cubic Feet" -> "m^3: ${(inputValue * 0.0283)}, L: ${inputValue * 28.316}"
                            "Cubic Meters" -> "Ft^3: ${inputValue * 35.147}, L: ${inputValue * 1000}"
                            "Liters" -> "m^3: ${inputValue * 0.001}, Ft^3: ${inputValue * 0.0353}"
                            else -> {
                                result = "Invalid Input"
                            }
                        }.toString()
                    }
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Convert")
            }
            Text(
                "Result: $result",
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

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
        TempConverterScreen(rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun WeightConverterPreview() {
    CSC475_CTA7_UnitConverterTheme {
        WeightConverterScreen(rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun DistanceConverterPreview() {
    CSC475_CTA7_UnitConverterTheme {
        DistanceConverterScreen(rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun VolumeConverterPreview() {
    CSC475_CTA7_UnitConverterTheme {
        VolumeConverterScreen(rememberNavController())
    }
}

