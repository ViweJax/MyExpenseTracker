package com.example.myexpensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar

import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseTrackerApp()
        }
    }
}

data class Expense(val name: String, val amount: Double, val date: String)

@Composable
fun ExpenseTrackerApp() {
    var expenses by remember { mutableStateOf(listOf<Expense>()) }
    var expenseName by remember { mutableStateOf("") }
    var expenseAmount by remember { mutableStateOf("") }
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            TopAppBar(
                title = { Text("Expense Tracker") },
                backgroundColor = MaterialTheme.colors.primary
            )

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Add Expense", style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(16.dp))

                BasicTextField(
                    value = expenseName,
                    onValueChange = { expenseName = it },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            // Handle the "Done" button press here
                            val amount = expenseAmount.toDoubleOrNull()
                            if (!expenseName.isNullOrBlank() && amount != null) {
                                val date = dateFormat.format(Date())
                                expenses = expenses + Expense(expenseName, amount, date)
                                expenseName = ""
                                expenseAmount = ""
                            }
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                )

                Spacer(modifier = Modifier.height(8.dp))

                BasicTextField(
                    value = expenseAmount,
                    onValueChange = { expenseAmount = it },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            // Handle the "Done" button press here
                            val amount = expenseAmount.toDoubleOrNull()
                            if (!expenseName.isNullOrBlank() && amount != null) {
                                val date = dateFormat.format(Date())
                                expenses = expenses + Expense(expenseName, amount, date)
                                expenseName = ""
                                expenseAmount = ""
                            }
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(expenses) { expense ->
                    ExpenseItem(expense = expense)
                }
            }
        }
    }
}

@Composable
fun ExpenseItem(expense: Expense) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = expense.name, style = MaterialTheme.typography.h6)
            Text(text = "Amount: $${expense.amount}", style = MaterialTheme.typography.body1)
            Text(text = "Date: ${expense.date}", style = MaterialTheme.typography.caption)
        }
    }
}
