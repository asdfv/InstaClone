package by.grodno.vasili.instaclone.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import by.grodno.vasili.instaclone.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener

class LoginActivity : AppCompatActivity(), KeyboardVisibilityEventListener, TextWatcher {
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login_button.isEnabled = false
        KeyboardVisibilityEvent.setEventListener(this, this)
        email_input.addTextChangedListener(this)
        password_input.addTextChangedListener(this)
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
        if (isKeyboardOpen) {
            scroll_view.scrollTo(0, scroll_view.bottom)
            sign_up_toolbar.visibility = View.GONE
        } else {
            scroll_view.scrollTo(0, scroll_view.top)
            sign_up_toolbar.visibility = View.VISIBLE
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        login_button.isEnabled =
                validate(email_input.text.toString(), password_input.text.toString())
    }

    private fun validate(email: String, password: String) =
        email.isNotEmpty() && password.isNotEmpty()

    private fun handleResult(result: Task<AuthResult>) {
        if (!result.isSuccessful) {
            showToast("Wrong email or password")
            Log.d(TAG, "SignIn failure: ${result.exception}")
            return
        }
        startActivity(Intent(this, HomeActivity::class.java))
    }
}
