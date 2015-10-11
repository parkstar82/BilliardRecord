package com.park.billard.billiardrecord;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

  public Button mRecordBtn = null;
  public Button mStartBtn = null;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    mRecordBtn = (Button) findViewById(R.id.btn_show_record);
    mStartBtn = (Button) findViewById(R.id.btn_start_game);

    mRecordBtn.setOnClickListener(mButtonClickListener);
    mStartBtn.setOnClickListener(mButtonClickListener);
  }

  View.OnClickListener mButtonClickListener = new View.OnClickListener() {

    @Override
    public void onClick(View view) {
      Intent intent = null;
      switch(view.getId()){

        case R.id.btn_show_record:
          intent = new Intent();
          // TODO : 리스트로 이동
          break;
        case R.id.btn_start_game:
          intent = new Intent(MainActivity.this, ScoreboardActivity.class);
          startActivity(intent);
          break;
        default:
          break;
      }
    }
  };
}
