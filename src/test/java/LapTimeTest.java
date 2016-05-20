import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;


public class LapTimeTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void LapTime_InstantiateCorrectly_True() {
    LapTime testLapTime = new LapTime(1, 2);
    assertTrue(testLapTime instanceof LapTime);
  }

  @Test
  public void LapTime_returnEndTime() {
    LapTime testLapTime = new LapTime(1, 2);
    assertEquals(0, testLapTime.getEndTime());
  }

  @Test
  public void LapTime_returnTaskId() {
    LapTime testLapTime = new LapTime(1, 2);
    Task testTask = new Task("Do the dishes", 1);
    assertEquals(0, testLapTime.getId());
  }

  public void LapTime_addTaskToDatabase() {
    Task testTask = new Task("Do the dishes", 1);
    LapTime testLapTime = new LapTime(1, testTask.getId());
    testLapTime.save();
    assertTrue(testLapTime.equals(LapTime.all().get(0)));
  }


//----already passing, coment out becouse got rid off end_time from constructor, test one more time when we have GET method time_lap----//

// this is the old version

  // @Test
  // public void timeFormattedAsString() {
  //   LapTime testLapTime = new LapTime (0L, 603010L);
  //   assertEquals("00:10:03:10", testLapTime.getDifferenceAsString());
  // }



  // CHANGED HOW IT ALL WORKS
  // @Test
  // public void saveStartTime() {
  //   Task testTask = new Task("Do the dishes", 1);
  //   LapTime testLapTime = new LapTime(1, testTask.getId());
  //   //testLapTime.addToTask(teskTask);
  //   testLapTime.saveStartTime();
  //   testLapTime.saveEndTime();
  //   LapTime newLapTime = LapTime.find(testLapTime.getId());
  //   long difference = newLapTime.getEndTime() - newLapTime.getStartTime();
  //   assertTrue(difference < 100);
  // }

  @Test
  public void getAverageTime() {
    LapTime testLapTime = new LapTime(1000);
    LapTime testLapTime2 = new LapTime(2000);
    LapTime testLapTime3 = new LapTime(3000);
    Task teskTask = new Task("Do the dishes", 1);
    testLapTime.addToTask(teskTask);
    testLapTime2.addToTask(teskTask);
    testLapTime3.addToTask(teskTask);
    testLapTime.saveEndTime();
    testLapTime2.saveEndTime();
    testLapTime3.saveEndTime();
    ArrayList<LapTime> laptimes = new ArrayList<LapTime>();
    laptimes.add(testLapTime);
    laptimes.add(testLapTime2);
    laptimes.add(testLapTime3);
    assertTrue(System.currentTimeMillis() > LapTime.getAverageTime(laptimes));
  }

  @Test
  public void getBestTimes() {
    LapTime testLapTime = new LapTime(1000);
    LapTime testLapTime2 = new LapTime(2000);
    LapTime testLapTime3 = new LapTime(3000);
    Task teskTask = new Task("Do the dishes", 1);
    testLapTime.addToTask(teskTask);
    testLapTime2.addToTask(teskTask);
    testLapTime3.addToTask(teskTask);
    testLapTime.saveEndTime();
    testLapTime2.saveEndTime();
    testLapTime3.saveEndTime();
    ArrayList<LapTime> laptimes = new ArrayList<LapTime>();
    laptimes.add(testLapTime);
    laptimes.add(testLapTime2);
    laptimes.add(testLapTime3);
    assertTrue(System.currentTimeMillis() > LapTime.getBestTime(laptimes)/3);
  }

  @Test
  public void getTotalTime() {
    LapTime testLapTime = new LapTime(1000);
    LapTime testLapTime2 = new LapTime(2000);
    LapTime testLapTime3 = new LapTime(3000);
    Task teskTask = new Task("Do the dishes", 1);
    testLapTime.addToTask(teskTask);
    testLapTime2.addToTask(teskTask);
    testLapTime3.addToTask(teskTask);
    testLapTime.saveEndTime();
    testLapTime2.saveEndTime();
    testLapTime3.saveEndTime();
    ArrayList<LapTime> laptimes = new ArrayList<LapTime>();
    laptimes.add(testLapTime);
    laptimes.add(testLapTime2);
    laptimes.add(testLapTime3);
    assertTrue(System.currentTimeMillis() * 3 > LapTime.getTotalTime(laptimes));
  }
}
