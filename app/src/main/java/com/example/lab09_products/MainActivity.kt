package com.example.lab09_products

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lab09_products.ui.theme.Lab09ProductsTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.ui.res.painterResource
import androidx.compose.material.icons.filled.Facebook
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.unit.sp
import androidx.compose.material3.TextFieldDefaults


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab09ProductsTheme {
                ProgPrincipal9()
            }
        }
    }
}
@Composable
fun ProgPrincipal9() {
    val urlBase = "https://dummyjson.com/"
    val retrofit = Retrofit.Builder()
        .baseUrl(urlBase)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val navController = rememberNavController()
    val servicio = retrofit.create(ProductApiService::class.java)

    Scaffold(
        topBar = { BarraSuperior() },
        bottomBar = { BarraInferior(navController) },
        content = { paddingValues -> Contenido(paddingValues, navController, servicio) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperior() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Lista de Productos",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFF96D7EC)
        )
    )
}

@Composable
fun BarraInferior(navController: NavHostController) {
    NavigationBar(containerColor = Color(0xFF96D7EC)) { // Color celeste
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Home, contentDescription = "Inicio", tint = Color.DarkGray) }, // Icono negro oscuro
            label = { Text("Inicio") },
            selected = navController.currentDestination?.route == "inicio",
            onClick = { navController.navigate("inicio") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.ShoppingCart, contentDescription = "Productos", tint = Color.DarkGray) }, // Icono negro oscuro
            label = { Text("Productos") },
            selected = navController.currentDestination?.route == "productos",
            onClick = { navController.navigate("productos") }
        )
    }
}


@Composable
fun Contenido(
    pv: PaddingValues,
    navController: NavHostController,
    servicio: ProductApiService
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(pv)
            .background(Color.White)
    ) {
        NavHost(navController = navController, startDestination = "inicio") {
            composable("inicio") { ScreenInicio(navController) }
            composable("productos") { ScreenProducts(navController, servicio) }
            composable("productoVer/{id}", arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )) { backStackEntry ->
                ScreenProductDetail(navController, servicio, backStackEntry.arguments?.getInt("id") ?: 0)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenInicio(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Barra de búsqueda
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Buscar productos", color = Color.Black) },
                leadingIcon = {
                    Icon(Icons.Filled.Search, contentDescription = "Buscar", tint = Color.Black)
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Gray,
                ),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )

            // titulo
            Text(
                text = "Bienvenido a la Tienda",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            //descripcion
            Text(
                text = "Explora nuestra variedad de productos y encuentra lo que necesitas.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            //imagne
            Image(
                painter = painterResource(id = R.drawable.mi_tiendita),
                contentDescription = "Imagen de productos",
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 16.dp)
            )

            //boton
            Button(
                onClick = { navController.navigate("productos") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF000000)),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Ver Productos", color = MaterialTheme.colorScheme.onSecondary)
            }

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            // categorías
            Text("Categorías", style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp), fontWeight = FontWeight.Bold)
            Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(top = 8.dp)) {
                Text("• Electrónica", fontSize = 18.sp)
                Text("• Ropa", fontSize = 18.sp)
                Text("• Hogar", fontSize = 18.sp)
                Text("• Juguetes", fontSize = 18.sp)
            }

            // iconos de redes sociales y número
            Spacer(modifier = Modifier.height(24.dp))
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = { /*  */ }) {
                    Icon(Icons.Filled.Facebook, contentDescription = "Facebook")
                }
                Text("Mi Tiendita", modifier = Modifier.align(Alignment.CenterVertically))

                IconButton(onClick = { /* */ }) {
                    Icon(Icons.Filled.PhoneIphone, contentDescription = "Teléfono")
                }
                Text("968-521-047", modifier = Modifier.align(Alignment.CenterVertically))
            }
        }
    }
}




