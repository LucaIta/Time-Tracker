import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class LapTimeTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void LapTime_InstantiateCorrectly_True() {
    LapTime testLapTime = new LapTime(1, 2);
    assertTrue(testLapTime instanceof LapTime);
  }

  // start time is set differently now
  // @Test
  // public void LapTime_returnStartTime() {
  //   LapTime testLapTime = new LapTime(1, 2);
  //   assertEquals(1, testLapTime.getStartTime());
  // }

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
    //testLapTime.addToTask(testTask);
    assertTrue(testLapTime.equals(LapTime.all().get(0)));
  }


  // @Test
  // public void find_findsLapTimessInDatabase_True() {
  //   Task teskTask = new Task("Do the dishes", 1);
  //   LapTime testLapTime = new LapTime(1, teskTask.getId());
  //   testLapTime.addToTask(teskTask);
  //   LapTime savedLapTime = LapTime.find(testLapTime.getId());
  //   assertTrue(testLapTime.equals(savedLapTime));
  // }





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



}
