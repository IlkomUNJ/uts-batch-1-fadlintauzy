package com.example.contact

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.contact.ui.theme.ContactTheme

data class Contact(
    val id: Int,
    val name: String,
    val phoneNumber: String,
    val adress: String,
    val email: String
)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactTheme {
                ContactApp()
            }
        }
    }

    companion object {
        private var nextId = 2

        val contactList = mutableStateListOf(
            Contact(1, "Alice", "0812", "Jakarta", "alice@gmail.com")
        )

        fun saveContact(id: Int, name: String, phoneNumber: String, adress: String, email: String) {
            if (id == 0) {
                val newContact = Contact(
                    id = nextId++,
                    name = name,
                    phoneNumber = phoneNumber,
                    adress = adress,
                    email = email
                )
                contactList.add(newContact)
            } else {
                val index = contactList.indexOfFirst { it.id == id }
                if (index != -1) {
                    val updatedContact = contactList[index].copy(
                        name = name,
                        phoneNumber = phoneNumber,
                        adress = adress,
                        email = email
                    )
                    contactList[index] = updatedContact
                }
            }
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {

    Scaffold(
        topBar = { TopAppBar(title = { Text("Dashboard") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("edit_contact/0") }) {
                Icon(Icons.Filled.Add, contentDescription = "Add New Contact")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 8.dp)
        ) {
            items(MainActivity.contactList.sortedBy { it.name }) { contact ->
                ContactListItem(contact = contact, navController = navController)
            }
        }
    }
}

@Composable
fun ContactListItem(contact: Contact, navController: NavController) {
    val customDuration = 1500L
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .combinedClickable(onLongClick = {navController.navigate("edit_contact/${contact.id}")}) {

            }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
                Text(
                    text = "${contact.name}, ${contact.adress}",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1
                )


        }
    }
}


