package by.grodno.vasili.instaclone.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView.BufferType.EDITABLE
import by.grodno.vasili.instaclone.R
import by.grodno.vasili.instaclone.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileActivity : AppCompatActivity() {
    private val TAG = this::class.java.simpleName
    private val database = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        Log.d(TAG, "onCreate")

        close_image.setOnClickListener {
            finish()
        }

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            Log.e(TAG, "Not authorized user")
            return
        }
        database.child("users").child(currentUser.uid)
            .addListenerForSingleValueEvent(
                ValueEventListenerAdapter(
                    ::fillProfileFields
                )
            )
    }

    private fun fillProfileFields(data: DataSnapshot) {
        val user = data.getValue(User::class.java)
        if (user == null) {
            Log.e(TAG, "Error converting DataSnapshot ${data.key} to User")
            return
        }
        full_name_input.setText(user.name, EDITABLE)
        username_input.setText(user.username, EDITABLE)
        email_input.setText(user.email, EDITABLE)
        website_input.setText(user.website, EDITABLE)
        phone_input.setText(user.phone, EDITABLE)
        bio_input.setText(user.bio, EDITABLE)
    }
}
