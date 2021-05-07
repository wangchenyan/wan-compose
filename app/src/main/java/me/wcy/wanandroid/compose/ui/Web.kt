package me.wcy.wanandroid.compose.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.text.TextUtils
import android.view.ViewGroup
import android.webkit.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import me.wcy.wanandroid.compose.ui.widget.TitleBar
import me.wcy.wanandroid.compose.viewmodel.WebViewModel

@Composable
fun Web(navController: NavHostController, url: String) {
    val viewModel: WebViewModel = viewModel()
    Column(Modifier.fillMaxSize()) {
        TitleBar(title = viewModel.title, onBack = {
            navController.popBackStack()
        })
        Box(Modifier.fillMaxSize()) {
            AndroidView({ context ->
                WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    settings.apply {
                        domStorageEnabled = true
                        databaseEnabled = true
                        setAppCacheEnabled(true)
                        setAppCachePath(context.cacheDir.absolutePath)
                        allowFileAccess = false
                        savePassword = false
                        javaScriptEnabled = true
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                        }
                    }
                    webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            url: String?
                        ): Boolean {
                            if (url == null) {
                                return false
                            }

                            if (url.startsWith("http://", true)
                                || url.startsWith("https://", true)
                                || url.startsWith("ftp://", true)
                            ) {
                                view?.loadUrl(url)
                            } else {
                                val uri = Uri.parse(url)
                                if (!TextUtils.isEmpty(uri.scheme) && !TextUtils.isEmpty(uri.host)) {
                                    val intent = Intent(Intent.ACTION_VIEW, uri)
                                    if (isInstall(intent)) {
                                        try {
                                            context.startActivity(intent)
                                        } catch (e: Exception) {
                                        }
                                    }
                                }
                            }

                            return true
                        }

                        override fun onReceivedSslError(
                            view: WebView?,
                            handler: SslErrorHandler?,
                            error: SslError?
                        ) {
                            handler?.proceed()
                        }

                        private fun isInstall(intent: Intent): Boolean {
                            return context.packageManager.queryIntentActivities(
                                intent,
                                PackageManager.MATCH_DEFAULT_ONLY
                            ).size > 0
                        }
                    }
                    webChromeClient = object : WebChromeClient() {
                        override fun onReceivedTitle(view: WebView?, title: String?) {
                            viewModel.title = title ?: ""
                        }

                        override fun onProgressChanged(view: WebView?, newProgress: Int) {
                            super.onProgressChanged(view, newProgress)
                            viewModel.progress = newProgress
                        }
                    }
                    loadUrl(url)
                }
            }, Modifier.fillMaxSize())
            if (viewModel.progress < 100) {
                LinearProgressIndicator(
                    progress = viewModel.progress / 100f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp),
                    backgroundColor = Color.Transparent
                )
            }
        }
    }
}
