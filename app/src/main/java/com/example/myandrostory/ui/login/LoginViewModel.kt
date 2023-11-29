package com.example.myandrostory.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myandrostory.data.Repository
import com.example.myandrostory.data.Result
import com.example.myandrostory.data.response.LoginResponse
import com.example.myandrostory.data.response.RegisterResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository): ViewModel() {
    val loginResult: MutableLiveData<Result<LoginResponse>> = MutableLiveData()

    fun loginUser(email: String, password: String) {
        loginResult.postValue(Result.Loading())
        viewModelScope.launch {
            try {
                val response = repository.loginUser(email, password)
                if (response != null) {
                    if(response.isSuccessful){
                        loginResult.postValue(Result.Success(response.body()))
                    } else {
                        loginResult.postValue(Result.Error(response.body()?.message))
                    }
                }
            } catch (ex: Exception) {
                loginResult.value = Result.Error(ex.message)
            }
        }

    }
}