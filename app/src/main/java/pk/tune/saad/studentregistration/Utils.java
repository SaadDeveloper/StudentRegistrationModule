package pk.tune.saad.studentregistration;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

public class Utils {

    public static ProgressDialog showProgressDialog(Context context, String message) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        return dialog;
    }
}
