import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;


public class LapTimeTest {


  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void LapTime_InstantiateCorrectly_True() {
    LapTime testLapTime = new LapTime(1,0,0);
    assertTrue(testLapTime instanceof LapTime);
  }

  @Test
  public void LapTime_returnStartTime() {
    LapTime testLapTime = new LapTime(1,1,1);
    assertEquals(1, testLapTime.getStartTime());
  }

  @Test
  public void LapTime_returnEndTime() {
    LapTime testLapTime = new LapTime(1,4,1);
    assertEquals(4, testLapTime.getEndTime());
  }

  @Test
  public void LapTime_returnTaskId() {
    LapTime testLapTime = new LapTime(1,4,6);
    assertEquals(6, testLapTime.getId());
  }

  @Test
  public void LapTime_addTaskToDatabase() { // to test, not passed yet
    LapTime testLapTime = new LapTime(1,4,6);
    assertTrue(testLapTime.equals(Task.all().get(0));
  }



}
