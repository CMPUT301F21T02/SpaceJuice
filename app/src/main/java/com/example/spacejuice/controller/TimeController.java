package com.example.spacejuice.controller;

import android.util.Log;

import com.example.spacejuice.MainActivity;
import com.example.spacejuice.Member;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;

public class TimeController {
    private final static Member user = MainActivity.getUser();


    public static Calendar getCurrentTime() {
        Calendar currentTime = Calendar.getInstance();
        if (user.isAdmin()) {
            long timeInMilli = currentTime.getTimeInMillis();
            long timeOffset = user.getAdminTimeOffset();
            currentTime.setTimeInMillis(timeInMilli + timeOffset);
        }
        return currentTime;
    }

    public static void adminIncrementDay() {
        if (user.isAdmin()) {
            Long offset = user.getAdminTimeOffset();
            offset += 86000000;
            user.setAdminTimeOffset(offset);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String userName = user.getMemberName();
            DocumentReference documentReference = db.collection("Members").document(userName);
            documentReference.update("adminTimeOffset", offset);
        }
    }

    public static int compareCalendarDays(Calendar cal1, Calendar cal2) {
        // returns 0 if two calendar objects are the same day
        // otherwise returns <0 if cal1 is before cal2
        // or returns >0 if cal1 is after cal2

        if (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)) {
            Log.d("debugInfo", (cal1.getTime()).toString() + " and " +
                    (cal2.getTime()).toString() + " occur on the same day");
            return 0;
        }

        Date date1 = cal1.getTime();
        Date date2 = cal2.getTime();
        Log.d("debugInfo", (cal1.getTime()).toString() + " and " +
                (cal2.getTime()).toString() + " do NOT occur on the same day");
        return date1.compareTo(date2);
    }
}