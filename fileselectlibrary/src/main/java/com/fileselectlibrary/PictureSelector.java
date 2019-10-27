package com.fileselectlibrary;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.fileselectlibrary.utils.DoubleClickUtils;

import java.lang.ref.WeakReference;

public final class PictureSelector {

    private final WeakReference<Activity> mActivity;
    private final WeakReference<Fragment> mFragment;

    private PictureSelector(Activity activity) {
        this(activity, null);
    }

    private PictureSelector(Fragment fragment) {
        this(fragment.getActivity(), fragment);
    }

    private PictureSelector(Activity activity, Fragment fragment) {
        mActivity = new WeakReference<>(activity);
        mFragment = new WeakReference<>(fragment);
    }

    /**
     * Start PictureSelector for Activity.
     */
    public static PictureSelector create(Activity activity) {
        return new PictureSelector(activity);
    }

    /**
     * Start PictureSelector for Fragment.
     */
    public static PictureSelector create(Fragment fragment) {
        return new PictureSelector(fragment);
    }
    private void forResult(int requestCode){
        if(!DoubleClickUtils.isFastDoubleClick()){
            Activity activity = getActivity();
            Fragment fragment = getFragment();
            Intent intent = new Intent(activity, PictureSelectorActivity.class);
            if(fragment!=null){
                fragment.startActivityForResult(intent, requestCode);
            }else if(activity!=null) {
                activity.startActivityForResult(intent, requestCode);
            }
        }
    }
    /**
     * @return Activity.
     */
    @Nullable
    Activity getActivity() {
        return mActivity != null ? mActivity.get() : null;
    }

    /**
     * @return Fragment.
     */
    @Nullable
    Fragment getFragment() {
        return mFragment != null ? mFragment.get() : null;
    }
}
