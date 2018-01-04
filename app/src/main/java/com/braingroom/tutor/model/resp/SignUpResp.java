package com.braingroom.tutor.model.resp;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ashketchup on 20/12/17.
 */

public class SignUpResp extends BaseResp {
    @SerializedName("braingroom")
    List<Snippet> data;

    public SignUpResp(List<Snippet> data) {
        this.data = data;
    }

    @Override
    public boolean getResCode() {
        return !isEmpty(data);
    }

    @NonNull
    public Snippet getData() {
        return isEmpty(data) ? new Snippet() : data.get(0);
    }

    public void setData(List<Snippet> data) {
        this.data = data;
    }


    public static class Snippet {

        @SerializedName("uuid")
        public String uuid;

        @SerializedName("user_id")
        String userId;

        @Expose(serialize = false)
        String loginType;

        @Expose(serialize = false)
        String emailId;

        @Expose(serialize = false)
        String mobileNumber;

        @Expose(serialize = false)
        private String password;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getLoginType() {
            return loginType;
        }

        public void setLoginType(String loginType) {
            this.loginType = loginType;
        }

        public String getEmailId() {
            return emailId;
        }

        public void setEmailId(String emailId) {
            this.emailId = emailId;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}