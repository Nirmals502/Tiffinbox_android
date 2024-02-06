package com.example.easy_tiffin

import android.content.Intent
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.easy_tiffin.R
import com.example.easy_tiffin.Models.LoggedInUserView
import com.example.easy_tiffin.Models.LoginFormState
import com.example.easy_tiffin.Models.LoginResult
import com.example.easy_tiffin.ui.login.LoginRepository
import com.example.easy_tiffin.ui.login.LoginActivity
import com.example.easy_tiffin.ui.login.LoginViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.openMocks
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController


@RunWith(RobolectricTestRunner::class)
class LoginViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var activity: LoginActivity
    private lateinit var activityController: ActivityController<LoginActivity>

    @Mock
    private lateinit var loginViewModel: LoginViewModel

    @Mock
    private lateinit var viewModelFactory: ViewModelProvider.Factory

    @Mock
    private lateinit var loginRepository: LoginRepository

    private val loginFormState = MutableLiveData<LoginFormState>()
    private val loginResult = MutableLiveData<LoginResult>()

    private var closeable: AutoCloseable? = null


    @After
    @Throws(java.lang.Exception::class)
    fun closeService() {
        closeable!!.close()
    }

    @Before
    fun setUp() {
        //activity = mock(LoginActivity::class.java)

        //closeable = openMocks(this);
       // loginViewModel = mock()
        closeable = openMocks(this);
        viewModelFactory = mock(ViewModelProvider.Factory::class.java)
        loginViewModel = mock(LoginViewModel::class.java)
        `when`(viewModelFactory.create(LoginViewModel::class.java)).thenReturn(loginViewModel)
        `when`(loginViewModel.loginFormState).thenReturn(loginFormState)
        `when`(loginViewModel.loginResult).thenReturn(loginResult)

        activityController = Robolectric.buildActivity(LoginActivity::class.java, Intent())
        activity = activityController.get()
        activity.loginRepository = loginRepository
       // openMocks(this).close()
    }

    @Test
    fun `test login success`() {
        activityController.create().start().resume()

        // Set LiveData values
        loginFormState.postValue(LoginFormState(isDataValid = true))
        loginResult.postValue(LoginResult(success = LoggedInUserView(displayName = "Test User")))

        // Verify methods are called
        verify(loginViewModel).loginFormState
        verify(loginViewModel).loginResult


        // You can also check the UI changes here
        // Assert that the success Toast is shown, for example
    }

    @Test
    fun `test login failure`() {
        activityController.create().start().resume()

        // Set LiveData values
        loginFormState.postValue(LoginFormState(usernameError = R.string.invalid_username))
        loginResult.postValue(LoginResult(error = "Login failed"))

        // Verify methods are called
        verify(loginViewModel).loginFormState
        verify(loginViewModel).loginResult
        openMocks(this).close()


        // Check the UI for error handling
        // Assert that the error Toast is shown, for example
    }


    // Add more tests as needed
}
