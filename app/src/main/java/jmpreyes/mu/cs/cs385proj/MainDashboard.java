package jmpreyes.mu.cs.cs385proj;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class MainDashboard extends AppCompatActivity {
    private final String FORMAT = "#.##";
    private DecimalFormat df = new DecimalFormat(FORMAT);
    private EditText moStuAidInc, moEmpInc, moSavInc, moOthInc;
    private EditText moRentExp, moFoodExp, moTransportExp, moPersonalExp;
    private Button calcInc, calcExp, summary;
    private TextView tv_monthlyInc, tv_monthlyExp;
    private double moStuAidDbl, moEmpIncDbl, moSavIncDbl, moOthIncDbl;
    private double moRentDbl, moFoodDbl, moTransportDbl, moPersonalDbl;
    private double monthlyIncome, monthlyExpenses;

    private void setMonthlyIncome(double d) {
        monthlyIncome = d;
    }

    private void setMonthlyExpenses(double d) {
        monthlyExpenses = d;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public double getMonthlyExpenses() {
        return monthlyExpenses;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);

        moStuAidInc = (EditText)findViewById(R.id.text_moStuAidInc);
        moEmpInc = (EditText)findViewById(R.id.text_moEmpInc);
        moSavInc = (EditText)findViewById(R.id.text_moSavInc);
        moOthInc = (EditText)findViewById(R.id.text_moOthInc);

        moRentExp = (EditText)findViewById(R.id.text_moRentExp);
        moFoodExp = (EditText)findViewById(R.id.text_moFoodExp);
        moTransportExp = (EditText)findViewById(R.id.text_moTransportExp);
        moPersonalExp = (EditText)findViewById(R.id.text_moPersonalExp);

        calcInc = (Button)findViewById(R.id.calc_totalInc);
        calcExp = (Button)findViewById(R.id.calc_totalExp);
        summary = (Button)findViewById(R.id.btn_summary);

        tv_monthlyInc = (TextView)findViewById(R.id.text_showMonthlyIncTotal);
        tv_monthlyExp = (TextView)findViewById(R.id.text_showMonthlyExp);

        calcInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (moStuAidInc.getText().toString().isEmpty()) {
                    moStuAidDbl = 0.00;
                } else {
                    moStuAidDbl = Double.parseDouble(moStuAidInc.getText().toString());
                }
                if (moEmpInc.getText().toString().isEmpty()) {
                    moEmpIncDbl = 0.00;
                } else {
                    moEmpIncDbl = Double.parseDouble(moEmpInc.getText().toString());
                }
                if (moSavInc.getText().toString().isEmpty()) {
                    moSavIncDbl = 0.00;
                } else {
                    moSavIncDbl = Double.parseDouble(moSavInc.getText().toString());
                }
                if (moOthInc.getText().toString().isEmpty()) {
                    moOthIncDbl = 0.00;
                } else {
                    moOthIncDbl = Double.parseDouble(moOthInc.getText().toString());
                }
                double monthlySum = moStuAidDbl + moEmpIncDbl + moSavIncDbl + moOthIncDbl;
                setMonthlyIncome(monthlySum);
                tv_monthlyInc.setText(getResources().getString(R.string.showMonthlyIncome) + df.format(monthlySum));
            }
        });

        calcExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (moRentExp.getText().toString().isEmpty()) {
                    moRentDbl = 0.00;
                } else {
                    moRentDbl = Double.parseDouble(moRentExp.getText().toString());
                }
                if (moFoodExp.getText().toString().isEmpty()) {
                    moFoodDbl = 0.00;
                } else {
                    moFoodDbl = Double.parseDouble(moFoodExp.getText().toString());
                }
                if (moTransportExp.getText().toString().isEmpty()) {
                    moTransportDbl = 0.00;
                } else {
                    moTransportDbl = Double.parseDouble(moTransportExp.getText().toString());
                }
                if (moPersonalExp.getText().toString().isEmpty()) {
                    moPersonalDbl = 0.00;
                } else {
                    moPersonalDbl = Double.parseDouble(moPersonalExp.getText().toString());
                }
                double monthlyExps = moRentDbl + moFoodDbl + moTransportDbl + moPersonalDbl;
                setMonthlyExpenses(monthlyExps);
                tv_monthlyExp.setText(getResources().getString(R.string.showMonthlyExpenses) + df.format(monthlyExps));
            }
        });

        // color codes
        // RED = #FF0000         0 - 25%
        // ORANGE = #FFA500     26 - 50%
        // YELLOW = #FFFF00     51 - 75%
        // GREEN = #008000      76 - 100%

        summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double inc = getMonthlyIncome();
                double exp = getMonthlyExpenses();
                double diff = (inc - exp);
                double perc = (diff / inc) * 100;

                AlertDialog.Builder alertSummary = new AlertDialog.Builder(MainDashboard.this);

                if (perc >= 76 && perc <= 100) {
                    alertSummary.setTitle(Html.fromHtml("<font-color='#008000'>Managable budget!</font>"));
                    alertSummary.setMessage("You save " + df.format(perc) + "%!");
                } else if (perc >= 51 && perc <= 75) {
                    alertSummary.setTitle(Html.fromHtml("<font-color='#FFFF00'>Decent budget!</font>"));
                    alertSummary.setMessage("You save " + df.format(perc) + "%!");
                } else if (perc >= 26 && perc <=50) {
                    alertSummary.setTitle(Html.fromHtml("<font-color='#FFA500'>Cautious budget!</font>"));
                    alertSummary.setMessage("You save " + df.format(perc) + "%!");
                } else if (perc >= 0 && perc <= 25) {
                    alertSummary.setTitle(Html.fromHtml("<font-color='#FF0000'>Critical budget!</font>"));
                    alertSummary.setMessage("You save " + df.format(perc) + "%!");
                } else {
                    alertSummary.setTitle(Html.fromHtml("<font-color='#FF0000'>No budget!</font>"));
                    alertSummary.setMessage("You spend more than you can save! Be careful!");
                }

                alertSummary.setCancelable(true);
                alertSummary.setPositiveButton(
                        getResources().getString(R.string.understand),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog showAlertSummary = alertSummary.create();
                showAlertSummary.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage(getResources().getString(R.string.ask_toLeaveDashboard))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainDashboard.this.finish();
                        Intent intent = new Intent(MainDashboard.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no), null)
                .show();
    }
}