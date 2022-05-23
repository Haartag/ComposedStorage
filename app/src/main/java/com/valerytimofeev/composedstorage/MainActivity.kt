package com.valerytimofeev.composedstorage

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.valerytimofeev.composedstorage.about.AboutScreen
import com.valerytimofeev.composedstorage.addnewcategory.AddNewCategoryScreen
import com.valerytimofeev.composedstorage.addnewtab.AddNewTabScreen
import com.valerytimofeev.composedstorage.allitemslist.ShowAllScreen
import com.valerytimofeev.composedstorage.categorydetail.CategoryDetailScreen
import com.valerytimofeev.composedstorage.categorylist.CategoryListScreen
import com.valerytimofeev.composedstorage.drawer.Drawer
import com.valerytimofeev.composedstorage.ui.theme.ComposedStorageTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "categories")


@AndroidEntryPoint
@ExperimentalMaterialApi
@ExperimentalPagerApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposedStorageTheme {
                //Navigation
                val navController = rememberNavController()


                //Drawer
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                val openDrawer = {
                    scope.launch {
                        drawerState.open()
                    }
                }
                ModalDrawer(
                    drawerState = drawerState,
                    gesturesEnabled = drawerState.isOpen,
                    drawerContent = {
                        Drawer(
                            onDestinationClicked = {
                                scope.launch {
                                    drawerState.close()
                                }
                                navController.navigate(it)
                            }
                        )
                    }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "category_list_screen"
                    ) {
                        composable("category_list_screen") {
                            CategoryListScreen(
                                navController = navController,
                                openDrawer = { openDrawer() },
                            )
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
                                navController = navController,
                                categoryName = categoryName ?: ""
                            )
                        }
                        //Drawer menu
                        composable("add_new_tab_screen") {
                            AddNewTabScreen(
                                navController = navController
                            )
                        }
                        composable("add_new_category_screen") {
                            AddNewCategoryScreen(
                                navController = navController
                            )
                        }
                        composable("about_screen") {
                            AboutScreen(
                                navController = navController
                            )
                        }
                        composable("show_all") {
                            ShowAllScreen(
                                navController = navController
                            )
                        }
                    }
                }


            }
        }
    }
}

