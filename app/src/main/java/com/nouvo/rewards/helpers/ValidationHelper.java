package com.nouvo.rewards.helpers;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.nouvo.rewards.R;


/**
 * Class to provide validation related functionalities which needed throughout the app.
 *
 * @author VishalB
 */
public class ValidationHelper {

    /**
     * Method used to check empty view
     *
     * @param context  : App context
     * @param editText : view to in which text inserted
     * @return false if not empty
     */
    public boolean isEmpty(Context context, EditText editText) {
        boolean check = TextUtils.isEmpty(editText.getText().toString().trim());
        if (check) {
            editText.setError(context.getString(R.string.error_validation_empty));
//            editText.requestFocus();
            return true;
        }
        return false;
    }

    public boolean isValidEditTexts(Context context, EditText... editTexts) {
        boolean isEditTextsValid = true;
        for (int i = 0; i < editTexts.length; i++) {
            EditText editText = editTexts[i];

            boolean check = TextUtils.isEmpty(editText.getText().toString().trim());
            if (check) {
                editText.setError(context.getString(R.string.error_validation_empty));
                isEditTextsValid = false;
            }
        }
        return isEditTextsValid;
    }

    /**
     * Method used to validate email id
     *
     * @param context  : App context
     * @param editText : view to in which email inserted
     */
    public boolean isValidEmail(Context context, EditText editText) {
        boolean check;
        if (!isEmpty(context, editText)) {
            check = android.util.Patterns.EMAIL_ADDRESS.matcher(editText.getText().toString().trim()).matches();
            if (!check) {
                editText.setError(context.getString(R.string.error_validation_email));
                return false;
            } else
                return true;
        }
        return false;
    }

    /**
     * Method used to validate mobile number
     *
     * @param context  : App context
     * @param editText : view to in which mobile number inserted
     */
    public boolean isValidMobile(Context context, EditText editText) {
        boolean check;
        check = isEmpty(context, editText);
        if (!check) {
            check = android.util.Patterns.PHONE.matcher(editText.getText().toString().trim()).matches();
            if (check) {
                return true;
//                if (editText.getText().toString().length() == 14)
//                    return true;
//                else {
//                    editText.setError(context.getString(R.string.error_validation_mobile));
//                    return false;
//                }
            } else {
                editText.setError(context.getString(R.string.error_validation_mobile));
                return false;
            }
        } else
            return false;

    }

    /**
     * Method used to check editText only contains digits
     *
     * @param context  : App context
     * @param editText : view to in which text inserted
     * @return
     */
    public boolean isDigitsOnly(Context context, EditText editText) {
        if (!isEmpty(context, editText)) {
            String text = editText.getText().toString();
            if (text.matches("[0-9]+") && text.length() > 2) {
                return true;
            }
            return false;
        }
        return false;
    }


    /**
     * Get Special character filter
     *
     * @return
     */
    public InputFilter getInputFilter() {

        InputFilter filter = new InputFilter() {

            String blockCharacterSet = "★☆♥☏✹✃✈✉☺☻™✽ッツ♨✆~`!@#$%^&*()_-+=|';:,.<>/?£€\\";

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                if (source != null && blockCharacterSet.contains(("" + source))) {
                    return "";
                }
                return null;
            }
        };

        return filter;
    }

    public String convertToCamelCase(String source) {
        StringBuffer res = new StringBuffer();

        String[] strArr = source.split(" ");
        for (String str : strArr) {
            char[] stringArray = str.trim().toCharArray();
            stringArray[0] = Character.toUpperCase(stringArray[0]);
            str = new String(stringArray);

            res.append(str).append(" ");
        }
        return res.toString().trim();
    }

    /**
     * Method used to restrict user to add white space
     *
     * @param editText
     */
    public void ignoreWhiteSpace(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String result = s.toString().replaceAll(" ", "");
                if (!s.toString().equals(result)) {
                    editText.setText(result);
                    editText.setSelection(result.length());
                }
            }
        });
    }

    public static final String SPECIAL_CHARACTERS = "!@#$%^&*()~`-=_+[]{}|:\";',./<>?";
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 14;

    public boolean isAcceptablePassword(Context context, EditText editText) {
        String password = editText.getText().toString();

        int len = password.length();
        int validationCount = 0;
        if (TextUtils.isEmpty(password) || password.contains(" ") || len < MIN_PASSWORD_LENGTH || len > MAX_PASSWORD_LENGTH) {
            editText.setError(context.getString(R.string.err_password_pattern));
            return false;
        }

        boolean hasUppercase = !password.equals(password.toLowerCase());
//        boolean hasLowercase = !password.equals(password.toUpperCase());
        boolean hasSpecial = !password.matches("[A-Za-z0-9 ]*");//Checks at least one char is not alpha numeric
        boolean hasDigit = password.matches(".*\\d.*");

        if (hasUppercase) {
            validationCount++;
        } else {
            editText.setError(context.getString(R.string.err_password_uppercase));
        }
//        if (hasLowercase)
//            validationCount++;
        if (hasSpecial) {
            validationCount++;
        } else {
            editText.setError(context.getString(R.string.err_password_special_character));
        }
        if (hasDigit) {
            validationCount++;
        } else {
            editText.setError(context.getString(R.string.err_password_digit));
        }

        if (validationCount > 2)
            return true;
        return false;

    }

    public boolean isPasswordMatch(Context context, EditText editPassword, EditText editConfirmPassword) {

        if (editConfirmPassword.getText().toString().equals(editPassword.getText().toString())) {
            return true;
        }
        Toast.makeText(context, "Password and confirm password should be same!", Toast.LENGTH_SHORT).show();
        return false;

    }


}