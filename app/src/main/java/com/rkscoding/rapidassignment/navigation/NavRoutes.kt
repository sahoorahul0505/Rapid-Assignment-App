package com.rkscoding.rapidassignment.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class SubNavRoutes {
    @Serializable
    object LoginSignupRoute : SubNavRoutes()

    @Serializable
    object MainScreenRoute : SubNavRoutes()
}

@Serializable
sealed class NavRoutes {

    @Serializable
    object SignupRoute : NavRoutes()

    @Serializable
    object LoginRoute : NavRoutes()

    @Serializable
    object ResetPasswordRoute : NavRoutes()

    @Serializable
    object HomeRoute : NavRoutes()

    @Serializable
    object ProfileRoute : NavRoutes()

    @Serializable
    object AiChatRoute : NavRoutes()

    @Serializable
    object ProfileSettingsRoute : NavRoutes()

    @Serializable
    data class QuizRoute(val quizDetailsJson: String) : NavRoutes()
//    object QuizRoute : NavRoutes()

    @Serializable
    data class QuizResultRoute(
        val quizResultJson : String
    ) : NavRoutes()
}