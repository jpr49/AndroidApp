package jmpreyes.mu.cs.cs385proj;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private EditText username, password;
    private Button login, register;
    private String input_username, input_password;

    private void setInputUsername(String u) {
        input_username = u;
    }

    private void setInputPassword(String p) {
        input_password = p;
    }

    public String getInputUsername() {
        return input_username;
    }

    public String getInputPassword() {
        return input_password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.text_username);
        password = (EditText)findViewById(R.id.text_password);
        login = (Button)findViewById(R.id.btn_login);
        register = (Button)findViewById(R.id.btn_register);

        login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setInputUsername(username.getText().toString());
                    setInputPassword(password.getText().toString());

                    if (getInputUsername().contains("admin") && getInputPassword().contains("password")) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.login_success).toString(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, MainDashboard.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.login_failed).toString(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
        });

        register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.new_registration).toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, Register.class);
                    startActivity(intent);
                }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage(getResources().getString(R.string.ask_toCloseMessage))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Login.this.finish();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no), null)
                .show();
    }
}