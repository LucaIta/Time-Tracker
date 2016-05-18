import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class RoutineTest {

  @Rule
    public DatabaseRule database = new DatabaseRule();

  @Test
    public void Routine_instantiatesCorrectly_true() {
      Routine myRoutine = new Routine("routine1");
      assertEquals(true, myRoutine instanceof Routine);
    }

  @Test
  public void getName_InstantiatesWithName_String() {
    Routine myRoutine = new Routine("routine1");
    assertEquals("routine1", myRoutine.getName());
  }

  @Test
  public void all_emptyAtFirst_0() {
    assertEquals(0, Routine.all().size());
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame_true() {
    Routine firstRoutine = new Routine("routine1");
    Routine secondRoutine = new Routine("routine1");
    assertTrue(firstRoutine.equals(secondRoutine));
  }

  @Test
  public void save_savesObjectIntoDatabase_true() {
    Routine myRoutine = new Routine("routine1");
    myRoutine.save();
    assertTrue(Routine.all().get(0).equals(myRoutine));
  }

  @Test
  public void save_assignsIdToObject_int() {
    Routine myRoutine = new Routine("routine1");
    myRoutine.save();
    Routine savedRoutine = Routine.all().get(0);
    assertEquals(myRoutine.getId(), savedRoutine.getId());
  }

  @Test
  public void find_findRoutineInDatabase_true() {
    Routine myRoutine = new Routine("routine1");
    myRoutine.save();
    Routine savedRoutine = Routine.find(myRoutine.getId());
    assertTrue(myRoutine.equals(savedRoutine));
  }

  @Test
  public void update_updateRoutineInDatabase() {
    Routine myRoutine = new Routine("routine1");
    myRoutine.save();
    myRoutine.update("routine_number_1");
    assertEquals("routine_number_1", Routine.find(myRoutine.getId()).getName());
  }

  @Test
  public void addTask_addsTaskToRoutine_true() {
    Routine myRoutine = new Routine("routine1");
    myRoutine.save();
    Task myTask = new Task("Jumping jack", 500);
    myTask.save();
    myRoutine.addTask(myTask);
    Task savedTask = myRoutine.getTasks().get(0);
    assertTrue(myTask.equals(savedTask));
  }

  @Test
  public void getTasks_returnsAllTasks_List() {
    Routine myRoutine = new Routine("AC/DC");
    myRoutine.save();
    Task myTask = new Task("Jumping jack", 500);
    myTask.save();
    myRoutine.addTask(myTask);
    List savedTasks = myRoutine.getTasks();
    assertEquals(1, savedTasks.size());
  }

  @Test
  public void delete_deletesTheRoutineAndTasksAssociations() {
    Routine myRoutine = new Routine("routine1");
    myRoutine.save();
    Task myTask = new Task("Jumping jack", 500);
    myTask.save();
    myRoutine.addTask(myTask);
    myRoutine.delete();
    assertEquals(0, Routine.all().size());
  }

}
