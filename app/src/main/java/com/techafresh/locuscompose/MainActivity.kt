package com.techafresh.locuscompose

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.birjuvachhani.locus.Locus
import com.birjuvachhani.locus.isDenied
import com.birjuvachhani.locus.isFatal
import com.birjuvachhani.locus.isPermanentlyDenied
import com.birjuvachhani.locus.isSettingsDenied
import com.birjuvachhani.locus.isSettingsResolutionFailed
import com.techafresh.locuscompose.ui.theme.LocusComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LocusComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")

                    Locus.setLogging(true)

                    Locus.startLocationUpdates(this@MainActivity){
                        it.location?.let {
                            Log.d("TAG", "onCreate: ${it.latitude} \n Provider = ${it.provider}")
                        }
                        it.error?.let {error ->
                            Log.d("TAG", "onCreate: Error = ${error.message}")
                            when {
                                error.isDenied -> {
                                    /* When permission is denied */
                                    setLog("Permission Denied!!" , applicationContext)
                                }
                                error.isPermanentlyDenied -> {
                                    /* When permission is permanently denied */
                                    setLog("Permission Permanently Denied!!" , applicationContext)
                                }
                                error.isSettingsResolutionFailed -> { /* When location settings resolution is failed */ }
                                error.isSettingsDenied -> { /* When user denies to allow required location settings */ }
                                error.isFatal -> { /* None of above */ }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun setLog(message : String , context: Context){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    Log.d("SET_LOG", "setLog: $message")
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LocusComposeTheme {
        Greeting("Android")
    }
}