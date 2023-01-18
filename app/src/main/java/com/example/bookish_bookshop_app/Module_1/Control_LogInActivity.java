package com.example.bookish_bookshop_app.Module_1;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookish_bookshop_app.MainActivity;
import com.example.bookish_bookshop_app.R;
import com.example.bookish_bookshop_app.utils.MyOpenHelper;

import java.util.List;

public class Control_LogInActivity extends AppCompatActivity {
    // Global Variables
    public EditText txtUsername;
    public EditText txtPassword;
    public CheckBox chkSession;
    // Database Variables
    public Data_Module_1 data;
    public MyOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_1_activity_log_in);

        // Variables
        txtUsername = (EditText) findViewById(R.id.LogIn_txt_Username);
        txtPassword = (EditText) findViewById(R.id.LogIn_txt_Password);
        chkSession = (CheckBox) findViewById(R.id.LogIn_chk_Session);

        // database connection
        db = new MyOpenHelper(getApplicationContext(), 1);
        data = new Data_Module_1(getApplicationContext(), db);

        // Initialize data in database
        data.insertDataCredential();
        data.insertDataUser();

        // Verifies: if SharedPreferences exist then autologin
        if (loadPreferences()) {
            callActivity();
            // TODO: send credential to UserActivity
        }
    }

    /**
     * Methods--------------------------------------------------------------------------------------
     */

    public void onBtnLogIn(View view) {

        // Variables
        boolean flag = false;
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        boolean session = chkSession.isChecked();

        // Verifies if the credentials match database
        for (Model_Credential element : data.readDataCredentials()) {
            if (username.equals(element.username) && password.equals(element.password)) {
                flag = true;
                break; // para que salga cuando ya encuentre la respuesta
            }
        }

        if (flag) {
            if (session) {
                // if checkbox = true, then saves SharedPreferences
                savePreferences();
                toastMessage("Pereferncias guardadas");
            } else {
                toastMessage("Preferencias NO guardadas");
            }
            callActivity();
            toastMessage("Pereferncias guardadas");
        } else {
            toastMessage("Datos Incorrectos");
        }

    }

    public void onLblSignUp(View view) {
        // Calls SignUpActivity
        startActivity(new Intent(Control_LogInActivity.this, Control_SignUpActivity.class));
        //finish();
    }

    private void toastMessage(String message) {
        // toast a message
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void callActivity() {
        /**
         * Only calls MainActivity
         */
        startActivity(new Intent(Control_LogInActivity.this, MainActivity.class));
        finish();
    }

    // region Methods: SharedPreferences
    private void savePreferences() {
        SharedPreferences preferences = getSharedPreferences("credentials", MODE_PRIVATE);
        String username_pref = txtUsername.getText().toString();
        String password_pref = txtPassword.getText().toString();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", username_pref);
        editor.putString("password", password_pref);

        editor.commit();
    }

    private boolean loadPreferences() {
        /**
         * true: SharedPreferences do exist
         * false: SharedPreferences doesn't exist
         */
        boolean flag = false;

        SharedPreferences preferences = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        String username_temp = preferences.getString("username", "");
        String password_temp = preferences.getString("password", "");

        if (username_temp != "" && password_temp != "") {
            flag = true;
        }

        return flag;
    }
    // endregion

}