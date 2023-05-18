package com.morax.xephalon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.morax.xephalon.R;
import com.morax.xephalon.conversion.Binary;
import com.morax.xephalon.conversion.Decimal;
import com.morax.xephalon.conversion.Hexadecimal;
import com.morax.xephalon.conversion.Octal;
import com.morax.xephalon.databinding.ActivityConversionBinding;
import com.morax.xephalon.databinding.ActivityLoginBinding;

import java.util.ArrayList;
import java.util.Objects;

public class ConversionActivity extends AppCompatActivity {

    private ActivityConversionBinding binding;

    private String conversionTypeFrom;
    private String conversionTypeTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConversionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ArrayAdapter<CharSequence> spinnerConversionAdapter = ArrayAdapter.createFromResource(this, R.array.conversions, R.layout.conversion_spinner_layout);
        spinnerConversionAdapter.setDropDownViewResource(R.layout.conversion_spinner_dropdown_item);
        binding.fromConversionSpinner.setAdapter(spinnerConversionAdapter);
        binding.fromConversionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                conversionTypeFrom = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.toConversionSpinner.setAdapter(spinnerConversionAdapter);
        binding.toConversionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                conversionTypeTo = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void goBack(View view) {
        onBackPressed();
        finish();
    }


    private void decimal(String decimal) {
        String[] result = {};
        switch (conversionTypeTo) {
            case "Binary":
                result = Decimal.toBinary(Integer.parseInt(decimal));
                break;
            case "Hexadecimal":
                result = Decimal.toHexadecimal(Integer.parseInt(decimal));
                break;
            case "Octal":
                result = Decimal.toOctal(Integer.parseInt(decimal));
                break;
            default:
                return;
        }
        binding.etToConversion.setText(result[0]);
        binding.tvSolution.setText(result[1]);
    }

    private void binary(String binary) {
        String[] result = {};
        switch (conversionTypeTo) {
            case "Decimal":
                result = Binary.toDecimal(binary);
                break;
            case "Hexadecimal":
                result = Binary.toHexadecimal(binary);
                break;
            case "Octal":
                result = Binary.toOctal(binary);
                break;
            default:
                return;
        }
        binding.etToConversion.setText(result[0]);
        binding.tvSolution.setText(result[1]);
    }

    private void octal(String octal) {
        String[] result = {};
        switch (conversionTypeTo) {
            case "Decimal":
                result = Octal.toDecimal(octal);
                break;
            case "Hexadecimal":
                result = Octal.toHexadecimal(octal);
                break;
            case "Binary":
                result = Octal.toBinary(octal);
                break;
            default:
                return;
        }
        binding.etToConversion.setText(result[0]);
        binding.tvSolution.setText(result[1]);
    }

    private void hexadecimal(String octal) {
        String[] result = {};
        switch (conversionTypeTo) {
            case "Decimal":
                result = Hexadecimal.toDecimal(octal);
                break;
            case "Octal":
                result = Hexadecimal.toOctal(octal);
                break;
            case "Binary":
                result = Hexadecimal.toBinary(octal);
                break;
            default:
                return;
        }
        binding.etToConversion.setText(result[0]);
        binding.tvSolution.setText(result[1]);
    }

    public void convertNumber(View view) {
        String fromStr = binding.etFromConversion.getText().toString();
        try {
            switch (conversionTypeFrom) {
                case "Decimal":
                    decimal(fromStr);
                    break;
                case "Binary":
                    binary(fromStr);
                    break;
                case "Octal":
                    octal(fromStr);
                case "Hexadecimal":
                    hexadecimal(fromStr);
            }
        } catch (Exception e) {
            Toast.makeText(ConversionActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}