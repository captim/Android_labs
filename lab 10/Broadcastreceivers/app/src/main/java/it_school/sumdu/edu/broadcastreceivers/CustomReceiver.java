package it_school.sumdu.edu.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CustomReceiver extends BroadcastReceiver {
    private static final String EXTRA_RANDOM_NUMBER = "it_school.sumdu.edu.broadcastreceivers.RANDOM_NUMBER";
    private static final String ACTION_CUSTOM_BROADCAST = ".ACTION_CUSTOM_BROADCAST";

    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();
        String stringNumber = intent.getStringExtra(EXTRA_RANDOM_NUMBER);
        int number = Integer.parseInt(stringNumber);
        if (intentAction != null) {
            String toastMessage = context.getResources().getString(R.string.unknown_intent_action);
            if (ACTION_CUSTOM_BROADCAST.equals(intentAction)) {
                toastMessage = context.getResources().getString(R.string.custom_broadcast_received);
            }
            Toast.makeText(context, toastMessage + " Square of the random number: " + number * number, Toast.LENGTH_LONG).show();
        }
    }
}