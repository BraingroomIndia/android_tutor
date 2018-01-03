
package com.braingroom.tutor.model.resp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collections;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import static com.braingroom.tutor.utils.CommonUtilsKt.getNonNull;

@SuppressWarnings("unused")
public class LoginResp extends BaseResp {

    @SerializedName("braingroom")
    private List<Snippet> data;

    @Override
    public boolean getResCode() {
        return !isEmpty(data);
    }

    public static class Snippet {

        @SerializedName("id")
        private String mId;
        @SerializedName("name")
        private String mName;
        @SerializedName("profile_pic")
        private String mProfilePic;
        @SerializedName("mobile")
        private String mMobile;
        @SerializedName("city_id")
        private String mCityId;
        @SerializedName("is_mobile_verified")
        private int mIsMobileVerified;
        @SerializedName("referal_code")
        private String mReferralCode;
        @SerializedName("uuid")
        private String mUuid;

        @SerializedName("email_id")
        private String mEmailId;


        @NonNull
        public String getCityId() {
            return getNonNull(mCityId);
        }

        @NonNull
        public String getUserId() {
            return getNonNull(mId);
        }

        public boolean getIsMobileVerified() {
            return mIsMobileVerified == 1;
        }

        @NonNull
        public String getMobile() {
            return getNonNull(mMobile);
        }

        @NonNull
        public String getName() {
            return getNonNull(mName);
        }

        @NonNull
        public String getEmailId() {
            return getNonNull(mEmailId);
        }

        @NonNull
        public String getProfilePic() {
            return getNonNull(mProfilePic);
        }

        @NonNull
        public String getReferralCode() {
            return getNonNull(mReferralCode);
        }

        @NonNull
        public String getUuid() {
            return getNonNull(mUuid);
        }


    }

    @NonNull
    public Snippet getData() {
        return isEmpty(data) ? new Snippet() : data.get(0);
    }


}
