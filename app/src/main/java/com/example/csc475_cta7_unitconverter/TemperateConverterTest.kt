package com.example.csc475_cta7_unitconverter

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import com.example.csc475_cta7_unitconverter.ui.theme.CSC475_CTA7_UnitConverterTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


private fun MainActivity.TempConverterScreen(navController: NavHostController) {}

@RunWith(RobolectricTestRunner::class)
class TemperateConverterTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    //Fahrenheit to Celsius
    @Test
    fun convertFahrenheitToCelsius() {
        composeTestRule.setContent {
            TempConverterScreen(navController: rememberNavController)
        }

        //User input value
        composeTestRule
            .onNodeWithTag("Temperature Input")
            .performTextInput("212")

        //Open drop-down
        composeTestRule
            .onNodeWithText("Fahrenheit")
            .performClick()

        //Select Temp
        composeTestRule
            .onNodeWithText("Celsius")
            .performClick()

        //Convert
        composeTestRule
            .onNodewithTag("Convert Button")
            .performClick()

        //Check ressult
        composeTestRule
            .onNodeWithTag("Result Text")
            .assertEsists()
            .assertTextContains("C: 100.0")
    }

    //Fahrenheit to Kelvin
    @Test
    fun convertFahrenheitToKelvin() {
        composeTestRule.setContent {
            TempConverterScreen(navController: rememberNavController)
        }

        composeTestRule
            .onNodeWithTag("Temperature Input")
            .performTextInput("0")

        composeTestRule
            .onNodeWithText("Fahrenheit")
            .performClick()

        composeTestRule
            .onNodeWithText("Kelvin")
            .performClick()

        composeTestRule
            .onNodewithTag("Convert Button")
            .performClick()

        composeTestRule
            .onNodeWithTag("Result Text")
            .assertEsists()
            .assertTextContains("K: 255.37")
    }

    //Celsius To Fahrenheit
    @Test
    fun convertCelsiusToFahrenheit() {
        composeTestRule.setContent {
            TempConverterScreen(navController: rememberNavController)
        }

        composeTestRule
            .onNodeWithTag("Temperature Input")
            .performTextInput("0")

        composeTestRule
            .onNodeWithText("Celsius")
            .performClick()

        composeTestRule
            .onNodeWithText("Fahrenheit")
            .performClick()

        composeTestRule
            .onNodewithTag("Convert Button")
            .performClick()

        composeTestRule
            .onNodeWithTag("Result Text")
            .assertEsists()
            .assertTextContains("F: 32.0")
    }

    //Celsius To Kelvin
    @Test
    fun convertCelsiusToKelvin() {
        composeTestRule.setContent {
            TempConverterScreen(navController: rememberNavController)
        }

        composeTestRule
            .onNodeWithTag("Temperature Input")
            .performTextInput("0")

        composeTestRule
            .onNodeWithText("Celsius")
            .performClick()

        composeTestRule
            .onNodeWithText("Kelvin")
            .performClick()

        composeTestRule
            .onNodewithTag("Convert Button")
            .performClick()

        composeTestRule
            .onNodeWithTag("Result Text")
            .assertEsists()
            .assertTextContains("K: 273.15")
    }

    //Kelvin to Fahrenheit
    @Test
    fun convertKelvinToFahrenheit() {
        composeTestRule.setContent {
            TempConverterScreen(navController: rememberNavController)
        }

        composeTestRule
            .onNodeWithTag("Temperature Input")
            .performTextInput("200")

        composeTestRule
            .onNodeWithText("Kelvin")
            .performClick()

        composeTestRule
            .onNodeWithText("Fahrenheit")
            .performClick()

        composeTestRule
            .onNodewithTag("Convert Button")
            .performClick()

        composeTestRule
            .onNodeWithTag("Result Text")
            .assertEsists()
            .assertTextContains("F: -99.67")
    }

    //Kelvin to Celsius
    @Test
    fun convertKelvinToCelsius() {
        composeTestRule.setContent {
            TempConverterScreen(navController: rememberNavController)
        }

        composeTestRule
            .onNodeWithTag("Temperature Input")
            .performTextInput("273.15")

        composeTestRule
            .onNodeWithText("Kelvin")
            .performClick()

        composeTestRule
            .onNodeWithText("Celsius")
            .performClick()

        composeTestRule
            .onNodewithTag("Convert Button")
            .performClick()

        composeTestRule
            .onNodeWithTag("Result Text")
            .assertEsists()
            .assertTextContains("K: 0")
    }
}
