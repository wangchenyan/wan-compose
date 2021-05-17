package me.wcy.wanandroid.compose

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.wcy.wanandroid.compose.ui.Main
import me.wcy.wanandroid.compose.ui.mine.CollectList
import me.wcy.wanandroid.compose.ui.mine.Login
import me.wcy.wanandroid.compose.ui.mine.Register
import me.wcy.wanandroid.compose.ui.search.Search
import me.wcy.wanandroid.compose.ui.search.SearchResult
import me.wcy.wanandroid.compose.ui.web.Web

@Composable
fun ComposeNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { Main(navController) }
        composable("web?url={url}") { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""
            Web(navController, url)
        }
        composable("login") { Login(navController) }
        composable("register") { Register(navController) }
        composable("collect") { CollectList(navController) }
        composable("search") { Search(navController) }
        composable("search_result?keyword={keyword}") { backStackEntry ->
            val keyword = backStackEntry.arguments?.getString("keyword") ?: ""
            SearchResult(navController, keyword)
        }
    }
}