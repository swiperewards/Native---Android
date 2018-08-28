package com.nouvo.rewards.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.nouvo.rewards.R;
import com.nouvo.rewards.interfaces.MessageDialogConfirm;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * A helper class which provides Ui related functions which needed throughout the app.
 *
 * @author VishalB
 */
public class UIHelper {

    public static UIHelper uiHelper;

    public static UIHelper getInstance() {
        if (uiHelper == null)
            uiHelper = new UIHelper();
        return uiHelper;
    }

    /**
     * Method used to add fragment on activity
     *
     * @param fragmentManager
     * @param layout
     * @param fragment
     * @param isBackStack     decide to add fragment with back stack or not.
     */
    public void replaceFragment(FragmentManager fragmentManager, int layout, Fragment fragment, boolean isBackStack, String tagName, String stackName) {

        if (TextUtils.isEmpty(tagName)) {
            tagName = fragment.getClass().getSimpleName();
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        if (fragmentManager.findFragmentByTag(tagName) == null) {
            if (isBackStack) {
                fragmentTransaction.replace(layout, fragment, tagName).addToBackStack(stackName).commit();
            } else {
                fragmentTransaction.replace(layout, fragment).commit();
            }

        } else {
            Fragment requestedFragment = fragmentManager.findFragmentByTag(tagName);
            if (requestedFragment != null) {
                for (Fragment f : fragmentManager.getFragments()) {
                    if (f != null) {
                        if (f == requestedFragment)
                            fragmentTransaction.show(f);
                        else
                            fragmentTransaction.hide(f);
                    }
                }
                fragmentTransaction.commit();
            }
        }
    }


    /**
     * Method used to add fragment on activity
     *
     * @param fragmentManager
     * @param layout
     * @param fragment
     * @param isBackStack
     */
    public void addFragment(FragmentManager fragmentManager, int layout, Fragment fragment, boolean isBackStack, String tagName, String stackName) {
        if (TextUtils.isEmpty(tagName)) {
            tagName = fragment.getClass().getSimpleName();
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (fragmentManager.findFragmentByTag(tagName) == null) {
            if (isBackStack) {
                fragmentTransaction.add(layout, fragment, tagName).addToBackStack(stackName).commit();
            } else {
                fragmentTransaction.add(layout, fragment).commit();
            }

        } else {

            Fragment requestedFragment = fragmentManager.findFragmentByTag(tagName);

            if (requestedFragment != null) {
                for (Fragment f : fragmentManager.getFragments()) {
                    if (f != null) {
                        if (f == requestedFragment)
                            fragmentTransaction.show(f);
                        else
                            fragmentTransaction.hide(f);
                    }
                }
                fragmentTransaction.commit();
            }
        }
    }

    /**
     * Method used to remove all fragments from activity
     *
     * @param fragmentManager
     */
    public void removeAllFragments(FragmentManager fragmentManager) {
//        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
//           fragmentManager.popBackStack();
//        }
        // OR
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void removeFragmentByName(FragmentManager fragmentManager, String name) {
        Fragment fragment = fragmentManager.findFragmentByTag(name);
        if (fragment != null)
            fragmentManager.beginTransaction().remove(fragment).commit();
    }

    public void popFragment(FragmentManager fragmentManager) {
        fragmentManager.popBackStack();
    }

    /**
     * Remove fragment from back stack
     *
     * @param fragmentManager
     * @param fragment        : fragment to remove
     */
    public void removeFragment(FragmentManager fragmentManager, Fragment fragment) {
        FragmentTransaction trans = fragmentManager.beginTransaction();
        // trans.remove(fragment);
        trans.commit();
        fragmentManager.popBackStack();
    }

    /**
     * Function to hide the keyboard.
     *
     * @param mContext
     */
    public void hideKeyboard(Activity mContext) {
        View view = mContext.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String temp = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        return temp;
    }


    public Fragment getCurrentFragment(FragmentManager fragmentManager, int containerId) {
        return fragmentManager.findFragmentById(containerId);
    }

    public void clearBackStack(FragmentManager fragmentManager, String simpleName) {
        for (Fragment fragment : fragmentManager.getFragments()) {
            try {
                if (fragment.getClass().getSimpleName().equalsIgnoreCase("HomeFragment") == false)
                    removeFragment(fragmentManager, fragment);
            } catch (NullPointerException e) {

            }
        }
    }


    public boolean clearBackStackTillFragmentReached(FragmentManager fragmentManager, String fragmentName) {
        boolean clearedFlag = false;
        for (Fragment fragment : fragmentManager.getFragments()) {
            try {
                if (fragment.getClass().getSimpleName().equalsIgnoreCase(fragmentName) == false && fragment.getClass().getSimpleName().equalsIgnoreCase("HomeFragment") == false) {
                    removeFragment(fragmentManager, fragment);
                    clearedFlag = true;
                }
            } catch (NullPointerException e) {
                return false;
            }
        }
        return clearedFlag;
    }


    public void clearBackStackExcludingFragment(FragmentManager fragmentManager, ArrayList<String> fragmentToExclude) {
//        for (int i = fragmentManager.getBackStackEntryCount(); i > 0; i--) {
//            if (fragmentManager.getFragments().get(i) != null)
//                removeFragment(fragmentManager, fragmentManager.getFragments().get(i));
//        }

        for (Fragment fragment : fragmentManager.getFragments()) {
            try {
                if (fragmentToExclude.contains(fragment.getClass().getSimpleName()) == false)
                    removeFragment(fragmentManager, fragment);
            } catch (NullPointerException e) {

            }
        }
    }


    public void saveBitmap(Context context, Bitmap bitmap, String name) {
        try {

            String path = Environment.getExternalStorageDirectory().toString();
            OutputStream fOut = null;
            Integer counter = 0;
            File file = new File(path, name + ".jpg"); // the File to save , append increasing numeric counter to prevent files from getting overwritten.
            fOut = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
            fOut.flush(); // Not really required
            fOut.close(); // do not forget to close the stream

            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());

        } catch (Exception e) {

        }
    }


    public void clearBackStackImmediate(FragmentManager manager) {
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStackImmediate(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public static int getDrawableIdFromResourceName(Context context, String resourceName) {
        return context.getResources().getIdentifier(resourceName, "drawable", context.getPackageName());
    }


    public static void configureShowConfirmDialog(final String message, Context context, int positiveButton, int negativeButton, int title, final MessageDialogConfirm messageDialogConfirm) {
        if (message.trim().length() > 0) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogStyle);
//            builder.setMessage(message);
            TextView myMsg = new TextView(context);
            myMsg.setText(Html.fromHtml(message));
            myMsg.setGravity(Gravity.CENTER_VERTICAL);
            int padding = 20;
            int sidePadding = 20;
            myMsg.setPadding(sidePadding, padding, sidePadding, padding);
            myMsg.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0f, context.getResources().getDisplayMetrics()), 1.0f);
            myMsg.setTextAppearance(context, android.R.style.TextAppearance_DeviceDefault_Small);
            builder.setView(myMsg);
            builder.setTitle(title);
            builder.setCancelable(false);
            builder.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    messageDialogConfirm.onPositiveClick();
                }
            }).setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    messageDialogConfirm.onNegativeClick();
                }
            });
            builder.show();
        }
    }


    public void loadImageOnline(Context context, String url, ImageView target, int defaultImage, int errorImage) {
        if (TextUtils.isEmpty(url) == false) {
            Picasso.with(context)
                    .load(url.replace(" ", "%20"))
                    .placeholder(defaultImage)
                    .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .error(errorImage)
                    .into(target);
        }
    }

    public void loadImage(final Context context, final String url, final ImageView target, final int defaultImage, final int errorImage) {
        if (TextUtils.isEmpty(url) == false) {
            Picasso.with(context)
                    .load(url.replace(" ", "%20"))
                    .placeholder(defaultImage)
                    .into(target, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError() {
                            Picasso.with(context)
                                    .load(url.replace(" ", "%20"))
                                    .placeholder(defaultImage)
                                    .networkPolicy(NetworkPolicy.OFFLINE)
                                    .error(errorImage)
                                    .into(target);
                        }
                    });
        }
    }

    public void popBackStackByName(FragmentManager fragmentManager, String BACK_STACK_ROOT_TAG) {
        fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

}