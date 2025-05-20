package com.example.foodhub_android.ui.features.home

import androidx.lifecycle.ViewModel
import com.example.foodhub_android.data.FoodApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val foodApi: FoodApi ) : ViewModel() {


    sealed class HomeScreenState(){
        object Loading : HomeScreenState()
        object Empty : HomeScreenState()
//        data class Success(val data : List<String>) : HomeScreenState()
        object Success : HomeScreenState()
    }

    sealed class HomeScreenNavigationEvents{
        object NavigateToDetails : HomeScreenNavigationEvents()
    }
}