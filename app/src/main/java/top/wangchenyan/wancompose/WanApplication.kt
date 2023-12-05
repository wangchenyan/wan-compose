package top.wangchenyan.wancompose

import android.app.Application
import android.content.Context
import android.util.Log
import me.wcy.mockhttp.MockHttp
import me.wcy.mockhttp.MockHttpOptions
import top.wangchenyan.wancompose.auth.AuthManager

/**
 * Created by wcy on 2021/4/1.
 */
class WanApplication : Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        AuthManager.init()
        val options = MockHttpOptions.Builder()
            .setMockServerPort(3000)
            .setMockSleepTime(500)
            .setLogEnable(true)
            .setLogLevel(Log.ERROR)
            .build()
        MockHttp.get().setMockHttpOptions(options)
        MockHttp.get().start(this)
    }
}