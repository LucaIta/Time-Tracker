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
  //
  // @Test
  // public void LapTime_returnReal_time() {
  //   Time time = new Time(1.0,0,0);
  //   assertEquals(1.0, time.getRealTime());
  // }




}
