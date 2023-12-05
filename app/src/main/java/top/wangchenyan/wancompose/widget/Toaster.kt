package top.wangchenyan.wancompose.widget

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import top.wangchenyan.wancompose.WanApplication

@SuppressLint("StaticFieldLeak")
object Toaster {
    private val context = WanApplication.context
    private val handler = Handler(Looper.getMainLooper())

    fun show(resId: Int) {
        show(context.getString(resId))
    }

    fun show(text: CharSequence?) {
        if (text == null) {
            return
        }
        val runnable = Runnable {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run()
        } else {
            handler.post(runnable)
        }
    }
}