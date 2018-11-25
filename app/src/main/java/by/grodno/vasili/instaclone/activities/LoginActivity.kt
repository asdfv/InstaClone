package by.grodno.vasili.instaclone.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import by.grodno.vasili.instaclone.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LoginActivity : AppCompatActivity(), KeyboardVisibilityEventListener, CoroutineScope {
    private val TAG = this::class.java.simpleName
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        job = Job()
        disableButtonForEmptyInputs(login_button, email_input, password_input)
        setListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onVisibilityChanged(isKeyboardOpen: Boolean) {
        sign_up_toolbar.visibility = if (isKeyboardOpen) View.GONE else View.VISIBLE
    }

    private fun setListeners() {
        KeyboardVisibilityEvent.setEventListener(this, this)
        login_button.setOnClickListener {
            launch(Dispatchers.Main) {
                login()
            }
        }
        sign_up_toolbar.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private suspend fun login() {
        val mAuth = FirebaseAuth.getInstance()
        val email = email_input.text.toString()
        val password = password_input.text.toString()
        if (signIn(mAuth, email, password)) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        } else {
            showToast("Authentication failed")
        }
    }

    private suspend fun signIn(mAuth: FirebaseAuth, email: String, password: String) =
        suspendCoroutine<Boolean> { cont ->
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    val successful = task.isSuccessful
                    cont.resume(successful)
                    if (!successful) {
                        Log.d(TAG, "SignIn failure: ${task.exception}")
                    }
                }
        }
}
