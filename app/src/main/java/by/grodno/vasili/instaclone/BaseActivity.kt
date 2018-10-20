package by.grodno.vasili.instaclone

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.bottom_navigation_view.*

abstract class BaseActivity : AppCompatActivity() {
    private val TAG = BaseActivity::class.java.simpleName

    abstract fun getMenuIndex(): Int

    fun setupBottomNavigation() {
        bottom_navigation_view.enableAnimation(false)
        bottom_navigation_view.isItemHorizontalTranslationEnabled = false
        bottom_navigation_view.labelVisibilityMode = 1
        bottom_navigation_view.setIconSize(29f, 29f)
        bottom_navigation_view.setOnNavigationItemSelectedListener {
            val nextActivity = when (it.itemId) {
                R.id.home_nav_item -> HomeActivity::class.java
                R.id.search_nav_item -> SearchActivity::class.java
                R.id.share_nav_item -> ShareActivity::class.java
                R.id.likes_nav_item -> LikesActivity::class.java
                R.id.profile_nav_item -> ProfileActivity::class.java
                else -> {
                    Log.e(TAG, "Unknown item clicked")
                    null
                }
            }
            if (nextActivity == null) {
                false
            } else {
                val intent = Intent(this, nextActivity)
                intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                startActivity(intent)
                overridePendingTransition(0,0)
                true
            }
        }
        bottom_navigation_view.menu.getItem(getMenuIndex()).isChecked = true
    }
}
