import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class LapTimeTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void LapTime_InstantiateCorrectly_True() {
    LapTime testLapTime = new LapTime(1,0);
    assertTrue(testLapTime instanceof LapTime);
  }

  @Test
  public void LapTime_returnStartTime() {
    LapTime testLapTime = new LapTime(1,1);
    assertEquals(1, testLapTime.getStartTime());
  }

  @Test
  public void LapTime_returnEndTime() {
    LapTime testLapTime = new LapTime(1,4);
    assertEquals(4, testLapTime.getEndTime());
  }

  @Test
  public void LapTime_returnTaskId() {
    LapTime testLapTime = new LapTime(1,4);
    Task testTask = new Task("Do the dishes", 1);
    assertEquals(0, testLapTime.getId());
  }

  @Test
  public void LapTime_addTaskToDatabase() { // to test, not passed yet
    LapTime testLapTime = new LapTime(1,4);
    Task testTask = new Task("Do the dishes", 1);
    testLapTime.addToTask(testTask);
    assertTrue(testLapTime.equals(LapTime.all().get(0)));
  }

  @Test
  public void timeFormattedAsString() {
    LapTime testLapTime = new LapTime (0L, 603010L);
    assertEquals("00:10:03:10", testLapTime.getDifferenceAsString());
  }

}
