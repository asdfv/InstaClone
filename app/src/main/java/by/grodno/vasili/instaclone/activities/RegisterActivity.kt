package by.grodno.vasili.instaclone.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.grodno.vasili.instaclone.R
import by.grodno.vasili.instaclone.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_register_email.*
import kotlinx.android.synthetic.main.fragment_register_namepass.*

class RegisterActivity : AppCompatActivity(), EmailFragment.Listener, NamePassFragment.Listener {
    private val TAG = this::class.java.simpleName
    private var mEmail: String? = null
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference

        if (isFirstStart(savedInstanceState)) {
            presentEmailFragment()
        }
    }

    override fun onNext(email: String) {
        mEmail = email
        presentNamePassFragment()
    }

    override fun onRegister(fullName: String, password: String) {
        val email = mEmail
        if (email == null) {
            showToast(getString(R.string.enter_correct_email))
            supportFragmentManager.popBackStack()
            return
        }
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.exception != null) {
                Log.e(TAG, "Error saving user auth: ${it.exception}")
                showToast("Error in email or password, please recheck it")
                supportFragmentManager.popBackStack()
                return@addOnCompleteListener
            }
            val userId = it.result!!.user.uid
            saveUser(userId, fullName, email)
        }
    }

    private fun saveUser(userId: String, fullName: String, email: String) {
        mDatabase
            .child("users")
            .child(userId)
            .setValue(createUser(fullName, email))
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    Log.e(TAG, "Error saving user")
                }
            }
    }

    private fun createUser(fullName: String, mEmail: String): User {
        val username = fullName.toLowerCase().replace(" ", ".")
        return User(name = fullName, username = username, email = mEmail)
    }

    private fun isFirstStart(savedInstanceState: Bundle?) = savedInstanceState == null

    private fun presentEmailFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.register_frame_layout, EmailFragment())
            .commit()
    }

    private fun presentNamePassFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.register_frame_layout, NamePassFragment())
            .addToBackStack(null)
            .commit()
    }
}

class EmailFragment : Fragment() {
    private lateinit var mListener: Listener

    interface Listener {
        fun onNext(email: String)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register_email, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as Listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        next_btn.setOnClickListener {
            val email = email_input.text.toString()
            if (email.isValidEmail()) {
                mListener.onNext(email)
            } else {
                showToast(getString(R.string.enter_valid_email))
            }
        }
    }
}

class NamePassFragment : Fragment() {
    private lateinit var mListener: Listener

    interface Listener {
        fun onRegister(fullName: String, password: String)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register_namepass, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as Listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        register_button.setOnClickListener {
            val fullName = full_name_input.text.toString()
            val password = password_input.text.toString()
            if (enteredDataIsNotCorrect(fullName, password)) {
                showToast(getString(R.string.entered_data_is_incorrect))
            } else {
                mListener.onRegister(fullName, password)
            }
        }
    }

    private fun enteredDataIsNotCorrect(fullName: String, password: String) =
        fullName.isBlank() || password.isBlank() || password.length < 6
}
