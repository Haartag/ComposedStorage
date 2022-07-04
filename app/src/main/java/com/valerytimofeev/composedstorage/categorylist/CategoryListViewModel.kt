package com.valerytimofeev.composedstorage.categorylist

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.valerytimofeev.composedstorage.R
import com.valerytimofeev.composedstorage.data.DatabaseRepository
import com.valerytimofeev.composedstorage.data.local.CategoryItem
import com.valerytimofeev.composedstorage.data.local.TabItem
import com.valerytimofeev.composedstorage.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CategoryListViewModel @Inject constructor(
    private val repository: DatabaseRepository
) : ViewModel() {

    //for pager infinite loop
    val startIndex = Int.MAX_VALUE / 2

    fun getTabFlow(): Flow<List<TabItem>> {
        return repository.getTabsFlow()
    }

    fun getCategoryFlow(tab: String): Flow<List<CategoryItem>> {
        return repository.getCategoryByTabFlow(tab)
    }

    //rows of 2 item entry
    fun getCategoryRowCount(tabSize: Int): Int {
        return if (tabSize % 2 == 0) {
            tabSize / 2
        } else {
            tabSize / 2 + 1
        }
    }

    var currentPage = mutableStateOf(0)

    //color of Tab
    fun getCategoryTypeColor(colorScheme: Int): Color {
        return Constants.colorsMap[colorScheme] ?: Color.LightGray
    }

    fun getCategoryImg(imgIndex: Int): Int {
        return Constants.imgMap[imgIndex] ?: R.drawable.placeholder_white
    }
}
