package com.valerytimofeev.composedstorage.allitemslist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.valerytimofeev.composedstorage.data.DatabaseRepository
import com.valerytimofeev.composedstorage.utils.Constants.divider
import com.valerytimofeev.composedstorage.utils.ListForSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ShowAllViewModel @Inject constructor(
    private val repository: DatabaseRepository
) : ViewModel() {

    val searchText = mutableStateOf("")
    val isHintDisplayed = mutableStateOf(true)

    fun getSearchFlow(): Flow<List<ListForSearch>> {
        return repository.getList()
    }

    //items sorted by name by default.
    fun search(listForFilter: List<ListForSearch>, query: String): List<ListForSearch> {
        return if (query.length > 1) {
            listForFilter.filter { it.itemName.contains(query.trim(), ignoreCase = true) }
                .sortedBy { it.itemName }
        } else {
            listForFilter.sortedBy { it.itemName }
        }
    }

    fun stringSizeToDecimalSize(number: String): String =
        (number.toBigDecimal().divide(divider)).toString()
}


