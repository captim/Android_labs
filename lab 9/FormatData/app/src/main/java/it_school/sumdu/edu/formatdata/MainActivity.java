package it_school.sumdu.edu.formatdata;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * This app demonstrates how to localize an app with text, an image,
 * a floating action button, an options menu, and the app bar.
 */
public class MainActivity extends AppCompatActivity {

    private int mInputQuantity = 1;

    private final NumberFormat mNumberFormat = NumberFormat.getInstance();

    private static final String TAG = MainActivity.class.getSimpleName();

    private double mPrice = 1.10;


    private NumberFormat mCurrencyFormat = NumberFormat.getCurrencyInstance();

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> showHelp());

        final Date myDate = new Date();
        final long expirationDate = myDate.getTime() + TimeUnit.DAYS.toMillis(5);
        myDate.setTime(expirationDate);

        String myFormattedDate = DateFormat.getDateInstance().format(myDate);
        TextView expirationDateView = findViewById(R.id.date);
        expirationDateView.setText(myFormattedDate);

        String myFormattedPrice;
        String deviceLocale = Locale.getDefault().getCountry();

        if (deviceLocale.equals("FR") || deviceLocale.equals("IL")) {
            if (deviceLocale.equals("FR")) {
                double mFrExchangeRate = 0.87;
                mPrice *= mFrExchangeRate;
            } else {
                double mIwExchangeRate = 3.62;
                mPrice *= mIwExchangeRate;
            }
        } else {
            mCurrencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        }
        myFormattedPrice = mCurrencyFormat.format(mPrice);


        TextView localePrice = findViewById(R.id.price);
        localePrice.setText(myFormattedPrice);

        final EditText enteredQuantity = findViewById(R.id.quantity);

        enteredQuantity.setOnEditorActionListener
                ((v, actionId, event) -> {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        InputMethodManager imm = (InputMethodManager)
                                v.getContext().getSystemService
                                        (Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        try {
                            mInputQuantity = Objects.requireNonNull(mNumberFormat.parse(v.getText()
                                    .toString())).intValue();
                            v.setError(null);
                        } catch (ParseException e) {
                            Log.e(TAG, Log.getStackTraceString(e));
                            v.setError(getText(R.string.enter_number));
                            return false;
                        }

                        String myFormattedQuantity =
                                mNumberFormat.format(mInputQuantity);
                        v.setText(myFormattedQuantity);

                        calculateTotalAmount();

                        return true;
                    }
                    return false;
                });

        Button calculateButton = findViewById(R.id.calculate_button);
        calculateButton.setOnClickListener(view -> calculateTotalAmount());
    }

    private void calculateTotalAmount() {
        TextView total_amount = findViewById(R.id.total);
        double myTotalAmount = mPrice * mInputQuantity;
        total_amount.setText(mCurrencyFormat.format(myTotalAmount));
    }

    private void showHelp() {
        // Create the intent.
        Intent helpIntent = new Intent(this, HelpActivity.class);
        // Start the HelpActivity.
        startActivity(helpIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((EditText) findViewById(R.id.quantity)).getText().clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle options menu item clicks here.
        switch (item.getItemId()) {
            case R.id.action_help:
                showHelp();
                return true;
            case R.id.action_language:
                Intent languageIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(languageIntent);
                return true;
            default:
                // Do nothing
        }
        return super.onOptionsItemSelected(item);
    }
}