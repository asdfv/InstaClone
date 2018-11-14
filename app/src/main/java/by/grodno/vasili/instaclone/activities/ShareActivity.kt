package by.grodno.vasili.instaclone.activities

import android.os.Bundle
import android.util.Log
import by.grodno.vasili.instaclone.R

class ShareActivity : BaseActivity() {
    private val TAG = this::class.java.simpleName

    override fun getMenuIndex(): Int {
        return 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupBottomNavigation()
        Log.d(TAG, "onCreate")
    }
}
