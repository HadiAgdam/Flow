package ir.hadiagdamapps.flow.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import ir.hadiagdamapps.flow.ui.screen.PlaylistScreen
import ir.hadiagdamapps.flow.ui.screen.SongsScreen
import ir.hadiagdamapps.flow.viewmodel.PlaylistScreenViewModel
import ir.hadiagdamapps.flow.viewmodel.SongsViewModel
import ir.hadiagdamapps.flow.viewmodel.factory.PlaylistScreenViewModelFactory

@Composable
fun AppNavHost(navController: NavHostController, application: Application) {

    NavHost(navController, startDestination = SongsScreenRoute) {

        composable<SongsScreenRoute> {
            val viewmodel = viewModel<SongsViewModel>()

            SongsScreen(viewmodel, navController)
        }

        composable<PlaylistScreenRoute> {

            val args = it.toRoute<PlaylistScreenRoute>()

            val viewmodel: PlaylistScreenViewModel = viewModel(factory = PlaylistScreenViewModelFactory(application, args.playlistId))

            PlaylistScreen(viewmodel)

        }

    }

}