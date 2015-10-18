package in.championswimmer.refuel.uihelper;

import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.CompoundButton;

import java.util.Stack;

/**
 * Created by championswimmer on 18/10/15.
 */
public class ExtendedFuelEntryWatchers {

    public static final String TAG = "ExtendedFuelEntryWatchers";

    static boolean enterRate = false;

    static Float money, fuel, rate;

    public static class RateEnterCheckedChanceListener implements CompoundButton.OnCheckedChangeListener {

        AppCompatEditText rateEt, fuelEt;

        public RateEnterCheckedChanceListener(AppCompatEditText rateEt, AppCompatEditText fuelEt) {
            this.rateEt = rateEt;
            this.fuelEt = fuelEt;
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            enterRate = b;
            rateEt.setEnabled(b);
            fuelEt.setEnabled(!b);
        }
    }


    public static class MoneyTextWatcher implements TextWatcher {
        private AppCompatEditText fuelEt, rateEt;

        public MoneyTextWatcher(AppCompatEditText fuelEt, AppCompatEditText rateEt) {
            this.fuelEt = fuelEt;
            this.rateEt = rateEt;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            if (enterRate) {
                if (rateEt.getText().toString().length() > 0) {
                    rate = Float.valueOf(rateEt.getText().toString());
                    money = Float.valueOf("0" + charSequence.toString());

                    fuelEt.setText(String.valueOf(money / rate));

                }
            } else {
                if (fuelEt.getText().toString().length() > 0) {
                    fuel = Float.valueOf(fuelEt.getText().toString());
                    money = Float.valueOf("0" + charSequence.toString());

                    rateEt.setText(String.valueOf(money / fuel));
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    public static class FuelTextWatcher implements TextWatcher {
        private AppCompatEditText rateEt, moneyEt;

        public FuelTextWatcher(AppCompatEditText rateEt, AppCompatEditText moneyEt) {
            this.rateEt = rateEt;
            this.moneyEt = moneyEt;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            if (!enterRate) {
                if (moneyEt.getText().toString().length() > 0) {
                    money = Float.valueOf(moneyEt.getText().toString());
                    fuel = Float.valueOf("0" + charSequence.toString());

                    rateEt.setText(String.valueOf(money / fuel));
                }
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.toString().length() == 0) editable.append("0.0");
        }
    }

    public static class RateTextWatcher implements TextWatcher {
        private AppCompatEditText fuelEt, moneyEt;

        public RateTextWatcher(AppCompatEditText fuelEt, AppCompatEditText moneyEt) {
            this.fuelEt = fuelEt;
            this.moneyEt = moneyEt;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            if (enterRate) {
                if (moneyEt.getText().toString().length() > 0) {
                    money = Float.valueOf(moneyEt.getText().toString());
                    rate = Float.valueOf(charSequence.toString());

                    fuelEt.setText(String.valueOf(money / rate));
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.toString().length() == 0) editable.append("0.0");
        }
    }
}
