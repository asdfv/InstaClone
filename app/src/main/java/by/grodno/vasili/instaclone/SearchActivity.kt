package by.grodno.vasili.instaclone

import android.os.Bundle
import android.util.Log

class SearchActivity : BaseActivity() {
    private val TAG = this::class.java.simpleName

    override fun getMenuIndex(): Int {
        return 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupBottomNavigation()
        Log.d(TAG, "onCreate")
    }
}
