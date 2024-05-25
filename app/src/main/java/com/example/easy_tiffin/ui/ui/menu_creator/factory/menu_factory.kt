package com.example.easy_tiffin.ui.ui.menu_creator.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.easy_tiffin.ui.ui.menu_creator.MenuCreatorFragment
import com.example.easy_tiffin.ui.ui.menu_creator.menu_repo.menu_repo_
import com.example.easy_tiffin.ui.ui.menu_creator.view_model.Menu_Viewmodel

class menu_factory(private val repository: menu_repo_) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Menu_Viewmodel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return Menu_Viewmodel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}