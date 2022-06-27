package com.valerytimofeev.composedstorage.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.valerytimofeev.composedstorage.addnewcategory.AddNewCategoryViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

@ExperimentalCoroutinesApi
class AddNewCategoryViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: AddNewCategoryViewModel



}