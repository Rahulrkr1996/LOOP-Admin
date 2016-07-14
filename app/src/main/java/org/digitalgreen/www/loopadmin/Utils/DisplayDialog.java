package org.digitalgreen.www.loopadmin.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import org.digitalgreen.www.loopadmin.Constants.GeneralConstants;
import org.digitalgreen.www.loopadmin.R;

/**
 * Created by Rahul Kumar on 7/13/2016.
 */
public class DisplayDialog {
    private Dialog dialog;

    public void displayDialog(final Context context, final String message) {

        dialog = new Dialog(context);
        try {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if (message == null)
                return;

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            int layoutId = context.getResources().getIdentifier(layout, "layout", context.getPackageName());

            dialog.setContentView(R.layout.alert_dialog);

            TextView messageTextView = (TextView) dialog.findViewById(R.id.message);
            TextView ok = (TextView) dialog.findViewById(R.id.okButton);

            messageTextView.setText(message);

            messageTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (message.equals(context.getString(R.string.you_have_not_entered_transportation_details_for_mandi)))
                        GeneralConstants.FLAG_USER_NOTIFIED_TRANSPORTATION_DETAILS_NOT_ADDED = true;
                    dialog.dismiss();

                }
            });
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayDialogWithTwoButtons(Context context, String message) {

        dialog = new Dialog(context);
        try {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if (message == null)
                return;

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            int layoutId = context.getResources().getIdentifier(layout, "layout", context.getPackageName());

            dialog.setContentView(R.layout.dialog_with_two_buttons);

            TextView messageTextView = (TextView) dialog.findViewById(R.id.message);
            TextView ok = (TextView) dialog.findViewById(R.id.okButton);
            TextView cancel = (TextView) dialog.findViewById(R.id.cancelButton);

            messageTextView.setText(message);

            messageTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GeneralConstants.FLAG_IS_FARMER_CHANGE_ALLOWED = true;
                    dialog.dismiss();
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GeneralConstants.FLAG_IS_FARMER_CHANGE_ALLOWED = false;
                    dialog.dismiss();
                }
            });
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void displayDialogAndFinishActivityOnOkClick(final Context context, String message, final Activity activity
    ) {

        dialog = new Dialog(context);
        try {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if (message == null)
                return;

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            int layoutId = context.getResources().getIdentifier(layout, "layout", context.getPackageName());

            dialog.setContentView(R.layout.alert_dialog);

            TextView messageTextView = (TextView) dialog.findViewById(R.id.message);
            TextView ok = (TextView) dialog.findViewById(R.id.okButton);

            messageTextView.setText(message);

            messageTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    activity.finish();
                }
            });
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
