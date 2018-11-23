package by.grodno.vasili.instaclone.activities

import android.content.Context
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ValueEventListenerAdapter(val handler: (DataSnapshot) -> Unit) : ValueEventListener {
    private val TAG = this::class.java.simpleName

    override fun onCancelled(error: DatabaseError) {
        Log.e(TAG, "Error receiving data from Firebase database", error.toException())
    }

    override fun onDataChange(data: DataSnapshot) {
        handler(data)
    }
}

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}

fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(activity, message, duration).show()
}

fun String.isValidEmail(): Boolean =
    this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun disableButtonForEmptyInputs(button: Button, vararg inputs: EditText) {
    button.isEnabled = inputs.all { it.text.toString().isNotBlank() }
    inputs.forEach { it.addTextChangedListener(object: TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            button.isEnabled = inputs.all { it.text.toString().isNotBlank() }
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }) }
}