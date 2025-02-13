package ir.hadiagdamapps.flow.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ir.hadiagdamapps.flow.ui.screen.SongsScreen
import ir.hadiagdamapps.flow.viewmodel.SongsViewModel

@Composable
fun AppNavHost(navController: NavHostController) {

    NavHost(navController, startDestination = SongsScreenRoute) {

        composable<SongsScreenRoute> {
            val viewmodel = viewModel<SongsViewModel>()

            SongsScreen(viewmodel)
        }

        composable<PlayingSongRoute> {  }

    }

}