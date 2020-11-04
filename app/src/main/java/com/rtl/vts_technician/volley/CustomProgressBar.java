package com.rtl.vts_technician.Volley;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.cardview.widget.CardView;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rtl.vts_technician.R;


/**
 * Created by Diwash Choudhary on 30-01-2020.
 */

public class CustomProgressBar {

    private static Dialog mDialog;

    public static Dialog showCustomProgressDialog(Context context){
        return showCustomProgressDialog(context, null);
    }

    public static Dialog showCustomProgressDialog(Context context, String title){
        mDialog = new Dialog(context, R.style.CustomProgressBarTheme);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        mDialog.setContentView(R.layout.custom_layout);

        CardView cp_cardview =  mDialog.findViewById(R.id.cp_cardview);
        ConstraintLayout cp_bg_view = mDialog.findViewById(R.id.cp_bg_view);
        ProgressBar cp_pbar = mDialog.findViewById(R.id.cp_pbar);
        TextView cp_title = mDialog.findViewById(R.id.cp_title);

        cp_bg_view.setBackgroundColor(Color.parseColor("#60000000")); //Background Color
        cp_cardview.setCardBackgroundColor(Color.parseColor("#70000000")); //Box Color
        setColorFilter(cp_pbar.getIndeterminateDrawable(), ResourcesCompat.getColor(context.getResources(), R.color.white, null));//Progress Bar Color
        cp_title.setTextColor(Color.WHITE);//Text Color

        if (title != null){
            cp_title.setText(title);
        }

        if(mDialog != null && mDialog.isShowing() == false) {
            mDialog.show();
        }

        return mDialog;
    }

    public static void setColorFilter(@NonNull Drawable drawable, int color){
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    //custom progress close
    public static void removeCustomProgressDialog() {
        try {
            if (mDialog != null) {
                mDialog.dismiss();
                mDialog = null;
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
