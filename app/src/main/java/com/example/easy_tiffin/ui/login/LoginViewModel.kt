package com.example.easy_tiffin.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy_tiffin.R
import kotlinx.coroutines.launch
import com.example.easy_tiffin.Models.LoggedInUserView
import com.example.easy_tiffin.Models.LoginFormState
import com.example.easy_tiffin.Models.LoginResult
import com.example.easy_tiffin.data.Result
import javax.inject.Inject

open class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {

        viewModelScope.launch {
            val result = loginRepository.login(username, password)
            when (result) {
                is Result.Success -> {
                    _loginResult.value =
                        LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
                }

                is Result.Error -> {
                    val errorMessage = result.exception.message ?: "An error occurred"
                    _loginResult.value = LoginResult(error = errorMessage)
                }
            }
        }
    }

    fun loginDataChanged(username: String, password: String) {
        when {
            username.isEmpty() -> _loginForm.value =
                LoginFormState(usernameError = R.string.email_required)

            password.isEmpty() -> _loginForm.value =
                LoginFormState(passwordError = R.string.password_required)

            !Patterns.EMAIL_ADDRESS.matcher(username).matches() -> _loginForm.value =
                LoginFormState(usernameError = R.string.invalid_username)
            else -> {
                _loginForm.value = LoginFormState(isDataValid = true)
            }
        }
    }


}
