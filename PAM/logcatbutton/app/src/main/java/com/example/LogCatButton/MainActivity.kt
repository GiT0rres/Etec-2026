package com.example.logcatbutton

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.logcatbutton.ui.theme.*
import com.example.logcatbutton.R

const val TAG = "TesteAndroid"

val RosaClaro = Color(0xFFFFE4EC)
val RosaMedio = Color(0xFFFF80AB)
val RosaForte = Color(0xFFE91E63)
val VermelhoLaco = Color(0xFFD32F2F)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LogcatbuttonTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("dados", Context.MODE_PRIVATE)

    var nome by remember {
        mutableStateOf(prefs.getString("nome", "") ?: "")
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = RosaClaro
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(R.drawable.hellokitty),
                contentDescription = "Hello Kitty",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(180.dp)
                    .height(120.dp)
            )

            Text(
                text = "Sistema de Notas",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = RosaForte
            )

            Text(
                text = "por Giovanna Torres",
                style = MaterialTheme.typography.bodyMedium,
                color = RosaMedio
            )

            TextField(
                value = nome,
                onValueChange = { novoNome ->
                    nome = novoNome
                    prefs.edit().putString("nome", nome).apply()
                },
                label = { Text("Digite seu nome") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = RosaForte,
                    cursorColor = RosaForte
                ),
                modifier = Modifier.fillMaxWidth(0.85f)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                ActionButton("Insuficiente", ErrorButtonColors()) {
                    val nomeFinal = if (nome.isBlank()) "Sem nome" else nome
                    Log.e(TAG, "$nomeFinal tirou I")
                }

                ActionButton("Regular", WarningButtonColors()) {
                    val nomeFinal = if (nome.isBlank()) "Sem nome" else nome
                    Log.w(TAG, "$nomeFinal tirou R")
                }

                ActionButton("Bom", DebugButtonColors()) {
                    val nomeFinal = if (nome.isBlank()) "Sem nome" else nome
                    Log.d(TAG, "$nomeFinal tirou B")
                }

                ActionButton("Muito Bom", InfoButtonColors()) {
                    val nomeFinal = if (nome.isBlank()) "Sem nome" else nome
                    Log.i(TAG, "$nomeFinal tirou MB")
                }
            }
        }
    }
}

@Composable
fun ErrorButtonColors(): ButtonColors = ButtonDefaults.buttonColors(
    containerColor = VermelhoLaco,
    contentColor = Color.White
)

@Composable
fun WarningButtonColors(): ButtonColors = ButtonDefaults.buttonColors(
    containerColor = RosaMedio,
    contentColor = Color.White
)

@Composable
fun DebugButtonColors(): ButtonColors = ButtonDefaults.buttonColors(
    containerColor = RosaForte,
    contentColor = Color.White
)

@Composable
fun InfoButtonColors(): ButtonColors = ButtonDefaults.buttonColors(
    containerColor = Color(0xFFFFC1CC),
    contentColor = Color.Black
)

@Composable
fun ActionButton(
    text: String,
    buttonColors: ButtonColors,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ElevatedButton(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        colors = buttonColors,
        modifier = modifier
            .fillMaxWidth(0.75f)
            .height(55.dp)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    LogcatbuttonTheme {
        App()
    }
}