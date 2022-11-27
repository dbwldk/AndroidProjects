package kr.ac.yuhan.yujiayujia55.creditcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    LinearLayout linearChoose, linearInput, linearResult;
    RadioGroup rg;
    RadioButton rb1, rb2, rb3, rb4, rb5, rb6, rb7;
    Button btnSend, btnCalc, btnBack;
    EditText edit1, edit2, edit3, edit4;
    TextView tName, tTotal, tCredit,
            tCheck, tInput1, tInput2, tInput3;

    double score1 = 0.0;
    double score2 = 0.0;
    double score3 = 0.0;
    int score_out = 0;

    String subName = "";
    double subTotal = 0.0;
    String subCredit = "";

    boolean rgChecked; //라디오 그룹 눌렸는지 여부
    int checkedRbtn = 0; //어떤 라디오 버튼이 눌렸는지

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearChoose = findViewById(R.id.linear_choose);
        linearInput = findViewById(R.id.linear_input);
        linearResult = findViewById(R.id.linear_result);

        rg = findViewById(R.id.rg1);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
        rb5 = findViewById(R.id.rb5);
        rb6 = findViewById(R.id.rb6);
        rb7 = findViewById(R.id.rb7);

        btnSend = findViewById(R.id.btn_send);
        btnCalc = findViewById(R.id.btn_calc);
        btnBack = findViewById(R.id.btn_back);

        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        edit3 = findViewById(R.id.edit3);
        edit4 = findViewById(R.id.edit4);

        tName = findViewById(R.id.text_name);
        tTotal = findViewById(R.id.text_total);
        tCredit = findViewById(R.id.text_credit);

        tCheck = findViewById(R.id.text_check);

        tInput1 = findViewById(R.id.text_input1);
        tInput2 = findViewById(R.id.text_input2);
        tInput3 = findViewById(R.id.text_input3);

        rg.setOnCheckedChangeListener(rgL);
        btnSend.setOnClickListener(btnL);
        btnCalc.setOnClickListener(btnL);
        btnBack.setOnClickListener(btnL);
    }

    //라디오그룹 리스너
    RadioGroup.OnCheckedChangeListener rgL = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int rbId) {
            rgChecked = true;
            
            //초기화
            tInput1.setText("중간");
            tInput2.setText("기말");
            tInput3.setText("과제");

            //과목별
            switch (rbId){
                case R.id.rb1:
                    checkedRbtn = 1;
                    subName = "DB설계및구축";
                    tInput2.setText("팀프로젝트");
                    tInput3.setText("기말");
                    break;
                case R.id.rb2:
                    checkedRbtn = 2;
                    subName = "JSP";
                    tInput2.setText("개인 프로젝트");
                    tInput3.setText("별도 테스트");
                    break;
                case R.id.rb3:
                    checkedRbtn = 3;
                    subName = "운영체제"; //2시간 과목: F => 8 이상
                    break;
                case R.id.rb4:
                    checkedRbtn = 4;
                    subName = "모바일프로그래밍";
                    break;
                case R.id.rb5:
                    checkedRbtn = 5;
                    subName = "Java프로그래밍응용";
                    break;
                case R.id.rb6:
                    checkedRbtn = 6;
                    subName = "VC++실습";
                    tInput2.setText("개인 프로젝트");
                    break;
                case R.id.rb7:
                    checkedRbtn = 7;
                    subName = "시스템분석및설계";
                    break;
            }

            if(checkedRbtn <= 5){
                edit1.setHint("max: 30");
                edit2.setHint("max: 30");
                edit3.setHint("max: 20");
                edit4.setHint("12 이상일 경우 F");
                if(checkedRbtn == 3){
                    edit4.setHint("8 이상일 경우 F");
                }
            } else if(checkedRbtn == 6){
                edit1.setHint("max: 30");
                edit2.setHint("max: 40");
                edit3.setHint("max: 10");
                edit4.setHint("12 이상일 경우 F");
            } else if(checkedRbtn == 7){
                edit1.setHint("max: 40");
                edit2.setHint("max: 40");
                edit3.setHint("과제x");
                edit4.setHint("12 이상일 경우 F");
            }

            tCheck.setText(subName);
        }
    };

    //버튼 클릭 리스너
    View.OnClickListener btnL = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_send:

                    if(rgChecked == false){
                        Toast.makeText(getApplicationContext(), "과목을 선택해주세요.", Toast.LENGTH_SHORT).show();
                        break;
                    } //과목 선택 안되었을 시

                    //setVisibility
                    linearChoose.setVisibility(View.INVISIBLE);
                    linearInput.setVisibility(View.VISIBLE);
                    linearResult.setVisibility(View.INVISIBLE);

                    if(checkedRbtn == 7){
                        edit3.setFocusableInTouchMode(false); //과제를 받지 않는 과목
                    }

                    rgChecked = false;
                    break;


                case R.id.btn_calc:
                    //edittext 항목 받아오기
                    String editFir = edit1.getText().toString();
                    String editSec = edit2.getText().toString();
                    String editThi = edit3.getText().toString();
                    String editOut = edit4.getText().toString();


                /// 입력값 제한
                    boolean over = false; //값 범위 벗어남 체크

                    if(Integer.parseInt(editOut) < 0){
                        Toast.makeText(getApplicationContext(), "결석 시수는 음수가 올 수 없습니다.", Toast.LENGTH_SHORT).show();
                        over = true;
                        break;
                    }

                    //1-5번 과목(0는 위에서 제한함)
                    if(checkedRbtn  <= 5){
                        if(Double.parseDouble(editFir) > 30 || Double.parseDouble(editFir) < 0){
                            Toast.makeText(getApplicationContext(), "첫번째 항목의 값이 범위를 벗어남", Toast.LENGTH_SHORT).show();
                            over = true;
                            break;
                        }
                        if(Double.parseDouble(editSec) > 30 || Double.parseDouble(editSec) < 0){
                            Toast.makeText(getApplicationContext(), "두번째 항목의 값이 범위를 벗어남", Toast.LENGTH_SHORT).show();
                            over = true;
                            break;
                        }
                        if(Double.parseDouble(editThi) > 20 || Double.parseDouble(editThi) < 0){
                            Toast.makeText(getApplicationContext(), "세번째 항목의 값이 범위를 벗어남", Toast.LENGTH_SHORT).show();
                            over = true;
                            break;
                        }
                    }

                    //6번 과목
                    else if(checkedRbtn  <= 6){
                        if(Double.parseDouble(editFir) > 30 || Double.parseDouble(editFir) < 0){
                            Toast.makeText(getApplicationContext(), "첫번째 항목의 값이 범위를 벗어남", Toast.LENGTH_SHORT).show();
                            over = true;
                            break;
                        }
                        if(Double.parseDouble(editSec) > 40 || Double.parseDouble(editSec) < 0){
                            Toast.makeText(getApplicationContext(), "두번째 항목의 값이 범위를 벗어남", Toast.LENGTH_SHORT).show();
                            over = true;
                            break;
                        }
                        if(Double.parseDouble(editThi) > 10 || Double.parseDouble(editThi) < 0){
                            Toast.makeText(getApplicationContext(), "세번째 항목의 값이 범위를 벗어남", Toast.LENGTH_SHORT).show();
                            over = true;
                            break;
                        }
                    }

                    //7번 과목
                    else if(checkedRbtn  <= 7){
                        if(Double.parseDouble(editFir) > 40 || Double.parseDouble(editFir) < 0){
                            Toast.makeText(getApplicationContext(), "첫번째 항목의 값이 범위를 벗어남", Toast.LENGTH_SHORT).show();
                            over = true;
                            break;
                        }
                        if(Double.parseDouble(editSec) > 40 || Double.parseDouble(editSec) < 0){
                            Toast.makeText(getApplicationContext(), "두번째 항목의 값이 범위를 벗어남", Toast.LENGTH_SHORT).show();
                            over = true;
                            break;
                        }

                        editThi = "0"; //시분설은 3번째 항목을 체크하지 않는 과목임, 별도로 0로 초기화
                    }

                    if(over == true){
                        editFir = null;
                        editSec = null;
                        editThi = null;
                        editOut = null;

                        edit1.setText(null);
                        edit2.setText(null);
                        edit3.setText(null);
                        edit4.setText(null);
                        break; //초기화 후 브레이크
                    }

                //end

                    //항목 입력이 없을 시
                    if (editFir == null || editSec == null || editThi == null || editOut == null){
                        if(checkedRbtn == 7 && editThi == null){
                            //시분설은 3번째 항목을 체크하지 않는 과목임
                        }
                        Toast.makeText(getApplicationContext(), "항목을 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                        break;
                    }

                    //항목 변수에 저장
                    score1 = Double.parseDouble(editFir);
                    score2 = Double.parseDouble(editSec);
                    score3 = Double.parseDouble(editThi);
                    score_out = Integer.parseInt(editOut);

                    //합계 계산
                    subTotal = score1 + score2 + score3 + 20; //출석 점수 만점 기준으로, 아래에서 감점

                    //결석 시수 합계에 적용(출석 점수) + F 적용
                    if(checkedRbtn != 3){
                        if(score_out >= 3){
                            subTotal--;
                            if(score_out >= 6){
                                subTotal--;
                                if (score_out >= 9){
                                    subTotal--;
                                    if (score_out >= 12){
                                        subTotal = 0;
                                        subCredit = "F";

                                    }
                                }
                            }
                        }
                    }
                    else{
                        if(score_out >= 2){
                            subTotal--;
                            if(score_out >= 4){
                                subTotal--;
                                if (score_out >= 6){
                                    subTotal--;
                                    if (score_out >= 8){
                                        subTotal = 0;
                                        subCredit = "F";

                                    }
                                }
                            }
                        }
                    }


                    //합계가 음수가 나올시(오류)
                    if(subTotal < 0){
                        score1 = 0; score2 = 0; score3 = 0; score_out = 0; //초기화
                        Toast.makeText(getApplicationContext(), "error: 입력값을 확인해주세요.", Toast.LENGTH_SHORT).show();
                    }

                    //학점 계산
                    if(subTotal >= 95){
                        subCredit = "A+";
                    } else if(subTotal >= 90){
                        subCredit = "A0";
                    } else if(subTotal >= 85){
                        subCredit = "B+";
                    } else if(subTotal >= 80){
                        subCredit = "B0";
                    } else if(subTotal >= 75){
                        subCredit = "C+";
                    } else if(subTotal >= 70){
                        subCredit = "C0";
                    } else if(subTotal >= 65){
                        subCredit = "D+";
                    } else if(subTotal >= 60){
                        subCredit = "D0";
                    } else {
                        subCredit = "F";
                    }
                    
                    //결과 미리 세팅
                    tName.setText(subName);
                    tTotal.setText(subTotal+"");
                    tCredit.setText(subCredit);

                    //setVisibility
                    linearChoose.setVisibility(View.INVISIBLE);
                    linearInput.setVisibility(View.INVISIBLE);
                    linearResult.setVisibility(View.VISIBLE);

                    //edit text 초기화
                    edit1.setText(null);
                    edit2.setText(null);
                    edit3.setText(null);
                    edit4.setText(null);

                    edit3.setFocusableInTouchMode(true); //3번 항목 수정 불가일 수 있어서 풀기
                    
                    break;


                case R.id.btn_back:
                    //값 초기화
                    score1 = 0.0;
                    score2 = 0.0;
                    score3 = 0.0;
                    score_out = 0;
                    subName = "";
                    subTotal = 0.0;
                    subCredit = "";
                    rgChecked = false;

                    tName.setText("");
                    tTotal.setText("");
                    tCredit.setText("");

                    rg.clearCheck();
                    rgChecked = false;

                    //setVisibility
                    linearChoose.setVisibility(View.VISIBLE);
                    linearInput.setVisibility(View.INVISIBLE);
                    linearResult.setVisibility(View.INVISIBLE);

                    //결과 텍스트 초기화
                    tName.setText("");
                    tTotal.setText("");
                    tCredit.setText("");
                    break;
            }
        }
    };
}