package com.example.codingapp.presentation.tools

import android.os.Bundle
import androidx.navigation.NavDirections

sealed class NavigationCommand {
    data class To(val directions: NavDirections, val extra: Bundle? = null) :
        NavigationCommand()

    object Back : NavigationCommand()
    data class BackTo(val destinationId: Int) : NavigationCommand()
}