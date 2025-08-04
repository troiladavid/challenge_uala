package com.davidtroila.desafiauala

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.davidtroila.desafiauala.navigation.NavigationWrapper
import com.davidtroila.desafiauala.ui.theme.DesafíoUalaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DesafíoUalaTheme {
                NavigationWrapper()
            }
        }
    }
}