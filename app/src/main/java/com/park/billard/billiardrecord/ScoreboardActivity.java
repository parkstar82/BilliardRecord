package com.park.billard.billiardrecord;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
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

  public TextView mAnimationLeft = null;
  public TextView mAnimationRight = null;

  Vibrator mVibrator = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_score_board);
    initViews();
    enableButtons(false);
    initValues();
    mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
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
    mAnimationLeft = (TextView) findViewById(R.id.txt_animation_left);

    mTxtScoreRight = (TextView) findViewById(R.id.txt_score_right);
    mTxtInningScoreRight = (TextView) findViewById(R.id.txt_inning_score_right);
    mTxtCurInningRight = (TextView) findViewById(R.id.txt_current_inning_right);
    mTxtCurAvgRight = (TextView) findViewById(R.id.txt_current_avg_right);
    mTxtCurHighrunRight = (TextView) findViewById(R.id.txt_current_highrun_right);
    mTxtCurNoScoreRight = (TextView) findViewById(R.id.txt_current_no_score_right);
    mTxtCurFoulRight = (TextView) findViewById(R.id.txt_current_foul_right);
    mAnimationRight = (TextView) findViewById(R.id.txt_animation_right);

    mBtnGameStart = (Button) findViewById(R.id.btn_start_end_game);

    mBtnAddLeft = (Button) findViewById(R.id.btn_add_score_left);
    mBtnSubLeft = (Button) findViewById(R.id.btn_sub_score_left);
    mBtnEndInningLeft = (Button) findViewById(R.id.btn_end_inning_left);

    mBtnAddRight = (Button) findViewById(R.id.btn_add_score_right);
    mBtnSubRight = (Button) findViewById(R.id.btn_sub_score_right);
    mBtnEndInningRight = (Button) findViewById(R.id.btn_end_inning_right);

    mBtnGameStart.setOnTouchListener(mButtonClickListener);

    mBtnAddLeft.setOnTouchListener(mButtonClickListener);
    mBtnSubLeft.setOnTouchListener(mButtonClickListener);
    mBtnEndInningLeft.setOnTouchListener(mButtonClickListener);

    mBtnAddRight.setOnTouchListener(mButtonClickListener);
    mBtnSubRight.setOnTouchListener(mButtonClickListener);
    mBtnEndInningRight.setOnTouchListener(mButtonClickListener);

  }


  View.OnTouchListener mButtonClickListener = new View.OnTouchListener() {

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
      switch (motionEvent.getAction()){
        case MotionEvent.ACTION_DOWN:
          mVibrator.vibrate(300);
          break;
        case MotionEvent.ACTION_UP:
          switch (view.getId()) {
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
              int inningScore = Integer.parseInt(mTxtInningScoreLeft.getText().toString());
              setScoreLeftAnimation("" + inningScore);
              break;
            case R.id.btn_add_score_right:
              setInningScoreRight(1);
              break;
            case R.id.btn_sub_score_right:
              setInningScoreRight(-1);
              break;
            case R.id.btn_end_inning_right:
              int inningScoreRight = Integer.parseInt(mTxtInningScoreRight.getText().toString());
              setScoreRightAnimation("" + inningScoreRight);
              break;
            default:
              break;
          }
          break;
      }

      return true;
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
    mHighRunLeft = getHighRun(Integer.parseInt(mTxtInningScoreLeft.getText().toString()), mHighRunLeft);
    mTxtCurHighrunLeft.setText("" + mHighRunLeft);

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
    mHighRunRight = getHighRun(Integer.parseInt(mTxtInningScoreRight.getText().toString()), mHighRunRight);
    mTxtCurHighrunRight.setText("" + mHighRunRight);

    mTxtInningScoreRight.setText("0");
    mInningScoreRight = 0;
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

  private int getHighRun(int score, int highRun) {
    if (highRun < score) {
      return score;
    } else {
      return highRun;
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
    initValues();

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
      stringBuilder.append(String.format("%02d", minutes%60));
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

  private void initValues(){
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
  }

  private void setScoreLeftAnimation(String score){
    int leftInningEndXY[] = new int[2];
    int leftInningScoreXY[] = new int[2];

    mBtnEndInningLeft.getLocationOnScreen(leftInningEndXY);
    mTxtInningScoreLeft.getLocationOnScreen(leftInningScoreXY);

    float leftInningScoreY = leftInningScoreXY[1];

    ScaleAnimation scaleAni = new ScaleAnimation(1f, 1f, 1f, 0.7f);
    scaleAni.setDuration(1000);

    TranslateAnimation transAni = new TranslateAnimation(
        Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0f,
        Animation.ABSOLUTE, 0f, Animation.ABSOLUTE, -leftInningScoreY
    );
    transAni.setDuration(1000);

    AnimationSet aniSet = new AnimationSet(true);
    aniSet.setFillBefore(true);
    aniSet.setFillEnabled(true);
    aniSet.addAnimation(scaleAni);
    aniSet.addAnimation(transAni);

    mAnimationLeft.setText("" + score);
    mAnimationLeft.setVisibility(View.VISIBLE);
    mAnimationLeft.startAnimation(aniSet);

    aniSet.setAnimationListener(new Animation.AnimationListener() {
      @Override
      public void onAnimationStart(Animation animation) {

      }

      @Override
      public void onAnimationEnd(Animation animation) {
        mAnimationLeft.setVisibility(View.GONE);
        endInningLeft();
      }

      @Override
      public void onAnimationRepeat(Animation animation) {

      }
    });
  }

  private void setScoreRightAnimation(String score){
    int inningEndXY[] = new int[2];
    int inningScoreXY[] = new int[2];

    mBtnEndInningRight.getLocationOnScreen(inningEndXY);
    mTxtInningScoreRight.getLocationOnScreen(inningScoreXY);

    float inningScoreY = inningScoreXY[1];

    ScaleAnimation scaleAni = new ScaleAnimation(1f, 1f, 1f, 0.7f);
    scaleAni.setDuration(1000);

    TranslateAnimation transAni = new TranslateAnimation(
        Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0f,
        Animation.ABSOLUTE, 0f, Animation.ABSOLUTE, -inningScoreY
    );
    transAni.setDuration(1000);

    AnimationSet aniSet = new AnimationSet(true);
    aniSet.setFillBefore(true);
    aniSet.setFillEnabled(true);
    aniSet.addAnimation(scaleAni);
    aniSet.addAnimation(transAni);

    mAnimationRight.setText(""+score);
    mAnimationRight.setVisibility(View.VISIBLE);
    mAnimationRight.startAnimation(aniSet);

    aniSet.setAnimationListener(new Animation.AnimationListener() {
      @Override
      public void onAnimationStart(Animation animation) {

      }

      @Override
      public void onAnimationEnd(Animation animation) {
        mAnimationRight.setVisibility(View.GONE);
        endInningRight();
      }

      @Override
      public void onAnimationRepeat(Animation animation) {

      }
    });
  }
}
