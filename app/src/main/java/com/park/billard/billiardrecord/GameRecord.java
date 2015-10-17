package com.park.billard.billiardrecord;

import java.util.Date;
import java.util.List;

/**
 * Created by Park on 2015-10-17.
 */
public class GameRecord {
  // 시작 시간
  public Date startTime = null;
  // 게임 시간, 초
  public int gameTime = 0; // second
  // 게임 참여자들
  public List<Player> players = null;

}
