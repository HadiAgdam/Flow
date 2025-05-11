package ir.hadiagdamapps.flow

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ir.hadiagdamapps.flow.navigation.AppNavHost
import ir.hadiagdamapps.flow.ui.theme.Color
import ir.hadiagdamapps.flow.ui.theme.FlowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        }

        setContent {
            val systemUiController = rememberSystemUiController()

            LaunchedEffect(Unit) {
                systemUiController.setNavigationBarColor(
                    color = Color.surface,
                    darkIcons = false
                )
                systemUiController.setStatusBarColor(Color.primary, darkIcons = false)

            }
            FlowTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController, application = application)
            }
        }
    }
}