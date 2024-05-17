package hu.ait.agora.ui.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.ait.agora.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import hu.ait.agora.ui.navigation.Screen
import hu.ait.agora.ui.theme.agoraDarkGrey
import hu.ait.agora.ui.theme.agoraPurple
import hu.ait.agora.ui.theme.agoraWhite
import hu.ait.agora.ui.theme.interFamilyLight
import hu.ait.agora.ui.theme.interFamilyRegular
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel(),
    onLoginSuccess: () -> Unit = {},
    navController: NavController,
) {

    var showPassword by rememberSaveable { mutableStateOf(false) }
    var email by rememberSaveable { mutableStateOf("barbie@gmail.com") }
    var password by rememberSaveable { mutableStateOf("password") }
    val coroutineScope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }

    Column ( modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)) {

        Image(
            painter = painterResource(id = R.drawable.login_image),
            contentDescription = stringResource(R.string.desc_background),
            modifier = Modifier
                .fillMaxHeight(0.6f)
                .fillMaxWidth()
        )

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {

            // enter email
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
            Spacer(modifier = Modifier.height(16.dp))

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
                    .align(Alignment.CenterHorizontally),
                singleLine = true,
                textStyle = TextStyle(color = agoraWhite)
            ) {

                OutlinedTextFieldDefaults.DecorationBox(
                    value = password,
                    innerTextField = it,
                    enabled = true,
                    singleLine = true,
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    interactionSource = interactionSource,
                    // keep vertical paddings but change the horizontal
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            if (showPassword) {
                                Icon(Icons.Default.Close, null)
                            } else {
                                Icon(Icons.Default.Info, null)
                            }
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(),
                    contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                        top = 2.dp,
                        bottom = 2.dp,
                    ),
                    container = {
                        OutlinedTextFieldDefaults.ContainerBox(enabled = true, isError = false, interactionSource, colors = OutlinedTextFieldDefaults.colors())
                    },
                )

            }

            Spacer(modifier = Modifier.height(15.dp))

            Column( modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            val result = loginViewModel.loginUser(email, password)
                            if (result?.user != null) {
                                onLoginSuccess()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = agoraPurple, contentColor = agoraWhite)
                ) {
                    Text(stringResource(R.string.text_login))
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    Text(
                        stringResource(R.string.text_don_t_have_an_account_yet),
                        color = Color.White,
                        fontFamily = interFamilyRegular,
                        fontSize = 14.sp,
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        stringResource(R.string.text_register),
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = interFamilyRegular,
                        modifier = Modifier.clickable { navController.navigate(Screen.Register.route)}
                    )

                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    when (loginViewModel.loginUiState) {
                        is LoginUiState.Init -> {}
                        is LoginUiState.Loading -> CircularProgressIndicator()
                        is LoginUiState.LoginSuccess -> {
                            Text(
                                text = "Login OK", 
                                color = agoraWhite
                            )
                            navController.navigate(Screen.Feed.route)
                        }
                        
                        is LoginUiState.Error -> {
                            Text(
                                text = "Error: ${(loginViewModel.loginUiState as LoginUiState.Error).error }",
                                color = agoraWhite,
                                fontSize = 12.sp
                            )
                        }
                        
                    }

                }
            }
        }
    }
}
