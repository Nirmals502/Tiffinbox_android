package com.example.TiffinBox.ui.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.TiffinBox.data.LoginDataSource
import com.example.TiffinBox.repository.LoginRepository
import com.example.TiffinBox.viewModel.LoginViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class LoginViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    //@Mock
    lateinit var data: LoginDataSource
   // @Mock
    lateinit var repository: LoginRepository

    lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
      //  MockitoAnnotations.initMocks(this)
        data = LoginDataSource()
        repository = LoginRepository(data)
        viewModel = LoginViewModel(repository)
    }

    @Test
    fun loginWithValidCredentials() {
        // Arrange
        val username = "test@example.com"
        val password = "validPassword"

        //`when`(repository.login(username, password)).thenReturn(Result.Success(LoggedInUser("1","JaneDoe")))

        // Act
        viewModel.login(username, password)

        // Assert
    //    verify(repository, times(1)).login(username, password)
        assert(viewModel.loginResult.value?.success != null)
    }

    @Test
    fun loginWithInvalidCredentials() {
        // Arrange
        val username = "invalidEmail"
        val password = "short"

       // `when`(repository.login(username, password)).thenReturn(Result.Error(IOException("error")))

        // Act
        viewModel.login(username, password)

        // Assert
       // verify(repository, times(1)).login(username, password)
        assert(viewModel.loginResult.value?.error != null)
    }

    @Test
    fun loginDataChangedWithValidInput() {
        // Act
        viewModel.loginDataChanged("test@example.com", "validPassword")

        // Assert
        assert(viewModel.loginFormState.value?.isDataValid == true)
    }

    @Test
    fun loginDataChangedWithInvalidInput() {
        // Act
        viewModel.loginDataChanged("invalidEmail", "short")

        // Assert
        assert(viewModel.loginFormState.value?.isDataValid == false)
       // assert(viewModel.loginFormState.value?.usernameError == R.string.)
       // assert(viewModel.loginFormState.value?.passwordError == R.string.invalid_password)
    }
}
