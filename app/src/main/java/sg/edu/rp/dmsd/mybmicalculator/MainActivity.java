package sg.edu.rp.dmsd.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText etWeight;
    EditText etHeight;
    Button btnCalculate;
    Button btnReset;
    TextView tvDate;
    TextView tvBmi;
    TextView tvSay;

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        Integer iWeight = prefs.getInt("Weight",0);
        etWeight.setText(String.valueOf(iWeight));


        Float fHeight = prefs.getFloat("Height",0);
        etHeight.setText(String.valueOf(fHeight));

        String sDate = prefs.getString("Date","");
        tvDate.setText("Last Calculated Date: "+ sDate);

        Float ffHeight = prefs.getFloat("BMI",0);
        tvBmi.setText("Last Calculated BMI: " + ffHeight);


    }

    @Override
    protected void onPause() {
        super.onPause();

        int intWeight = Integer.parseInt(etWeight.getText().toString());
        float floatHeight = Float.parseFloat(etHeight.getText().toString());
        float convertInt = (float) intWeight;
        float totalBMI = ((convertInt) / ( floatHeight * floatHeight));
        Calendar now = Calendar.getInstance();
        String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                (now.get(Calendar.MONTH)+1) + "/" +
                now.get(Calendar.YEAR) + " " +
                now.get(Calendar.HOUR_OF_DAY) + ":" +
                now.get(Calendar.MINUTE);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefsEditor = prefs.edit();

        prefsEditor.putInt("Weight",intWeight);
        prefsEditor.putFloat("Height",floatHeight);
        prefsEditor.putString("Date",datetime);
        prefsEditor.putFloat("BMI",totalBMI);

        prefsEditor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalculate = findViewById(R.id.buttonCalc);
        btnReset = findViewById(R.id.buttonReset);
        tvDate = findViewById(R.id.textViewDate);
        tvBmi = findViewById(R.id.textViewBMI);
        tvSay = findViewById(R.id.textViewSay);


        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userWeight = Integer.parseInt(etWeight.getText().toString());
                float userHeight = Float.parseFloat(etHeight.getText().toString());
                float convertInt = (float) userWeight;
                float totalBMI = ((convertInt) / ( userHeight * userHeight));
                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                tvDate.setText("Last Calculated Date: " + datetime);
                tvBmi.setText("Last Calculated BMI:" + String.valueOf(totalBMI));

                if (totalBMI < 18.5){
                    tvSay.setText("You are underweight");
                }else if(totalBMI > 18.5 && totalBMI <= 24.9){
                    tvSay.setText("Your BMI is normal");
                }else if(totalBMI > 24.9 && totalBMI <= 29.9){
                    tvSay.setText("You are overweight");
                }else{
                    tvSay.setText("You are obese");
                }


            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userWeight = Integer.parseInt(etWeight.getText().toString());
                float userHeight = Float.parseFloat(etHeight.getText().toString());
                etHeight.setText("");
                etWeight.setText("");
                tvDate.setText("Last Calculated Date: ");
                tvBmi.setText("Last Calculated BMI: ");
            }
        });

    }
}
