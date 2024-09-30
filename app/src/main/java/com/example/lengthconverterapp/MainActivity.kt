package com.example.lengthconverterapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lengthconverterapp.ui.theme.LengthConverterAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LengthConverterAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LengthUnitConverter(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun LengthUnitConverter(modifier: Modifier = Modifier) {
    var inputValue by remember { mutableStateOf("") }
    val conversionFactor = remember { mutableDoubleStateOf(1.00) }
    val outputConversionFactor = remember { mutableDoubleStateOf(1.00) }
    var outputValue by remember { mutableStateOf("0") }
    var inputError by remember { mutableStateOf(false) }
    var inputUnit by remember { mutableStateOf("Meters") }
    var inputUnitSmall by remember { mutableStateOf("m") }
    var iExpanded by remember { mutableStateOf(false) }
    var oExpanded by remember { mutableStateOf(false) }
    var outputUnit by remember { mutableStateOf("Meters") }

    fun convertUnits() {
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        val result =
            (inputValueDouble * conversionFactor.doubleValue / outputConversionFactor.doubleValue)
        outputValue = result.toString()
        inputError = inputValue.toDoubleOrNull() == null && inputValue.isNotEmpty()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(Color.White, shape = MaterialTheme.shapes.medium)
                .border(2.dp, Color.Black, shape = MaterialTheme.shapes.medium)
        ) {
            Image(
                painter = painterResource(id = R.drawable.appicon),
                contentDescription = "App Icon",
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = " Length Unit Converter",
            modifier = Modifier.padding(bottom = 32.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge

        )

        Spacer(modifier = Modifier.height(16.dp))

        //Input Field
        OutlinedTextField(
            value = inputValue,
            onValueChange = {
                inputValue = it
                convertUnits()
            },
            label = { Text("Enter length in $inputUnit") },
            trailingIcon = {
                if (inputError) {
                    Icon(Icons.Default.Warning, contentDescription = "Warning")
                }
            },
            suffix = { Text(inputUnitSmall) },
            supportingText = {
                if (inputError) {
                    Text(text = "Please enter a valid number")
                }
            },
            isError = inputError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            //Input Unit
            Box {
                //Input Button
                Button(onClick = { iExpanded = true }) {
                    Text(text = inputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
                }
                DropdownMenu(expanded = iExpanded, onDismissRequest = { iExpanded = false }) {
                    DropdownMenuItem(text = { Text("Millimeters") }, onClick = {
                        iExpanded = false
                        inputUnit = "Millimeters"
                        inputUnitSmall = "mm"
                        conversionFactor.doubleValue = 0.001
                        convertUnits()
                    })
                    DropdownMenuItem(text = { Text("Centimeters") }, onClick = {
                        iExpanded = false
                        inputUnit = "Centimeters"
                        inputUnitSmall = "cm"
                        conversionFactor.doubleValue = 0.01
                        convertUnits()
                    })
                    DropdownMenuItem(text = { Text("Meters") }, onClick = {
                        iExpanded = false
                        inputUnit = "Meters"
                        inputUnitSmall = "m"
                        conversionFactor.doubleValue = 1.0
                        convertUnits()
                    })
                    DropdownMenuItem(text = { Text("Kilometers") }, onClick = {
                        iExpanded = false
                        inputUnit = "Kilometers"
                        inputUnitSmall = "km"
                        conversionFactor.doubleValue = 1000.0
                        convertUnits()
                    })
                    DropdownMenuItem(text = { Text("Inches") }, onClick = {
                        iExpanded = false
                        inputUnit = "Inches"
                        inputUnitSmall = "in"
                        conversionFactor.doubleValue = 0.0254
                        convertUnits()
                    })
                    DropdownMenuItem(text = { Text("Feet") }, onClick = {
                        iExpanded = false
                        inputUnit = "Feet"
                        inputUnitSmall = "ft"
                        conversionFactor.doubleValue = 0.3048
                        convertUnits()
                    })
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Box {
                Text(
                    text = "to",
                    modifier = Modifier.padding(top = 6.dp),
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            //Output Unit
            Box {
                //Output Button
                Button(onClick = { oExpanded = true }) {
                    Text(text = outputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
                }
                DropdownMenu(expanded = oExpanded, onDismissRequest = { oExpanded = false }) {
                    DropdownMenuItem(text = { Text("Millimeters") }, onClick = {
                        oExpanded = false
                        outputUnit = "Millimeters"
                        outputConversionFactor.doubleValue = 0.001
                        convertUnits()
                    })
                    DropdownMenuItem(text = { Text("Centimeters") }, onClick = {
                        oExpanded = false
                        outputUnit = "Centimeters"
                        outputConversionFactor.doubleValue = 0.01
                        convertUnits()
                    })
                    DropdownMenuItem(text = { Text("Meters") }, onClick = {
                        oExpanded = false
                        outputUnit = "Meters"
                        outputConversionFactor.doubleValue = 1.0
                        convertUnits()
                    })
                    DropdownMenuItem(text = { Text("Kilometers") }, onClick = {
                        oExpanded = false
                        outputUnit = "Kilometers"
                        outputConversionFactor.doubleValue = 1000.0
                        convertUnits()
                    })
                    DropdownMenuItem(text = { Text("Inches") }, onClick = {
                        oExpanded = false
                        outputUnit = "Inches"
                        outputConversionFactor.doubleValue = 0.0254
                        convertUnits()
                    })
                    DropdownMenuItem(text = { Text("Feet") }, onClick = {
                        oExpanded = false
                        outputUnit = "Feet"
                        outputConversionFactor.doubleValue = 0.3048
                        convertUnits()
                    })
                }
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        //Result Text
        Text(
            text = "Result: $outputValue $outputUnit",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall
        )
    }

}

@Preview(showBackground = true)
@Composable
fun LengthConvertPreview() {
    LengthConverterAppTheme {
        LengthUnitConverter()
    }
}