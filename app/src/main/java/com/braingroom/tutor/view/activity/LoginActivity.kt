package com.braingroom.tutor.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.braingroom.tutor.R
import com.braingroom.tutor.viewmodel.activity.LoginViewModel
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.facebook.login.widget.LoginButton
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginResult
import com.facebook.FacebookCallback
import com.facebook.login.LoginManager
import com.facebook.CallbackManager

/*
 * Created by godara on 13/10/17.
*/
class LoginActivity : Activity(), OnConnectionFailedListener {

    interface UIHelper {
        fun fbLogin()
        fun googleLogin()
    }

    private lateinit var mFbLogin: LoginButton

    val callbackManager: CallbackManager by lazy {
        CallbackManager.Factory.create()
    }

    private val RC_SIGN_IN = 9001
    private val googleSignInOptions: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
    }
    private val mGoogleApiClient: GoogleApiClient by lazy {
        GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi<GoogleSignInOptions>(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFbLogin = findViewById(R.id.fb_login_button)
        LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        val accessToken = loginResult.accessToken
                        val request = GraphRequest.newMeRequest(accessToken) { user, graphResponse ->
                            Log.v(TAG, graphResponse.rawResponse)
                            val name = user.optString("name");
                            val picture = user.optJSONObject("picture").optJSONObject("data").optString("url");
                            var email = user.optString("email");
                            val socialId = user.optString("id");
                            if (email.isNullOrBlank())
                                email = "bgdemovendor2@gmail.com"
                            vm.socialLogin(name, email, picture, socialId)
                        }
                        val parameters = Bundle()
                        parameters.putString("fields", "id,name,email,picture")
                        request.parameters = parameters
                        request.executeAsync()
                    }

                    override fun onCancel() {
                        messageHelper.showDismissInfo("Login cancelled by user")
                    }

                    override fun onError(exception: FacebookException) {
                        Log.e(TAG, "onError: " + exception.message, exception)
                        exception.printStackTrace()
                        messageHelper.showDismissInfo("Facebook login error")
                    }
                })
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    override val vm: LoginViewModel by lazy {
        LoginViewModel(object : UIHelper {
            override fun fbLogin() {
                mFbLogin.performClick()
            }

            override fun googleLogin() {
                startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient), RC_SIGN_IN)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    override val layoutId: Int = R.layout.activity_login
}
