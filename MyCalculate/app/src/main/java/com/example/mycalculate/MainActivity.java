package com.example.mycalculate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private Button btnAdd, btnDec, btnMul, btnDiv, btnEqu, btnClear, btnDel;//用于存放其他功能按钮
    private TextView txtResult;
    private StringBuffer digitA = new StringBuffer(), digitB = new StringBuffer();
    private boolean isChar = false;//标记是否按下运算符
    private int operator = 0;//默认运算符为+
    private int last_operator=0;
    private boolean isDigitA = true;//标记第1个操作数，用于退格删除txtResult中内容
    private int[] r_id = new int[10];
    private boolean is_cal = false;
    private boolean is_sed = false;
    private float total = 0; //隐藏运算数值
    private TextView history_result;
    private void my_click(int operator) {
        if(isChar && is_cal==false){
            my_Cul();
        }
        this.operator=operator;
        isChar = true;
        isDigitA = false;
    }
    private void my_Cul(){
        float da = Float.parseFloat(digitA.toString());
        float db = Float.parseFloat(digitB.toString());
        String a = "";
        switch (operator) {
            case 0:
                total = da+db;
                a="+";
                break;
            case 1:
                total=da-db;
                a="-";
                break;
            case 2:
                total = da*db;
                a="x";
                break;
            case 3:
                if (db == 0) {
                    txtResult.setText("Error digit B!");
                    is_cal = true;
                    break;
                }
                a="÷";
                total = da / db;
                break;
        }
        if(is_cal==false)history_result.append(String.valueOf(da)+a+String.valueOf(db)+'='+String.valueOf(total)+'\n');
        isChar = false; //bug 修复
        isDigitA = true;
        is_cal = true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_click(0);
            }
        });
        btnDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_click(1);
            }
        });
        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_click(2);
            }
        });
        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_click(3);
            }
        });
        btnEqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_Cul();
                txtResult.setText(String.valueOf(MainActivity.this.total));
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                digitA = new StringBuffer();
                digitB = new StringBuffer();
                isChar = false;
                txtResult.setText("");
                history_result.setText("");
            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = txtResult.getText().toString();
                if (isDigitA) {
                    digitA = new StringBuffer();
                    if (temp.length() > 0)
                        if (temp.length() == 1) {
                            temp = "0";
                        } else {
                            temp = temp.substring(0, temp.length() - 1);
                        }
                    txtResult.setText(temp);
                    digitA.append(txtResult.getText().toString());
                } else {
                    digitB = new StringBuffer();
                    if (temp.length() > 0)
                        if (temp.length() == 1) {
                            temp = "0";
                        } else {
                            temp = temp.substring(0, temp.length() - 1);
                        }
                    txtResult.setText(temp);
                    digitB.append(txtResult.getText().toString());
                }
            }
        });
    }
    void init() {
        btnAdd = (Button) this.findViewById(R.id.btn_add);
        btnDec = (Button) this.findViewById(R.id.btn_dec);
        btnMul = (Button) this.findViewById(R.id.btn_mul);
        btnDiv = (Button) this.findViewById(R.id.btn_div);
        btnEqu = (Button) this.findViewById(R.id.btn_equ);
        btnClear = (Button) this.findViewById(R.id.btn_clear);
        btnDel = (Button) this.findViewById(R.id.btn_del);
        txtResult = (TextView) this.findViewById(R.id.txtresult);
        history_result=(TextView) findViewById(R.id.history_result);
        history_result.setMovementMethod(new ScrollingMovementMethod());
        r_id[0] = findViewById(R.id.btn_0).getId();
        r_id[1] = findViewById(R.id.btn_1).getId();
        r_id[2] = findViewById(R.id.btn_2).getId();
        r_id[3] = findViewById(R.id.btn_3).getId();
        r_id[4] = findViewById(R.id.btn_4).getId();
        r_id[5] = findViewById(R.id.btn_5).getId();
        r_id[6] = findViewById(R.id.btn_6).getId();
        r_id[7] = findViewById(R.id.btn_7).getId();
        r_id[8] = findViewById(R.id.btn_8).getId();
        r_id[9] = findViewById(R.id.btn_9).getId();
    }

    public void calClick(View view) {
        for (int i = 0; i < 10; i++) {
            if (this.r_id[i] == view.getId()) {
                if (isChar) {//如果已经按过运算符，将显示结果区域清空，并将0
                    if (is_cal) { //如果继续做计算执行一下操作，将上次的结果赋值给A B清空重新加入新数字进行运算
                        digitA = new StringBuffer(String.valueOf(total));
                        digitB = new StringBuffer("");
                    }
                    txtResult.setText("");
                    digitB.append(i);
                    txtResult.setText(digitB.toString());
                } else {
                    if (is_cal) { //没有按过运算符，想做其他新的运算
                        digitA = new StringBuffer("");
                        digitB = new StringBuffer("");
                        isChar = false;
                        txtResult.setText("");
                    }
                    digitA.append(i);
                    txtResult.setText(digitA.toString());
                }
                is_cal=false;
                break;
            }
        }
    }
}

