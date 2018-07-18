package com.winjit.swiperewards.helpers;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
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
import android.util.Base64;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.winjit.swiperewards.R;
import com.winjit.swiperewards.interfaces.MessageDialogConfirm;

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

    public int animCounter = 0;
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
    public void replaceFragment(FragmentManager fragmentManager, int layout, Fragment fragment, boolean isBackStack) {


        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        if (fragmentManager.findFragmentByTag(fragment.getClass().getSimpleName()) == null) {
//            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.pop_enter, R.anim.pop_exit);
            if (isBackStack) {
                fragmentTransaction.replace(layout, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
            } else {
                fragmentTransaction.replace(layout, fragment).commit();
            }

        } else {

            Fragment requestedFragment = fragmentManager.findFragmentByTag(fragment.getClass().getSimpleName());

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


    public void replaceFragmentAllowLoss(FragmentManager fragmentManager, int layout, Fragment fragment, boolean isBackStack) {


        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        if (fragmentManager.findFragmentByTag(fragment.getClass().getSimpleName()) == null) {
//            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.pop_enter, R.anim.pop_exit);
            if (isBackStack) {
                fragmentTransaction.replace(layout, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
            } else {
                fragmentTransaction.replace(layout, fragment).commit();
            }

        } else {

            Fragment requestedFragment = fragmentManager.findFragmentByTag(fragment.getClass().getSimpleName());

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
     * @param isWithAnimation - With or without animation.
     */
    public void addFragment(FragmentManager fragmentManager, int layout, Fragment fragment, boolean isBackStack, boolean isWithAnimation) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (fragmentManager.findFragmentByTag(fragment.getClass().getSimpleName()) == null) {
            if (isWithAnimation)
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.left_out, R.anim.pop_enter, R.anim.pop_exit);
            if (isBackStack) {
                fragmentTransaction.add(layout, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
            } else {
                fragmentTransaction.add(layout, fragment).commit();
            }

        } else {

            Fragment requestedFragment = fragmentManager.findFragmentByTag(fragment.getClass().getSimpleName());

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

    public void showToast(Context mContext, String string) {
        Toast.makeText(mContext, string, Toast.LENGTH_SHORT).show();
    }


    /**
     * Interface used to toggle drawer navigation button
     */
    public interface OnDrawerInteractionListener {
        void showDrawerToggle(boolean showDrawerToggle);
    }

    /**
     * Interface used to launch fragment
     */
    public interface OnLaunchFragmentListener {
        void launchFragment(Fragment fragment);
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
                if (fragment.getClass().getSimpleName().equalsIgnoreCase("NavigationDrawerFragment") == false &&
                        fragment.getClass().getSimpleName().equalsIgnoreCase("HomeFragment") == false)
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


    public void findFragmentByTag(FragmentManager fragmentManager) {
        for (Fragment fragment : fragmentManager.getFragments()) {
            String tag = fragment.getTag();
            if (tag != null)
                if (tag.equalsIgnoreCase("TreatmentPlanFragment"))
                    removeFragment(fragmentManager, fragment);

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



    public void startAnimation(final ImageView view, final int frequency) {
        if (view == null) {
            return;
        }


        PropertyValuesHolder postX = PropertyValuesHolder.ofFloat("scaleX",
                new float[]{0.9f, 1.05f});
        PropertyValuesHolder postY = PropertyValuesHolder.ofFloat("scaleY",
                new float[]{0.9f, 1.05f});

        final ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(view,
                postX, postY);

        scaleDown.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                scaleDown.removeListener(this);
                view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(300).start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        scaleDown.setDuration(700);
        scaleDown.setRepeatCount(frequency);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);
        scaleDown.start();
    }


    public static void configureShowConfirmDialog(final String message, Context context, int positiveButton, int negativeButton, final MessageDialogConfirm messageDialogConfirm) {
        if (message.trim().length() > 0) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogStyle);
//            builder.setMessage(message);
            TextView myMsg = new TextView(context);
            myMsg.setText(Html.fromHtml(message));
            myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
            int padding = 20;
            int sidePadding = 20;
            myMsg.setPadding(sidePadding, padding, sidePadding, padding);
            myMsg.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0f, context.getResources().getDisplayMetrics()), 1.0f);
            myMsg.setTextAppearance(context, android.R.style.TextAppearance_DeviceDefault_Small);
            builder.setView(myMsg);
            builder.setTitle("Confirm?");
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

}