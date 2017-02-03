package com.example.arajput9656.theroster;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.IntegerRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText edittext_person_name;
    private CheckBox check_yes, check_no;
    private Spinner spinner;
    private Button btn_select_date;
    private RadioButton radio_xs, radio_s, radio_m, radio_l, radio_xl, radio_xxl;
    private SeekBar seek1, seek2, seek3;

    private Calendar myCalendar = Calendar.getInstance();
    private int mYear, mMonth, mDay, mHour, mMinute;

    String b_date = null;
    static ArrayList arrayList_name = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        edittext_person_name = (EditText) findViewById(R.id.editText_person_name);
        check_yes = (CheckBox) findViewById(R.id.checkBox_yes);
        check_no = (CheckBox) findViewById(R.id.checkBox_no);
        spinner = (Spinner) findViewById(R.id.spinner);
        btn_select_date = (Button) findViewById(R.id.button_select_date);

        radio_xs = (RadioButton) findViewById(R.id.radiobutton_XS);
        radio_s = (RadioButton) findViewById(R.id.radiobutton_S);
        radio_m = (RadioButton) findViewById(R.id.radiobutton_M);
        radio_l = (RadioButton) findViewById(R.id.radiobutton_L);
        radio_xl = (RadioButton) findViewById(R.id.radiobutton_XL);
        radio_xxl = (RadioButton) findViewById(R.id.radiobutton_XXL);

        seek1 = (SeekBar) findViewById(R.id.seekBar3);
        seek2 = (SeekBar) findViewById(R.id.seekBar4);
        seek3 = (SeekBar) findViewById(R.id.seekBar5);

        final TextView pant_value = (TextView) findViewById(R.id.textView_pant_value);
        final TextView shirt_value = (TextView) findViewById(R.id.textView_shirt_value);
        final TextView shoe_value = (TextView) findViewById(R.id.textView_shoe_value);

        ArrayList arrayList_eye_color = new ArrayList();
        arrayList_eye_color.add("Blue");
        arrayList_eye_color.add("Black");
        arrayList_eye_color.add("Grey");
        arrayList_eye_color.add("Green");
        arrayList_eye_color.add("Yellow");
        arrayList_eye_color.add("Red");

        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayList_eye_color);
        spinner.setAdapter(arrayAdapter);


        //To select only one checkbox at a time

        check_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_no.isChecked()) {
                    check_no.setChecked(false);
                }
            }
        });
        check_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_yes.isChecked()) {
                    check_yes.setChecked(false);
                }
            }
        });


        btn_select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                monthOfYear++;
                                btn_select_date.setText(dayOfMonth + "-" + monthOfYear + "-" + year);
                                b_date = dayOfMonth + "-" + monthOfYear + "-" + year;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        //For SeekBar code

        seek1.setMax(16);
        seek2.setMax(12);
        seek3.setMax(12);
        seek3.setProgress(4);

        //To check if SharedPrefrence exist or not

        SharedPreferences sharedPreferences = getSharedPreferences("person_data", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "Not");

        if (!name.equals("Not")) {

            String name_v = sharedPreferences.getString("name", "Not");
            String chk_v = sharedPreferences.getString("checkbox", "Not");
            String spi_v = sharedPreferences.getString("eye", "Not");
            String date_v = sharedPreferences.getString("date", "31-01-1990");
            String radio_v = sharedPreferences.getString("shirt_size", "Not");
            String pant_v = sharedPreferences.getString("pant_value", "Not");
            String shirt_v = sharedPreferences.getString("shirt_value", "Not");
            String shoe_v = sharedPreferences.getString("shoe_value", "Not");

            edittext_person_name.setText(name_v);

            if (chk_v.equals("Yes")) {
                check_yes.setChecked(true);
            } else {
                check_no.setChecked(true);
            }

            spinner.setSelection(((ArrayAdapter<String>) spinner.getAdapter()).getPosition(spi_v));

            btn_select_date.setText(date_v);

            if (radio_v.equals("XS")) {
                radio_xs.setChecked(true);
            } else if (radio_v.equals("S")) {

                radio_s.setChecked(true);
            } else if (radio_v.equals("M")) {

                radio_m.setChecked(true);
            } else if (radio_v.equals("L")) {

                radio_l.setChecked(true);
            } else if (radio_v.equals("XL")) {

                radio_xl.setChecked(true);
            } else if (radio_v.equals("XXL")) {

                radio_xxl.setChecked(true);
            }


            seek1.setProgress(Integer.parseInt(pant_v));
            seek2.setProgress(Integer.parseInt(shirt_v));
            seek3.setProgress(Integer.parseInt(shoe_v));

            pant_value.setText(pant_v);
            shirt_value.setText(shirt_v);
            shoe_value.setText(shoe_v);


        }


        seek1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int min = 0;
            int pant_size;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress >= min) {
                    pant_size = seekBar.getProgress();
                } else {
                    seekBar.setProgress(min);
                }
                String pant_size_value = Integer.toString(pant_size);
                pant_value.setText(pant_size_value);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        seek2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int min = 4;
            int shirt_size;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress >= min) {
                    shirt_size = seekBar.getProgress();
                } else {
                    seekBar.setProgress(min);
                }
                String shoe_size_value = Integer.toString(shirt_size);
                shirt_value.setText(shoe_size_value);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });


        seek3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int min = 4;
            int shoe_size;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress >= min) {
                    shoe_size = seekBar.getProgress();
                } else {
                    seekBar.setProgress(min);
                }
                String shoe_size_value = Integer.toString(shoe_size);
                shoe_value.setText(shoe_size_value);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });
    }

    public void saveData(View view) {
        String person_name = edittext_person_name.getText().toString();
        String eye_color = spinner.getSelectedItem().toString();
        String checkbox_value;
        String shirt_size_value;

        //For checkbox value
        if (check_yes.isChecked()) {
            checkbox_value = "Yes";
        } else {
            checkbox_value = "No";
        }

        //For radiobutton value
        if (radio_xs.isChecked()) {
            shirt_size_value = "XS";
        } else if (radio_s.isChecked()) {
            shirt_size_value = "S";
        } else if (radio_m.isChecked()) {
            shirt_size_value = "M";
        } else if (radio_l.isChecked()) {
            shirt_size_value = "L";
        } else if (radio_xl.isChecked()) {
            shirt_size_value = "XL";
        } else {
            shirt_size_value = "XXL";
        }

        //Get Value of Seekbar
        int val1 = seek1.getProgress();
        int val2 = seek2.getProgress();
        int val3 = seek3.getProgress();

        SharedPreferences sharedPreferences;

        sharedPreferences = getSharedPreferences("person_data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        System.out.println("First User");
        editor.putString("name", person_name);
        editor.putString("checkbox", checkbox_value);
        editor.putString("eye", eye_color);
        editor.putString("date", b_date);
        editor.putString("shirt_size", shirt_size_value);
        editor.putString("pant_value", val1 + "");
        editor.putString("shirt_value", val2 + "");
        editor.putString("shoe_value", val3 + "");
        editor.commit();


        Toast.makeText(MainActivity.this, "Your data added", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);


    }
}
