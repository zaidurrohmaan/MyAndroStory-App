package com.example.myandrostory.ui.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myandrostory.data.Repository
import com.example.myandrostory.data.Result
import com.example.myandrostory.data.response.RegisterResponse
import kotlinx.coroutines.launch

class SignUpViewModel(private val repository: Repository) : ViewModel() {

    val registerResult: MutableLiveData<Result<RegisterResponse>> = MutableLiveData()

    fun registerUser(name: String, email: String, password: String) {
        registerResult.postValue(Result.Loading())
        viewModelScope.launch {
            try {
                val response = repository.registerUser(name, email, password)
                if (response.isSuccessful) {
                    registerResult.postValue(Result.Success(response.body()))
                } else {
                    registerResult.postValue(Result.Error(response.body()?.message))
                }
            } catch (ex: Exception) {
                registerResult.value = Result.Error(ex.message)
            }
        }

    }
}