import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class LapTimeTest {
  //@Rule
  //  public DatabaseRule database = new DatabaseRule();

  @Test
  public void timeFormattedAsString() {
    LapTime testLapTime = new LapTime (0L, 603010L);
    assertEquals("00:10:03:10", testLapTime.getDifferenceAsString());
  }
}
