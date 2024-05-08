package hu.ait.agora.ui.screen.splash

import android.annotation.SuppressLint
import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.VideoView
import android.window.SplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player.REPEAT_MODE_OFF
import com.google.android.exoplayer2.ui.StyledPlayerView
import hu.ait.agora.ui.navigation.Screen
import hu.ait.agora.ui.theme.agoraBlack

//@Composable
//fun SplashScreen(navController: NavController) {
//    Column (
//        modifier = Modifier
//            .fillMaxSize(0.5f)
//            .background(agoraBlack),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_animation))
//        val logoAnimationState =
//            animateLottieCompositionAsState(composition = composition)
//        LottieAnimation(
//            composition = composition,
//            progress = { logoAnimationState.progress }
//        )
//        if (logoAnimationState.isAtEnd && logoAnimationState.isPlaying) {
//            navController.navigate(Screen.Login.route)
//        }
//    }
//
//}
//
//
//@SuppressLint("OpaqueUnitKey")
//@Composable
//fun SplashScreen(navController: NavController) {
//
//    val context = LocalContext.current
//    val splashUri = "https://firebasestorage.googleapis.com/v0/b/agora-hills.appspot.com/o/splash_video.mp4?alt=media&token=41ac4d1b-d834-47d0-a7b8-bdb60a0016d4"
//
//    val mediaItem = MediaItem.Builder().setUri(splashUri).build()
//    val exoPlayer = remember(context, mediaItem) {
//        ExoPlayer.Builder(context)
//            .build()
//            .also { exoPlayer ->
//                exoPlayer.setMediaItem(mediaItem)
//                exoPlayer.prepare()
//                exoPlayer.play()
//                exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
//                exoPlayer.repeatMode = REPEAT_MODE_OFF
//            }
//    }
//
//    DisposableEffect(
//        AndroidView(
//            factory = {
//                StyledPlayerView(context).apply {
//                    player = exoPlayer
//                    useController = false
//                }
//            }
//        )
//    ) {
//        onDispose {
//            exoPlayer.release()
//            navController.navigate(Screen.Login.route)
//        }
//    }
//
//
//}







@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current
    val videoView = remember { VideoView(context) }
    val splashUri = "https://firebasestorage.googleapis.com/v0/b/agora-hills.appspot.com/o/splash_video.mp4?alt=media&token=41ac4d1b-d834-47d0-a7b8-bdb60a0016d4"

    DisposableEffect(videoView) {
        videoView.setVideoURI(Uri.parse(splashUri))
        videoView.start()
        videoView.setOnCompletionListener {
            navController.navigate(Screen.Login.route)
        }
        onDispose {
            videoView.stopPlayback()
        }
    }

    Box(Modifier.fillMaxSize().background(agoraBlack)){
        AndroidView(factory = { videoView }) {
            // Configuration and styling can be adjusted here.
            it.layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            it.setMediaController(null)  // Hide media controls
        }

    }


}
