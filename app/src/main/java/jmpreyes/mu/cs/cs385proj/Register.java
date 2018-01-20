package jmpreyes.mu.cs.cs385proj;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    private EditText firstName, lastName, userName, password, confirmPW;
    private Button completeReg, cancelReg;

    private SharedPreferences user_list;
    private SharedPreferences.Editor editor;

    public final int PW_MINIMUM = 6;
    public final String MY_PREFS = "UserPreferences";
    public final String KEY_FNAME = "FirstName";
    public final String KEY_LNAME = "LastName";
    public final String KEY_UNAME = "UserName";
    public final String KEY_PASSWORD = "Password";
    public final String KEY_CONFIRMPW = "ConfirmPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstName = (EditText)findViewById(R.id.text_regFirstName);
        lastName = (EditText)findViewById(R.id.text_regLastName);
        userName = (EditText)findViewById(R.id.text_regUserName);
        password = (EditText)findViewById(R.id.text_regPassword);
        confirmPW = (EditText)findViewById(R.id.text_regConfirmPW);
        completeReg = (Button)findViewById(R.id.btn_regFinishReg);
        cancelReg = (Button)findViewById(R.id.btn_regCancelReg);

        completeReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isValid()) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.register_failed).toString(), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    user_list = getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
                    editor = user_list.edit();
                    editor.putString(KEY_FNAME, firstName.getText().toString());
                    editor.putString(KEY_LNAME, lastName.getText().toString());
                    editor.putString(KEY_UNAME, userName.getText().toString());
                    editor.putString(KEY_PASSWORD, password.getText().toString());
                    editor.putString(KEY_CONFIRMPW, confirmPW.getText().toString());
                    editor.apply();
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.register_success).toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Register.this, MainDashboard.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        cancelReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(Register.this)
                    .setMessage(getResources().getString(R.string.ask_toCancelRegMessage))
                    .setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Register.this.finish();
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.no), null)
                    .show();
            }
        });
    }

    private boolean isValid() {
        boolean valid = true;
        if (firstName.getText().toString().isEmpty()) {
            firstName.setError(getResources().getString(R.string.empty_field));
            valid = false;
        } else if (lastName.getText().toString().isEmpty()) {
            lastName.setError(getResources().getString(R.string.empty_field));
            valid = false;
        } else if (userName.getText().toString().isEmpty()) {
            userName.setError(getResources().getString(R.string.empty_field));
            valid = false;
        } else if (password.getText().toString().isEmpty()) {
            password.setError(getResources().getString(R.string.empty_field));
            valid = false;
        } else if (confirmPW.getText().toString().isEmpty()) {
            confirmPW.setError(getResources().getString(R.string.empty_field));
            valid = false;
        } else if (password.getText().toString().length() < PW_MINIMUM && password.getText().toString().length() > 0) {
            password.setError(getResources().getString(R.string.invalid_password));
            valid = false;
        } else if (confirmPW.getText().toString().length() < PW_MINIMUM && confirmPW.getText().toString().length() > 0) {
            confirmPW.setError(getResources().getString(R.string.invalid_password));
            valid = false;
        } else if (!password.getText().toString().equals(confirmPW.getText().toString())) {
            password.setError(getResources().getString(R.string.passwords_notEqual));
            confirmPW.setError(getResources().getString(R.string.passwords_notEqual));
            valid = false;
        }
        return valid;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage(getResources().getString(R.string.ask_toCancelRegMessage))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Register.this.finish();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no), null)
                .show();
    }
}