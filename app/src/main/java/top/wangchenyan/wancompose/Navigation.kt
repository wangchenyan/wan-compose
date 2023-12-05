package top.wangchenyan.wancompose

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import top.wangchenyan.wancompose.ui.Main
import top.wangchenyan.wancompose.ui.mine.CollectList
import top.wangchenyan.wancompose.ui.mine.Login
import top.wangchenyan.wancompose.ui.mine.Register
import top.wangchenyan.wancompose.ui.search.Search
import top.wangchenyan.wancompose.ui.search.SearchResult
import top.wangchenyan.wancompose.ui.web.Web

@ExperimentalPagerApi
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