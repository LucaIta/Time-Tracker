import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class LapTimeTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void LapTime_InstantiateCorrectly_True() {
    LapTime testLapTime = new LapTime(1);
    assertTrue(testLapTime instanceof LapTime);
  }

  @Test
  public void LapTime_returnStartTime() {
    LapTime testLapTime = new LapTime(1);
    assertEquals(1, testLapTime.getStartTime());
  }

  @Test
  public void LapTime_returnEndTime() {
    LapTime testLapTime = new LapTime(1);
    assertEquals(0, testLapTime.getEndTime());
  }

  @Test
  public void LapTime_returnTaskId() {
    LapTime testLapTime = new LapTime(1);
    Task testTask = new Task("Do the dishes", 1);
    assertEquals(0, testLapTime.getId());
  }

  @Test
  public void LapTime_addTaskToDatabase() {
    LapTime testLapTime = new LapTime(1);
    Task testTask = new Task("Do the dishes", 1);
    testLapTime.addToTask(testTask);
    assertTrue(testLapTime.equals(LapTime.all().get(0)));
  }


  // @Test // nadia Test
  // public void find_findsBandssInDatabase_True() {
  //   Band myBand = new Band("Lotus");
  //   myBand.save();
  //   Band savedBand = Band.find(myBand.getId());
  //   assertTrue(myBand.equals(savedBand));
  // }




//----already passing, coment out becouse got rid off end_time from constructor, test one more time when we have GET method time_lap----//
  // @Test
  // public void timeFormattedAsString() {
  //   LapTime testLapTime = new LapTime (0L, 603010L);
  //   assertEquals("00:10:03:10", testLapTime.getDifferenceAsString());
  // }




}
