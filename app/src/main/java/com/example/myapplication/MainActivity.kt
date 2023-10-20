package com.example.myapplication


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlin.random.Random


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BullsEyeGame()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BullsEyePreview() {
    BullsEyeGame()
}

@Composable
fun BullsEyeGame() {
    var targetValue by remember { mutableStateOf(generateRandomTarget()) }
    var playerGuess by remember { mutableStateOf(50) }
    var playerScore by remember { mutableStateOf(0) }
    var feedbackMessage by remember { mutableStateOf("") }

    val paddingValue = 16.dp
    val verticalSpacerHeight = 16.dp
    val sliderSpacerHeight = 32.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValue),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Bull's Eye Game",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = verticalSpacerHeight)
        )

        Text(
            text = "Move the slider as close as possible to: $targetValue",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(verticalSpacerHeight))

        Slider(
            value = playerGuess.toFloat(),
            onValueChange = { newValue ->
                playerGuess = newValue.toInt()
            },
            valueRange = 0f..100f,
        )

        Spacer(modifier = Modifier.height(sliderSpacerHeight))

        Button(
            onClick = {
                val score = calculateScore(targetValue, playerGuess)
                playerScore += score
                feedbackMessage = "Perfect! You scored $score points."
                targetValue = generateRandomTarget()
                playerGuess = 50
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
        ) {
            Text(text = "Hit Me!")
        }

        Spacer(modifier = Modifier.height(verticalSpacerHeight))

        Text(
            text = feedbackMessage,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Green,
        )

        Spacer(modifier = Modifier.height(verticalSpacerHeight))

        Text(
            text = "Your Score: $playerScore",
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}



private fun generateRandomTarget() = Random.nextInt(0, 101)

private fun calculateScore(target: Int, guess: Int): Int {
    val difference = kotlin.math.abs(target - guess)
    return when {
        difference <= 3 -> 5
        difference <= 8 -> 1
        else -> 0
    }
}