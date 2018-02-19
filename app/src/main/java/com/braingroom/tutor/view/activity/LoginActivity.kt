package com.braingroom.tutor.view.activity

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import com.afollestad.materialdialogs.MaterialDialog
import com.braingroom.tutor.R
import com.braingroom.tutor.viewmodel.activity.LoginViewModel
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
import timber.log.Timber
import java.util.*

/*
 * Created by godara on 13/10/17.
*/
class LoginActivity : Activity(), OnConnectionFailedListener {

    interface UIHelper {
        fun fbLogin()
        fun googleLogin()
        fun showForgotPassDialog()


    }

    private lateinit var mFbLogin: LoginButton

    val callbackManager: CallbackManager by lazy {
        CallbackManager.Factory.create()
    }

    val fbLoginManger by lazy {
        LoginManager.getInstance()
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
        mFbLogin.setReadPermissions("public_profile email");
        fbLoginManger.registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        val accessToken = loginResult.accessToken
                        val request = GraphRequest.newMeRequest(accessToken) { user, graphResponse ->
                            Timber.tag(TAG).v(graphResponse.rawResponse)
                            val name = user.optString("name");
                            val picture = user.optJSONObject("picture").optJSONObject("data").optString("url");
                            val email = user.optString("email");
                            val socialId = user.optString("id");
                            vm.socialLogin(name, email, picture, socialId)
                            LoginManager.getInstance().logOut()
                        }
                        val parameters = Bundle()
                        parameters.putString("fields", "id,name,email,picture")
                        request.parameters = parameters
                        request.executeAsync()
                    }

                    override fun onCancel() {
                        messageHelper.showMessage("Login cancelled by user")
                        LoginManager.getInstance().logOut()
                    }

                    override fun onError(exception: FacebookException) {
                        Timber.tag(TAG).e(exception, exception.message)
                        messageHelper.showMessage("Facebook login error")
                        LoginManager.getInstance().logOut()
                    }
                })
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    override val vm: LoginViewModel by lazy {
        LoginViewModel(helperFactory, object : UIHelper {
            override fun showForgotPassDialog() {
                MaterialDialog.Builder(this@LoginActivity)
                        .title("Password recovery")
                        .content("Please enter your email")
                        .inputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                        .inputRange(4, 50)
                        .positiveText("SUBMIT")
                        .input("Email", "", false) { dialog, input ->
                            messageHelper.showProgressDialog("Resetting Password", "Sending Email...")
                            apiService.forgetPassword(input.toString()).subscribe { messageHelper.showMessage(it) }

                        }.show();
            }

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
        if (mGoogleApiClient.isConnected)
            mGoogleApiClient.clearDefaultAccountAndReconnect()
    }

    override val layoutId: Int = R.layout.activity_login


}
