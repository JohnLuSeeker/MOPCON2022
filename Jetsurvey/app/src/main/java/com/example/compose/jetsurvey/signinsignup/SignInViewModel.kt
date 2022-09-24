/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.compose.jetsurvey.signinsignup

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.compose.jetsurvey.Screen
import com.example.compose.jetsurvey.Screen.SignUp
import com.example.compose.jetsurvey.Screen.Survey
import com.example.compose.jetsurvey.network.UserLoginReqDTO
import com.example.compose.jetsurvey.network.login
import com.example.compose.jetsurvey.network.qrcode
import com.example.compose.jetsurvey.util.Event
import io.ktor.util.InternalAPI
import io.ktor.util.toByteArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream

class SignInViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    var _byteArray by mutableStateOf(byteArrayOf())

    /**
     * Consider all sign ins successful
     */
    fun signIn(email: String, password: String, code: String) {
        userRepository.signIn(email, password)
        viewModelScope.launch(Dispatchers.IO) {
            login(
                UserLoginReqDTO(
                    username = email,
                    password = password,
                    code = code
                )
            )
        }
        _navigateTo.value = Event(Survey)
    }

    fun signInAsGuest() {
        userRepository.signInAsGuest()
        _navigateTo.value = Event(Survey)
    }

    fun signUp() {
        _navigateTo.value = Event(SignUp)
    }

    @OptIn(InternalAPI::class)
    fun qrCode(username: String){
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("ss", username)
            _byteArray = qrcode(username).content.toByteArray()
        }
    }
}

class SignInViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            return SignInViewModel(UserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
