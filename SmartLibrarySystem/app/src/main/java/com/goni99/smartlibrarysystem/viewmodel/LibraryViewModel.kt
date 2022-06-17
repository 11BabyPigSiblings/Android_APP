package com.goni99.smartlibrarysystem.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goni99.smartlibrarysystem.model.RentBook
import com.goni99.smartlibrarysystem.model.User

class LibraryViewModel:ViewModel() {
    private var _userList = MutableLiveData<ArrayList<User>>()
    val userList: LiveData<ArrayList<User>>
        get() = _userList

    private var _rentBookList = MutableLiveData<ArrayList<ArrayList<RentBook>>>()
    val rentBookList: LiveData<ArrayList<ArrayList<RentBook>>>
        get() = _rentBookList

    fun setUserList(userList: ArrayList<User>){
        this._userList.value = userList
    }

    fun setRentBookList(rentBookList: ArrayList<ArrayList<RentBook>>){
        this._rentBookList.value = rentBookList
    }
}