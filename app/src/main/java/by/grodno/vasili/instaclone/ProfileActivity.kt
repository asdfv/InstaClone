package by.grodno.vasili.instaclone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : BaseActivity() {
    private val TAG = this::class.java.simpleName

    override fun getMenuIndex(): Int {
        return 4
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setupBottomNavigation()
        edit_profile_button.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }
        Log.d(TAG, "onCreate")
    }
}
