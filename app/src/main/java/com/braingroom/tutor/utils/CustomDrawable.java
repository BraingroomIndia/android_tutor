package com.braingroom.tutor.utils;

import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.braingroom.tutor.R;
import com.braingroom.tutor.common.CustomApplication;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/*
 * Created by godara on 30/10/17.
*/

@SuppressWarnings("unused")
public class CustomDrawable extends ObservableField<Drawable> implements Target {

    private final Integer color;

    public CustomDrawable(@Nullable String imageUrl) {
        super();
        this.color = null;
        if (!TextUtils.isEmpty(imageUrl) && CustomApplication.getInstance() != null && CustomApplication.getInstance().getAppModule() != null) {
            CustomApplication.getInstance().getAppModule().getPicasso().load(imageUrl).into(this);
        }
    }

    public CustomDrawable(@Nullable String imageUrl, @NonNull Integer color) {
        super();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            this.color = CustomApplication.getInstance().getColor(color);
        else this.color = CustomApplication.getInstance().getResources().getColor(color);
        if (!TextUtils.isEmpty(imageUrl) && CustomApplication.getInstance() != null && CustomApplication.getInstance().getAppModule() != null) {
            CustomApplication.getInstance().getAppModule().getPicasso().load(imageUrl).into(this);
        }
    }


    public CustomDrawable(@DrawableRes int resource) {
        super();
        this.color = null;
        if (CustomApplication.getInstance() != null && CustomApplication.getInstance().getAppModule() != null) {
            CustomApplication.getInstance().getAppModule().getPicasso().load(resource).into(this);
        }
    }

    public CustomDrawable(@DrawableRes int resource, @NonNull Integer color) {
        super();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            this.color = CustomApplication.getInstance().getColor(color);
        else this.color = CustomApplication.getInstance().getResources().getColor(color);
        if (CustomApplication.getInstance() != null && CustomApplication.getInstance().getAppModule() != null) {
            CustomApplication.getInstance().getAppModule().getPicasso().load(resource).error(resource).placeholder(resource).into(this);
        }
    }

    public CustomDrawable(@Nullable String imageUrl, @DrawableRes int placeHolder) {
        super();
        this.color = null;
        if (CustomApplication.getInstance() != null && CustomApplication.getInstance().getAppModule() != null) {
            if (!TextUtils.isEmpty(imageUrl)) {
                CustomApplication.getInstance().getAppModule().getPicasso().load(imageUrl).placeholder(placeHolder).error(placeHolder).into(this);
            } else {
                CustomApplication.getInstance().getAppModule().getPicasso().load(placeHolder).into(this);
            }
        }
    }

    public CustomDrawable(@Nullable String imageUrl, @DrawableRes int placeHolder, @NonNull Integer color) {
        super();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            this.color = CustomApplication.getInstance().getColor(color);
        else this.color = CustomApplication.getInstance().getResources().getColor(color);
        if (CustomApplication.getInstance() != null && CustomApplication.getInstance().getAppModule() != null) {
            if (!TextUtils.isEmpty(imageUrl)) {
                CustomApplication.getInstance().getAppModule().getPicasso().load(imageUrl).placeholder(placeHolder).error(placeHolder).into(this);
            } else {
                CustomApplication.getInstance().getAppModule().getPicasso().load(placeHolder).into(this);
            }
        }
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        if (bitmap != null)
            set(new BitmapDrawable(CustomApplication.getInstance().getResources(), bitmap));

    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        if (errorDrawable != null)
            set(errorDrawable);

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        if (placeHolderDrawable != null)
            set(placeHolderDrawable);

    }

    @Override
    @Nullable
    public Drawable get() {
        final Drawable drawable = super.get();
        if (drawable != null && color != null) {
            if (drawable instanceof GradientDrawable)
                ((GradientDrawable) drawable).setColor(color);
        }
        return drawable;
    }
}
