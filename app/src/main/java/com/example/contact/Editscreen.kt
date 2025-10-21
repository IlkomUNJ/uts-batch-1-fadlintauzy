package com.example.contact

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(contactId: Int, navController: NavController) {

    val minAdressLength = 5

    val existingContact = MainActivity.contactList.find { it.id == contactId }
    val titleText = if (contactId == 0) "Add New Contact" else "Edit ${existingContact?.name ?: "Contact"}"

    var name by remember { mutableStateOf(existingContact?.name ?: "") }
    var phone by remember { mutableStateOf(existingContact?.phoneNumber ?: "") }
    var adress by remember { mutableStateOf(existingContact?.adress ?: "") }
    var email by remember { mutableStateOf(existingContact?.email ?: "") }

    var addressError by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = { TopAppBar(title = { Text(titleText) }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = adress,
                onValueChange = {
                    adress = it
                    addressError = null
                },
                label = { Text("Address") },
                isError = addressError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (addressError != null) {
                Text(
                    text = addressError!!,
                    color = Color.Red,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val currentLength = adress.length

                    if (currentLength < minAdressLength) {
                        addressError = "Address must be at least $minAdressLength characters long."
                        return@Button
                    }

                    addressError = null

                    MainActivity.saveContact(
                        id = contactId,
                        name = name,
                        phoneNumber = phone,
                        adress = adress,
                        email = email
                    )

                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank() && addressError == null
            ) {
                Text(if (contactId == 0) "Add Contact" else "Save Changes")
            }
        }
    }
}