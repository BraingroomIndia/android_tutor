package com.braingroom.tutor.viewmodel.activity;

import android.content.Intent;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.Log;

import com.braingroom.tutor.R;
import com.braingroom.tutor.common.CustomApplication;
import com.braingroom.tutor.model.req.LoginReq;
import com.braingroom.tutor.model.req.SocialLoginReq;
import com.braingroom.tutor.model.resp.LoginResp;
import com.braingroom.tutor.utils.CustomDrawable;
import com.braingroom.tutor.view.activity.LoginActivity.UIHelper;
import com.braingroom.tutor.viewmodel.ViewModel;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import org.jetbrains.annotations.Nullable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;


import static com.braingroom.tutor.utils.CommonUtilsKt.isEmpty;
import static com.braingroom.tutor.utils.CommonUtilsKt.isValidEmail;
import static com.braingroom.tutor.utils.ConstantsKt.braingroomId;
import static com.braingroom.tutor.utils.ConstantsKt.email;
import static com.braingroom.tutor.utils.ConstantsKt.lodgedIn;
import static com.braingroom.tutor.utils.ConstantsKt.name;
import static com.braingroom.tutor.utils.ConstantsKt.profilePic;

/*
 * Created by godara on 13/10/17.
 */

public class LoginViewModel extends ViewModel {
    public final ObservableField<String> emailId = new ObservableField<>("");
    public final ObservableField<String> password = new ObservableField<>("");
    public final CustomDrawable loginButton;
    private final int RC_SIGN_IN = 9001;
    private final UIHelper uiHelper;
    public final Action onLoginClicked = new Action() {
        @Override
        public void run() throws Exception {
            login(emailId.get(), password.get());
        }
    };
    public final Action onGoogleLoginClicked = new Action() {
        @Override
        public void run() throws Exception {
            uiHelper.googleLogin();
        }
    };

    public final Action onFbLoginClicked = new Action() {
        @Override
        public void run() throws Exception {
            uiHelper.fbLogin();
        }
    };


    public LoginViewModel(UIHelper uiHelper) {
        loginButton = new CustomDrawable(R.drawable.rounded_corner_line, R.color.material_deeporange600);
        this.uiHelper = uiHelper;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                if (account != null && account.getDisplayName() != null && account.getEmail() != null && account.getId() != null)
                    socialLogin(account.getDisplayName(), account.getEmail(), account.getPhotoUrl() != null ? account.getPhotoUrl().toString() : "", account.getId());
            }
        }
    }


    public void socialLogin(@NonNull String name, @NonNull String emailId, @NonNull String profilePicture, @NonNull String socialId) {
        getApiService().login(new SocialLoginReq.Snippet(name, "", emailId, socialId)).
                doOnSubscribe(disposable -> getCompositeDisposable().add(disposable)).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(this::handleLoginResponse, throwable -> {
                    Log.d(getTAG(), throwable.getMessage());
                    throwable.printStackTrace();
                });
    }

    public void login(String emailId, String password) {
        if (isValidEmail(emailId) && !isEmpty(password)) {
            if (getMessageHelper() != null)
                getMessageHelper().showProgressDialog("Logging in", "Sit back while we connect you...");
            getApiService().login(new LoginReq.Snippet(emailId, password)).
                    doOnSubscribe(disposable -> getCompositeDisposable().add(disposable)).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(this::handleLoginResponse, throwable -> {
                        Log.d(getTAG(), throwable.getMessage());
                        throwable.printStackTrace();
                    });
        }

    }


    private void handleLoginResponse(LoginResp resp) {
        if (resp.getResCode()) {
            LoginResp.Snippet data = resp.getData();
            if (getMessageHelper() != null)
                getMessageHelper().dismissActiveProgress();
            if (getNavigator() != null)
                getNavigator().finishActivity(new Intent(), loginSuccess(data.getName(), data.getEmailId(), data.getProfilePic(), data.getUserId()));
        } else {
            if (getMessageHelper() != null)
                getMessageHelper().showDismissInfo(resp.getResMsg());
        }

    }

    private boolean loginSuccess(@NonNull String userName, @NonNull String emailId,
                                 @NonNull String profilePicture, @NonNull String userId) {
        try {
            setLoggedIn(true);
            getPreferencesEditor().putBoolean(lodgedIn, true);
            getPreferencesEditor().putString(name, userName);
            getPreferencesEditor().putString(email, emailId);
            getPreferencesEditor().putString(profilePic, profilePicture);
            getPreferencesEditor().putString(braingroomId, userId);
            getPreferencesEditor().commit();
            CustomApplication.getInstance().userEmail = emailId;
            CustomApplication.getInstance().userId = userId;
            CustomApplication.getInstance().userName = userName;
            CustomApplication.getInstance().userPic = profilePicture;
        } catch (Exception e) {
            return false;
        }
        return true;


    }
}
