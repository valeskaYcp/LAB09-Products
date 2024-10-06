package com.example.lab09_products

import android.graphics.fonts.FontStyle
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.outlined.Info

@Composable
fun ScreenProducts(navController: NavHostController, servicio: ProductApiService) {
    val listaProductos: SnapshotStateList<ProductModel> = remember { mutableStateListOf() }
    var loading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            val response = servicio.getProducts()
            listaProductos.addAll(response.products)
        } catch (e: Exception) {
            errorMessage = e.message
        } finally {
            loading = false
        }
    }

    if (loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    } else if (errorMessage != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Error: $errorMessage")
                Spacer(modifier = Modifier.height(8.dp))
                Icon(Icons.Outlined.Info, contentDescription = "Error", modifier = Modifier.size(48.dp))
            }
        }
    } else {
        LazyColumn {
            items(listaProductos) { producto ->
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            navController.navigate("productoVer/${producto.id}")
                        }
                ) {
                    Text(text = producto.title, Modifier.weight(1f))
                    Icon(Icons.Outlined.Info, contentDescription = "Info", modifier = Modifier.size(24.dp))
                }
            }
        }
    }
}



@Composable
fun ScreenProductDetail(navController: NavHostController, servicio: ProductApiService, id: Int) {
    var producto by remember { mutableStateOf<ProductModel?>(null) }

    LaunchedEffect(Unit) {
        producto = servicio.getProductById(id)
    }

    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        producto?.let {
            item {
                //id
                Text(text = "ID", fontWeight = FontWeight.Bold)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Text(text = "${it.id}")
                }

                Spacer(modifier = Modifier.height(16.dp))

                //titulo
                Text(text = "Title", fontWeight = FontWeight.Bold)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Text(text = it.title)
                }

                Spacer(modifier = Modifier.height(16.dp))

                //descripcion
                Text(text = "Description", fontWeight = FontWeight.Bold)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Text(text = it.description)
                }

                Spacer(modifier = Modifier.height(16.dp))

                //precio
                Text(text = "Price", fontWeight = FontWeight.Bold)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Text(text = "$${it.price}")
                }

                Spacer(modifier = Modifier.height(16.dp))

                //descuento
                Text(text = "Discount", fontWeight = FontWeight.Bold)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Text(text = "${it.discountPercentage}%")
                }

                Spacer(modifier = Modifier.height(16.dp))

                //categoria
                Text(text = "Category", fontWeight = FontWeight.Bold)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Text(text = "${it.category}")
                }

                Spacer(modifier = Modifier.height(16.dp))

                //marca
                Text(text = "Brand", fontWeight = FontWeight.Bold)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Text(text = "${it.brand}")
                }

                Spacer(modifier = Modifier.height(16.dp))

                //rating
                Text(text = "Rating", fontWeight = FontWeight.Bold)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Text(text = "${it.rating} ⭐")
                }

                Spacer(modifier = Modifier.height(16.dp))

                //stock
                Text(text = "Stock", fontWeight = FontWeight.Bold)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Text(text = "${it.stock} units")
                }

                Spacer(modifier = Modifier.height(16.dp))

                //etiquetas
                Text(text = "Tags", fontWeight = FontWeight.Bold)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Text(text = it.tags.joinToString(", "))
                }
            }
        }
    }
}




