package com.wireless.stickie.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.wireless.stickie.data.model.LoggedInUser
import java.io.IOException
import org.jetbrains.anko.toast


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    var mAuth: FirebaseAuth? = null
    var mAuthListener: FirebaseAuth.AuthStateListener? = null
    private val TAG: String = "Login Data Source"

    fun login(username: String, password: String): Result<LoggedInUser> {

        try {
            // TODO: handle loggedInUser authentication

            mAuth = FirebaseAuth.getInstance()

            val user = mAuth!!.currentUser

            val fakeUser = LoggedInUser(user!!.email.toString())
            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
        mAuth!!.signOut()
//        toast("Signed Out")
        Log.d(TAG,"Signed Out")
    }
}

