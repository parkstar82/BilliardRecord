package com.park.billard.billiardrecord;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Park on 2015-10-10.
 */
public class ScoreboardActivity extends Activity {

  public TextView mTxtTimer = null;

  public TextView mTxtScoreLeft = null;
  public TextView mTxtInningScoreLeft = null;
  public TextView mTxtCurInningLeft = null;
  public TextView mTxtCurAvgLeft = null;
  public TextView mTxtCurHighrunLeft = null;
  public TextView mTxtCurNoScoreLeft = null;
  public TextView mTxtCurFoulLeft = null;

  public TextView mTxtScoreRight = null;
  public TextView mTxtInningScoreRight = null;
  public TextView mTxtCurInningRight = null;
  public TextView mTxtCurAvgRight = null;
  public TextView mTxtCurHighrunRight = null;
  public TextView mTxtCurNoScoreRight = null;
  public TextView mTxtCurFoulRight = null;

  public Button mBtnGameStart = null;

  public Button mBtnAddLeft = null;
  public Button mBtnSubLeft = null;
  public Button mBtnEndInningLeft = null;

  public Button mBtnAddRight = null;
  public Button mBtnSubRight = null;
  public Button mBtnEndInningRight = null;

  public boolean isStart = false;
  public long mStartTime = 0;
  public Handler mHandler = new Handler();
  public int mScoreLeft = 0;
  public int mScoreRight = 0;
  public int mInningScoreLeft = 0;
  public int mInningScoreRight = 0;
  public int mCurInningLeft = 0;
  public int mCurInningRight = 0;
  public float mCurAvgLeft = 0.0f;
  public float mCurAvgRight = 0.0f;
  public int mHighRunLeft = 0;
  public int mHighRunRight = 0;
  public int mNoScoreLeft = 0;
  public int mNoScoreRight = 0;
  public int mFoulLeft = 0;
  public int mFoulRight = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_score_board);
    initViews();
  }

  public void initViews() {
    mTxtTimer = (TextView) findViewById(R.id.txt_timer);

    mTxtScoreLeft = (TextView) findViewById(R.id.txt_score_left);
    mTxtInningScoreLeft = (TextView) findViewById(R.id.txt_inning_score_left);
    mTxtCurInningLeft = (TextView) findViewById(R.id.txt_current_inning_left);
    mTxtCurAvgLeft = (TextView) findViewById(R.id.txt_current_avg_left);
    mTxtCurHighrunLeft = (TextView) findViewById(R.id.txt_current_highrun_left);
    mTxtCurNoScoreLeft = (TextView) findViewById(R.id.txt_current_no_score_left);
    mTxtCurFoulLeft = (TextView) findViewById(R.id.txt_current_foul_left);

    mTxtScoreRight = (TextView) findViewById(R.id.txt_score_right);
    mTxtInningScoreRight = (TextView) findViewById(R.id.txt_inning_score_right);
    mTxtCurInningRight = (TextView) findViewById(R.id.txt_current_inning_right);
    mTxtCurAvgRight = (TextView) findViewById(R.id.txt_current_avg_right);
    mTxtCurHighrunRight = (TextView) findViewById(R.id.txt_current_highrun_right);
    mTxtCurNoScoreRight = (TextView) findViewById(R.id.txt_current_no_score_right);
    mTxtCurFoulRight = (TextView) findViewById(R.id.txt_current_foul_right);

    mBtnGameStart = (Button) findViewById(R.id.btn_start_end_game);

    mBtnAddLeft = (Button) findViewById(R.id.btn_add_score_left);
    mBtnSubLeft = (Button) findViewById(R.id.btn_sub_score_left);
    mBtnEndInningLeft = (Button) findViewById(R.id.btn_end_inning_left);

    mBtnAddRight = (Button) findViewById(R.id.btn_add_score_right);
    mBtnSubRight = (Button) findViewById(R.id.btn_sub_score_right);
    mBtnEndInningRight = (Button) findViewById(R.id.btn_end_inning_right);

    mBtnGameStart.setOnClickListener(mButtonClickListener);

    mBtnAddLeft.setOnClickListener(mButtonClickListener);
    mBtnSubLeft.setOnClickListener(mButtonClickListener);
    mBtnEndInningLeft.setOnClickListener(mButtonClickListener);

    mBtnAddRight.setOnClickListener(mButtonClickListener);
    mBtnSubRight.setOnClickListener(mButtonClickListener);
    mBtnEndInningRight.setOnClickListener(mButtonClickListener);

  }


  View.OnClickListener mButtonClickListener = new View.OnClickListener() {

    @Override
    public void onClick(View view) {
      switch (view.getId()) {
        // TODO : 파울 버튼 만들기

        case R.id.btn_start_end_game:
          if (!isStart) {
            startGame();
          } else {
            endGame();
          }
          break;
        case R.id.btn_add_score_left:
          setInningScoreLeft(1);
          break;
        case R.id.btn_sub_score_left:
          setInningScoreLeft(-1);
          break;
        case R.id.btn_end_inning_left:
          endInningLeft();
          break;
        case R.id.btn_add_score_right:
          setInningScoreRight(1);
          break;
        case R.id.btn_sub_score_right:
          setInningScoreRight(-1);
          break;
        case R.id.btn_end_inning_right:
          endInningRight();
          break;
        default:
          break;
      }
    }
  };

  private void endInningLeft() {
    mTxtCurInningLeft.setText("" + (++mCurInningLeft));
    int inningScore = Integer.parseInt(mTxtInningScoreLeft.getText().toString());
    mScoreLeft += inningScore;

    if ( 0 == inningScore ){
      mTxtCurNoScoreLeft.setText( "" + (++mNoScoreLeft) );
    }
    mTxtScoreLeft.setText("" + mScoreLeft);
    mCurAvgLeft = getAvg(mCurInningLeft, mScoreLeft);
    mTxtCurAvgLeft.setText(String.format("%.2f", mCurAvgLeft));
    mTxtCurHighrunLeft.setText(getHighRun(Integer.parseInt(mTxtInningScoreLeft.getText().toString()), mHighRunLeft));

    mTxtInningScoreLeft.setText("0");
    mInningScoreLeft = 0;
  }

  private void endInningRight() {
    mTxtCurInningRight.setText("" + (++mCurInningRight));
    int inningScore = Integer.parseInt(mTxtInningScoreRight.getText().toString());
    mScoreRight += inningScore;

    if ( 0 == inningScore ){
      mTxtCurNoScoreRight.setText( "" + (++mNoScoreRight) );
    }
    mTxtScoreRight.setText("" + mScoreRight);
    mCurAvgRight= getAvg(mCurInningRight, mScoreRight);
    mTxtCurAvgRight.setText(String.format("%.2f", mCurAvgRight));
    mTxtCurHighrunRight.setText(getHighRun(Integer.parseInt(mTxtInningScoreRight.getText().toString()), mHighRunRight));

    mTxtInningScoreRight.setText("0");
  }

  private void setInningScoreLeft(int i) {
    mInningScoreLeft += i;
    mTxtInningScoreLeft.setText("" + mInningScoreLeft);
  }

  private void setInningScoreRight(int i) {
    mInningScoreRight += i;
    mTxtInningScoreRight.setText("" + mInningScoreRight);
  }

  private float getAvg(int inning, int score) {
    return (float)score/inning;
  }

  private String getHighRun(int score, int highRun) {
    if (highRun < score) {
      return "" + score;
    } else {
      return "" + highRun;
    }
  }

  private void endGame() {
    isStart = false;
    mHandler.removeCallbacks(mTimer);
    mBtnGameStart.setText(R.string.str_start_game);
    enableButtons(false);

    //TODO : W 이미지 띄워서 승자 쪽으로 이동시키기
  }

  private void startGame() {
    isStart = true;
    enableButtons(true);

    mTxtTimer.setText("00:00:00");

    mTxtScoreLeft.setText("0");
    mTxtInningScoreLeft.setText("0");
    mTxtCurInningLeft.setText("0");
    mTxtCurAvgLeft.setText("0");
    mTxtCurHighrunLeft.setText("0");
    mTxtCurNoScoreLeft.setText("0");
    mTxtCurFoulLeft.setText("0");

    mTxtScoreRight.setText("0");
    mTxtInningScoreRight.setText("0");
    mTxtCurInningRight.setText("0");
    mTxtCurAvgRight.setText("0");
    mTxtCurHighrunRight.setText("0");
    mTxtCurNoScoreRight.setText("0");
    mTxtCurFoulRight.setText("0");

    mScoreLeft = 0;
    mScoreRight = 0;
    mInningScoreLeft = 0;
    mInningScoreRight = 0;
    mCurInningLeft = 0;
    mCurInningRight = 0;
    mCurAvgLeft = 0.0f;
    mCurAvgRight = 0.0f;
    mHighRunLeft = 0;
    mHighRunRight = 0;
    mNoScoreLeft = 0;
    mNoScoreRight = 0;

    mBtnGameStart.setText(R.string.str_end_game);

    mStartTime = System.currentTimeMillis();
    mHandler.removeCallbacks(mTimer);
    mHandler.postDelayed(mTimer, 1000);

  }

  private Runnable mTimer = new Runnable() {
    @Override
    public void run() {
      long curTime = System.currentTimeMillis();
      long millis =  curTime - mStartTime;
      int second = (int) (millis / 1000);
      int minutes = second / 60;
      int hour = minutes / 60;
      second = second % 60;

      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(String.format("%02d", hour));
      stringBuilder.append(":");
      stringBuilder.append(String.format("%02d", minutes));
      stringBuilder.append(":");
      stringBuilder.append(String.format("%02d", second));

      mTxtTimer.setText(stringBuilder.toString());
      mHandler.postDelayed(this, 1000);
    }
  };

  private void enableButtons(boolean enable){
    mBtnAddLeft.setEnabled(enable);
    mBtnSubLeft.setEnabled(enable);
    mBtnEndInningLeft.setEnabled(enable);

    mBtnAddRight.setEnabled(enable);
    mBtnSubRight.setEnabled(enable);
    mBtnEndInningRight.setEnabled(enable);
  }

}
