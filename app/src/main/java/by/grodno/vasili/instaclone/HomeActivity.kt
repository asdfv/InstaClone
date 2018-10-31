package by.grodno.vasili.instaclone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : BaseActivity() {
    private val TAG = this::class.java.simpleName
    private lateinit var mAuth: FirebaseAuth

    override fun getMenuIndex(): Int {
        return 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupBottomNavigation()
        Log.d(TAG, "onCreate")


        mAuth = FirebaseAuth.getInstance()
        mAuth.signOut()
//        auth.signInWithEmailAndPassword("it@grodno.net", "password").addOnCompleteListener {
//            handleResult(it)
//        }
    }

    override fun onStart() {
        super.onStart()
        if (mAuth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

//    private fun handleResult(result: Task<AuthResult>) {
//        if (result.isSuccessful) {
//            Log.d(TAG, "SignIn success")
//        } else {
//            Log.d(TAG, "SignIn failure: ${result.exception}")
//        }
//    }
}
