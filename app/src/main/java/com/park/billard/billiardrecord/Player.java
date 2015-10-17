package com.park.billard.billiardrecord;

/**
 * Created by Park on 2015-10-15.
 */
public class Player {
  public String name = null;
  // 자기 점수
  public int myPoint = 0;
  // 이닝
  public int inning = 0;
  // 에버리지
  public double avg = 0.0;
  // 공타
  public int noScore = 0;
  // 파울
  public int foul = 0;
  // 하이런
  public int highRun;
  // 게임레코드 id
  public long gameRecordSeq = 0;
}
