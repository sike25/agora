package hu.ait.agora.ui.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import hu.ait.agora.R
import hu.ait.agora.ui.theme.agoraDarkGrey
import hu.ait.agora.ui.theme.agoraPurple
import hu.ait.agora.ui.theme.agoraWhite
import hu.ait.agora.ui.theme.interFamilyBold
import hu.ait.agora.ui.theme.interFamilyLight

@Composable
fun RegisterScreen(
    loginViewModel: LoginViewModel = viewModel(),
    navController: NavController,
) {
    Box (
        modifier = Modifier
    ) {
        var email by rememberSaveable { mutableStateOf("oogieva25@amherst.edu") }
        var password by rememberSaveable { mutableStateOf("password") }
        var username by rememberSaveable { mutableStateOf("Barbie Mattel") }

        fun allInputsValid(): Boolean {
            return (email != "") && (password != "") && (username != "")
        }

        Image(
            painter = painterResource(id = R.drawable.register_image),
            contentDescription = stringResource(R.string.desc_background),
            modifier = Modifier
                .fillMaxSize(1.0f)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight()
                .padding(20.dp, top = 90.dp),
            verticalArrangement = Arrangement.Top,
        ) {

            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "back to login",
                tint = agoraWhite,
                modifier = Modifier.size(60.dp).clickable { navController.popBackStack() }
            )
            Spacer(modifier = Modifier.height(70.dp))
            Text(
                text = "Create New Account",
                fontFamily = interFamilyBold,
                fontSize = 25.sp,
                color = agoraWhite,
                modifier =  Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.height(50.dp))

            // username
            Text(
                text = stringResource(R.string.text_username),
                fontFamily = interFamilyLight,
                fontSize = 13.sp,
                color = agoraWhite,
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer( modifier = Modifier.height(8.dp) )
            BasicTextField(
                value = username,
                onValueChange = {username = it},
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .background(agoraDarkGrey, shape = RoundedCornerShape(14.dp))
                    .padding(15.dp)
                    .align(Alignment.CenterHorizontally),
                singleLine = true,
                textStyle = TextStyle(color = agoraWhite)
            )
            Spacer(modifier = Modifier.height(20.dp))

            // email
            Text(
                text = stringResource(R.string.text_email),
                fontFamily = interFamilyLight,
                fontSize = 13.sp,
                color = agoraWhite,
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer( modifier = Modifier.height(8.dp) )
            BasicTextField(
                value = email,
                onValueChange = {email = it},
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .background(agoraDarkGrey, shape = RoundedCornerShape(14.dp))
                    .padding(15.dp)
                    .align(Alignment.CenterHorizontally),
                singleLine = true,
                textStyle = TextStyle(color = agoraWhite)
            )
            Spacer(modifier = Modifier.height(20.dp))

            // enter password
            Text(
                text = stringResource(R.string.text_password),
                fontFamily = interFamilyLight,
                fontSize = 13.sp,
                color = agoraWhite,
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            BasicTextField(
                value = password,
                onValueChange = {password = it},
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .background(agoraDarkGrey, shape = RoundedCornerShape(14.dp))
                    .padding(15.dp)
                    .align(Alignment.CenterHorizontally),
                singleLine = true,
                textStyle = TextStyle(color = agoraWhite)
            )

            Spacer(modifier = Modifier.height(60.dp))

            Button(
                onClick = {
                    if (allInputsValid()) {
                        loginViewModel.registerUser(username, email, password)
                    } else {
                        loginViewModel.registerUiState = RegisterUiState.Error("All inputs have not been supplied.")
                    }

                },
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(10),
                colors = ButtonDefaults.buttonColors(containerColor = agoraPurple, contentColor = agoraWhite)
            ) {
                Text(stringResource(R.string.btn_register))
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer (modifier = Modifier.height(30.dp))
                when (loginViewModel.registerUiState) {
                    is RegisterUiState.Init -> {}
                    is RegisterUiState.Loading -> CircularProgressIndicator()
                    is RegisterUiState.Error -> {
                        Text(
                            text = "Error: ${(loginViewModel.registerUiState as RegisterUiState.Error).error }",
                            color = agoraWhite
                        )
                    }
                    is RegisterUiState.RegisterSuccess -> {
                        Text(
                            text = "Register OK",
                            color = agoraWhite
                        )
                        navController.popBackStack()
                    }
                }
            }

        }

    }


}