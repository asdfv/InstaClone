package by.grodno.vasili.instaclone.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import by.grodno.vasili.instaclone.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {
    private val TAG = this::class.java.simpleName
    private val mAuth = FirebaseAuth.getInstance()

    override fun getMenuIndex(): Int {
        return 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupBottomNavigation()
        Log.d(TAG, "onCreate")

        home_label.setOnClickListener {
            println("signOut pressed")
            mAuth.signOut()
        }

        mAuth.addAuthStateListener {
            println("signOut AuthStateListener, current user = ${it.currentUser}")
            if (it.currentUser == null) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (mAuth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
