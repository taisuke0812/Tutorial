package tutorial.fujikawa.fuller.tutorial

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class detail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val key = intent.getIntExtra("key",0)

    }
}
