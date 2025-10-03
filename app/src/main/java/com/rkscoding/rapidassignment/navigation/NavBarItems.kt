package com.rkscoding.rapidassignment.navigation

import com.rkscoding.rapidassignment.R

enum class NavBarItems(
    val route: NavRoutes,
    val label: String,
    val selectedIcon: Int,
    val unSelectedIcon : Int,
    val contentDescription: String
) {
    HOME(
        route = NavRoutes.HomeRoute,
        label = "Home",
        selectedIcon = R.drawable.home_filled,
        unSelectedIcon = R.drawable.home_outlined,
        contentDescription = "Home"
    ),
    AI_CHAT(
        route = NavRoutes.AiChatRoute,
        label = "AI",
        selectedIcon = R.drawable.ai_star,
        unSelectedIcon = R.drawable.ai_star,
        contentDescription = "AI"
    ),
    PROFILE(
        route = NavRoutes.ProfileRoute,
        label = "Profile",
        selectedIcon = R.drawable.profile_filled,
        unSelectedIcon = R.drawable.profile_outlined,
        contentDescription = "Profile"
    ),
}