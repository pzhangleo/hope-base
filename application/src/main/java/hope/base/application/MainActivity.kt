package hope.base.application

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity

import androidx.appcompat.app.AppCompatActivity
import hope.base.AppConstant
import hope.base.extensions.logv
import hope.base.extensions.toast
import hope.base.log.ZLog
import hope.base.utils.AndroidUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppConstant.init(application)
        tv.setOnClickListener {
            "AndroidUtils.openAlbum(this, null, false, 0, 0, 0)".toast(Gravity.CENTER)
        }
        ZLog.init(true)
        "Test test test".logv()
        ZLog.d("test test test")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("URI", AndroidUtils.getRealPathFromURI(this, data?.data, null))
    }
}
