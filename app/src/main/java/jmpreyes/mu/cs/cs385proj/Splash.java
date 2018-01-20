package jmpreyes.mu.cs.cs385proj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000); // Sleep for 3 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // Intent to open another activity
                    Intent intent = new Intent(Splash.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }
}