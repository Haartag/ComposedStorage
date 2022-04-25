package com.valerytimofeev.composedstorage

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.valerytimofeev.composedstorage.categorydetail.CategoryDetailScreen
import com.valerytimofeev.composedstorage.categorylist.CategoryListScreen
import com.valerytimofeev.composedstorage.ui.theme.ComposedStorageTheme
import dagger.hilt.android.AndroidEntryPoint

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "categories")


@AndroidEntryPoint
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposedStorageTheme {
                //Navigation
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "category_list_screen"
                ) {
                    composable("category_list_screen") {
                        CategoryListScreen(navController = navController)
                    }
                    composable("category_detail_screen/{categoryName}", arguments = listOf(
                        navArgument("categoryName") {
                            type = NavType.StringType
                        }
                    )
                    ) {
                        val categoryName = remember {
                            it.arguments?.getString("categoryName")
                        }
                        CategoryDetailScreen(
                            categoryName = categoryName ?: ""
                        )
                    }
                }
            }
        }
    }
}

