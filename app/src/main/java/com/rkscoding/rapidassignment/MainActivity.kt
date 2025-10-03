package com.rkscoding.rapidassignment


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.rkscoding.rapidassignment.data.remote.dto.response.QuizSubmitResponse
import com.rkscoding.rapidassignment.data.remote.dto.response.UsersQuizDetailsResponse
import com.rkscoding.rapidassignment.data.remote.session.SessionToken
import com.rkscoding.rapidassignment.navigation.NavBarItems
import com.rkscoding.rapidassignment.navigation.NavRoutes
import com.rkscoding.rapidassignment.navigation.NavSerializer
import com.rkscoding.rapidassignment.navigation.SubNavRoutes
import com.rkscoding.rapidassignment.ui.screen.ai_chat.AiChatScreen
import com.rkscoding.rapidassignment.ui.screen.home.HomeScreen
import com.rkscoding.rapidassignment.ui.screen.login.LoginScreen
import com.rkscoding.rapidassignment.ui.screen.profile.ProfileScreen
import com.rkscoding.rapidassignment.ui.screen.profile.ProfileSettingsScreen
import com.rkscoding.rapidassignment.ui.screen.quiz.QuizResultScreen
import com.rkscoding.rapidassignment.ui.screen.quiz.QuizScreen
import com.rkscoding.rapidassignment.ui.screen.register.SignupScreen
import com.rkscoding.rapidassignment.ui.screen.reset_password.ResetPasswordScreen
import com.rkscoding.rapidassignment.ui.theme.RapidAssignmentTheme
import com.rkscoding.rapidassignment.ui.theme.indigo
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var session: SessionToken
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()
            val startDest = if (session.getToken() == null)
                SubNavRoutes.LoginSignupRoute
            else
                SubNavRoutes.MainScreenRoute

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            val showBottomBar = when (currentDestination?.route) {
                NavRoutes.HomeRoute::class.qualifiedName,
                NavRoutes.AiChatRoute::class.qualifiedName,
                NavRoutes.ProfileRoute::class.qualifiedName,
                    -> true

                else -> false
            }

            RapidAssignmentTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Scaffold(
                        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
                        bottomBar = {
                            if (showBottomBar) {
                                NavigationBar(
                                    containerColor = Color.White
                                ) {
                                    NavBarItems.entries.forEach { navBarItem ->
                                        val selected =
                                            currentDestination?.hierarchy?.any {
                                                it.route == navBarItem.route::class.qualifiedName
                                            } == true
                                        NavigationBarItem(
                                            selected = selected,
                                            onClick = {
                                                navController.navigate(navBarItem.route) {
                                                    // Pop up to the start destination of the graph to avoid building a large stack of composable(s)
                                                    popUpTo(navController.graph.startDestinationId) {
                                                        saveState = true
                                                    }
                                                    // Avoid multiple copies of the same destination when re-selecting the same item
                                                    launchSingleTop = true
                                                    // Restore state when re-selecting a previously selected item
                                                    restoreState = true
                                                }
                                            },
                                            icon = {
                                                Icon(
                                                    painter = if (selected)
                                                        painterResource(id = navBarItem.selectedIcon)
                                                    else painterResource(
                                                        id = navBarItem.unSelectedIcon
                                                    ),
                                                    contentDescription = navBarItem.contentDescription,
                                                    modifier = Modifier.size(28.dp)
                                                )
                                            },
                                            colors = NavigationBarItemDefaults.colors(
                                                selectedIconColor = indigo,
                                                unselectedIconColor = Color.LightGray,
                                                indicatorColor = Color.Transparent
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    ) { paddingValues ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                                .background(Color.White)
                        ) {
                            NavHost(
                                navController = navController,
                                startDestination = startDest,
                                modifier = Modifier
                                    .fillMaxSize(),
                                enterTransition = {
                                    slideInHorizontally(
                                        initialOffsetX = { fullWidth -> fullWidth },
                                        animationSpec = tween(700)
                                    ) + fadeIn(animationSpec = tween(700))
                                },
                                exitTransition = {
                                    slideOutHorizontally(
                                        targetOffsetX = { fullWidth -> fullWidth },
                                        animationSpec = tween(700)
                                    ) + fadeOut(animationSpec = tween(700))
                                },
                                // Combine slide and fade for the back navigation (Screen B -> A)
                                popEnterTransition = {
                                    slideInHorizontally(
                                        initialOffsetX = { fullWidth -> -fullWidth },
                                        animationSpec = tween(700)
                                    ) + fadeIn(animationSpec = tween(700))
                                },
                                popExitTransition = {
                                    slideOutHorizontally(
                                        targetOffsetX = { fullWidth -> fullWidth },
                                        animationSpec = tween(700)
                                    ) + fadeOut(animationSpec = tween(700))
                                }
                            ) {
                                navigation<SubNavRoutes.LoginSignupRoute>(startDestination = NavRoutes.LoginRoute) {
                                    composable<NavRoutes.LoginRoute> {
                                        LoginScreen(navController = navController)
                                    }
                                    composable<NavRoutes.SignupRoute> {
                                        SignupScreen(navController = navController)
                                    }
                                    composable<NavRoutes.ResetPasswordRoute> {
                                        ResetPasswordScreen(navController = navController)
                                    }
                                }

                                navigation<SubNavRoutes.MainScreenRoute>(startDestination = NavRoutes.HomeRoute) {
                                    composable<NavRoutes.HomeRoute> {
                                        HomeScreen(navController = navController)
                                    }

                                    composable<NavRoutes.ProfileRoute> {
                                        ProfileScreen(navController = navController)
                                    }

                                    composable<NavRoutes.AiChatRoute> {
                                        AiChatScreen()
                                    }

                                    composable<NavRoutes.ProfileSettingsRoute> {
                                        ProfileSettingsScreen(navController = navController)
                                    }

                                    composable<NavRoutes.QuizRoute> {
                                        val route = it.toRoute<NavRoutes.QuizRoute>()
                                        val decodedDetailsString =
                                            NavSerializer.decode<UsersQuizDetailsResponse>(route.quizDetailsJson)
                                        QuizScreen(
                                            navController = navController,
                                            quizDetails = decodedDetailsString
                                        )
                                    }

                                    composable<NavRoutes.QuizResultRoute> {
                                        val route = it.toRoute<NavRoutes.QuizResultRoute>()
                                        val decodedResultString =
                                            NavSerializer.decode<QuizSubmitResponse>(route.quizResultJson)
                                        QuizResultScreen(
                                            quizResult = decodedResultString,
                                            navController = navController
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}