package com.spoichcave.simplecount;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

public class CounterView {

    private Counter counter;

    public ConstraintLayout layoutCounter;
    public Button btnSave;
    public Button btnDel;
    public EditText txtCountName;
    public Button btnPlus;
    public Button btnMinus;
    public TextView txtCount;

    public CounterView(Context context, int counterNumber){
        this.counter = new Counter();
        createLayout(context, counterNumber);
    }

    public CounterView(Counter counter, Context context, int counterNumber){
        this.counter = counter;
        createLayout(context, counterNumber);
    }

    public void createLayout(Context context, int counterNumber){
        layoutCounter = new ConstraintLayout(context);
        layoutCounter.setId(counterNumber);

        ConstraintLayout.LayoutParams clpLayoutCounter = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        layoutCounter.setLayoutParams(clpLayoutCounter);

        btnSave = new Button(context);
        btnSave.setId(2000+counterNumber);
        btnSave.setText(R.string.btnSave);
        layoutCounter.addView(btnSave);
        ConstraintLayout.LayoutParams clpBtnReset = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        btnSave.setLayoutParams(clpBtnReset);

        btnDel = new Button(context);
        btnDel.setId(4000+counterNumber);
        btnDel.setText(R.string.btnDelete);
        layoutCounter.addView(btnDel);
        ConstraintLayout.LayoutParams clpBtnDel = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        btnDel.setLayoutParams(clpBtnDel);

        txtCountName = new EditText(context);
        txtCountName.setId(3000+counterNumber);
        if(this.counter.getName() == null) txtCountName.setHint("Add a name");
        else txtCountName.setText(this.counter.getName());
        txtCountName.setGravity(Gravity.CENTER);
        txtCountName.setInputType(InputType.TYPE_CLASS_TEXT);
        layoutCounter.addView(txtCountName);
        ConstraintLayout.LayoutParams clpTxtCountName = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        txtCountName.setLayoutParams(clpTxtCountName);

        btnPlus = new Button(context);
        btnPlus.setId(5000+counterNumber);
        btnPlus.setText("+");
        layoutCounter.addView(btnPlus);
        ConstraintLayout.LayoutParams clpBtnPlus = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        txtCountName.setLayoutParams(clpBtnPlus);

        btnMinus = new Button(context);
        btnMinus.setId(6000+counterNumber);
        btnMinus.setText("-");
        layoutCounter.addView(btnMinus);
        ConstraintLayout.LayoutParams clpBtnMinus = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        txtCountName.setLayoutParams(clpBtnMinus);

        txtCount = new TextView(context);
        txtCount.setId(7000+counterNumber);
        txtCount.setText(String.format(Locale.ROOT,"%d",this.counter.getValue()));
        txtCount.setGravity(Gravity.CENTER);
        layoutCounter.addView(txtCount);
        ConstraintLayout.LayoutParams clpTxtCount = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        txtCountName.setLayoutParams(clpTxtCount);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layoutCounter);
        constraintSet.connect(btnSave.getId(), ConstraintSet.LEFT, layoutCounter.getId(), ConstraintSet.LEFT);
        constraintSet.connect(btnDel.getId(), ConstraintSet.RIGHT, layoutCounter.getId(), ConstraintSet.RIGHT);
        constraintSet.connect(txtCountName.getId(), ConstraintSet.TOP, btnSave.getId(), ConstraintSet.TOP);
        constraintSet.connect(txtCountName.getId(), ConstraintSet.BOTTOM, btnSave.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(txtCountName.getId(), ConstraintSet.LEFT, btnSave.getId(), ConstraintSet.RIGHT);
        constraintSet.connect(txtCountName.getId(), ConstraintSet.RIGHT, btnDel.getId(), ConstraintSet.LEFT);
        constraintSet.connect(btnMinus.getId(), ConstraintSet.TOP, btnSave.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(btnMinus.getId(), ConstraintSet.LEFT, layoutCounter.getId(), ConstraintSet.LEFT);
        constraintSet.connect(btnPlus.getId(), ConstraintSet.TOP, btnMinus.getId(), ConstraintSet.TOP);
        constraintSet.connect(btnPlus.getId(), ConstraintSet.RIGHT, layoutCounter.getId(), ConstraintSet.RIGHT);
        constraintSet.connect(txtCount.getId(), ConstraintSet.LEFT, btnMinus.getId(), ConstraintSet.RIGHT);
        constraintSet.connect(txtCount.getId(), ConstraintSet.RIGHT, btnPlus.getId(), ConstraintSet.LEFT);
        constraintSet.connect(txtCount.getId(), ConstraintSet.TOP, btnPlus.getId(), ConstraintSet.TOP);
        constraintSet.connect(txtCount.getId(), ConstraintSet.BOTTOM, btnPlus.getId(), ConstraintSet.BOTTOM);
        constraintSet.applyTo(layoutCounter);

        setClickListeners();

    }

    private void setClickListeners(){

        final Counter thisCounter = this.counter;

        btnPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("DEBUG", "btnPlus id = "+v.getId());
                thisCounter.increment();
                txtCount.setText(thisCounter.toString());
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("DEBUG", "btnMinus id = "+v.getId());
                thisCounter.decrement();
                txtCount.setText(thisCounter.toString());
            }
        });

        txtCountName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                thisCounter.setName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    public void addDelOnClickListener(final LinearLayout layout){
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.removeView(layoutCounter);
            }
        });
    }

    public void addSaveOnClickListener(final DatabaseManager dbManager){
        final Counter thisCounter = this.counter;

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbManager.insertCounter(thisCounter);
            }
        });
    }

    public Counter getCounter() { return counter; }
}
