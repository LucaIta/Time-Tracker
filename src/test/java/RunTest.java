import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.time.*;

public class RunTest {

  @Rule
    public DatabaseRule database = new DatabaseRule();

  @Test
    public void Run_instantiatesCorrectly_true() {
      Run myRun = new Run(5);
      assertEquals(true, myRun instanceof Run);
    }

  @Test
  public void getName_InstantiatesWithRoutineId_String() {
    Run myRun = new Run(5);
    assertEquals(5, myRun.getRoutineId());
  }

  @Test
  public void all_emptyAtFirst_0() {
    assertEquals(0, Run.all().size());
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame_true() {
    Run firstRun = new Run(5);
    Run secondRun = new Run(5);
    assertTrue(firstRun.equals(secondRun));
  }

  @Test
  public void save_savesObjectIntoDatabase_true() {
    Run myRun = new Run(5);
    myRun.save();
    assertTrue(Run.all().get(0).equals(myRun));
  }

  @Test
  public void save_assignsIdToObject_int() {
    Run myRun = new Run(5);
    myRun.save();
    Run savedRun = Run.all().get(0);
    assertEquals(myRun.getId(), savedRun.getId());
  }

  @Test
  public void find_findRunInDatabase_true() {
    Run myRun = new Run(5);
    myRun.save();
    Run savedRun = Run.find(myRun.getId());
    assertTrue(myRun.equals(savedRun));
  }
}
