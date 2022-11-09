package com.detmerl.flipcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.detmerl.flipcard.data.JsonReader
import com.detmerl.flipcard.ui.theme.FlipcardTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.detmerl.flipcard.data.Translation
import com.detmerl.flipcard.data.Translations
import kotlin.random.Random


class MainActivity : ComponentActivity() {
    private lateinit var  translations: ArrayList<Translation>
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        translations = JsonReader().getData(this)
        getNextTranslationStartingPoint(translations)
        setContent {
            FlipcardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                     translationText.value = if (startingLanguage == RUSSIAN) translation.russian else translation.english
                    TranslationCard(translations)

                    
                }
            }
        }


    }
}

fun getNextTranslationStartingPoint(translations: ArrayList<Translation>) {
    if (startingIndex + 15 < translations.size)
        startingIndex +=  15
    else startingIndex = 0
    generateRandomTrans(translations)
}


fun generateRandomTrans(translations: ArrayList<Translation>) {
    translation =
        translations[Random(System.nanoTime()).nextInt(startingIndex, startingIndex+ 15)]

    translationText.value = if (startingLanguage == RUSSIAN) translation.russian else translation.english
    isRussian = startingLanguage == RUSSIAN
}
const val RUSSIAN = "russian"
const val ENGLISH = "english"
var startingLanguage = RUSSIAN
    var translation = Translation("", "")
val translationText = mutableStateOf<String>("translationText")
var  isRussian = true
var startingIndex = 0
@ExperimentalMaterialApi
@Composable
fun TranslationCard(translations: ArrayList<Translation>) {
    Column ( verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ){


        Card(
            shape = RoundedCornerShape(8.dp),
            backgroundColor = MaterialTheme.colors.secondaryVariant,
            onClick = {
                if (isRussian) {
                    translationText.value = translation.english
                    isRussian = false
                } else  {
                    translationText.value = translation.russian
                    isRussian = true
                }
            },
            modifier = Modifier.width(800.dp).padding(16.dp)

        ) {
            Column(
                modifier = Modifier
                    .height(200.dp)
                    .padding(16.dp)
                    ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var entry: String by translationText
                Text(
                    text = entry,
                    style = MaterialTheme.typography.h4
                )


            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .height(100.dp)
                .padding(16.dp),

        ) {
            Button(
                onClick = { generateRandomTrans(translations) }

            ) {

                Text(
                    "next",
                    style = MaterialTheme.typography.h4
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .height(100.dp)
                .padding(16.dp),

            ) {
            Button(
                onClick = {
                    startingLanguage = if (startingLanguage == RUSSIAN) ENGLISH else RUSSIAN
                    isRussian = startingLanguage == RUSSIAN
                    generateRandomTrans(translations)
                }

            ) {

                Text(
                    "switch ",
                    style = MaterialTheme.typography.h4
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .height(200.dp)
                .padding(16.dp),

            ) {
            Button(
                onClick = { getNextTranslationStartingPoint(translations) }

            ) {

                Text(
                    "New Game",
                    style = MaterialTheme.typography.h4
                )
            }
        }
    }

}



@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FlipcardTheme {
        //TranslationCard(tra)
    }
}