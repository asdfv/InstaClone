package by.grodno.vasili.instaclone.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import by.grodno.vasili.instaclone.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener

class LoginActivity : AppCompatActivity(), KeyboardVisibilityEventListener {
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        KeyboardVisibilityEvent.setEventListener(this, this)
        disableButtonForEmptyInputs(login_button, email_input, password_input)
    }

    @Suppress("UNUSED_PARAMETER")
    fun login(view: View) {
        val mAuth = FirebaseAuth.getInstance()
        val email = email_input.text.toString()
        val password = password_input.text.toString()
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { handleResult(it) }
    }

    @Suppress("UNUSED_PARAMETER")
    fun startRegister(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    override fun onVisibilityChanged(isKeyboardOpen: Boolean) {
        sign_up_toolbar.visibility = if (isKeyboardOpen) View.GONE else View.VISIBLE
    }

    private fun handleResult(result: Task<AuthResult>) {
        if (!result.isSuccessful) {
            showToast("Wrong email or password")
            Log.d(TAG, "SignIn failure: ${result.exception}")
            return
        }
        startActivity(Intent(this, HomeActivity::class.java))
    }
}
