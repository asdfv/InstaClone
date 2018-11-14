package by.grodno.vasili.instaclone

import android.util.Log
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
