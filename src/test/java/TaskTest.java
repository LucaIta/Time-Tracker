import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.time.*;

public class TaskTest {

  @Rule
    public DatabaseRule database = new DatabaseRule();

  @Test
    public void Task_instantiatesCorrectly_true() {
      Task myTask = new Task("task1", 500);
      assertEquals(true, myTask instanceof Task);
    }

  @Test
  public void getName_InstantiatesWithName_String() {
    Task myTask = new Task("task1", 500);
    assertEquals("task1", myTask.getName());
  }

  @Test
  public void all_emptyAtFirst_0() {
    assertEquals(0, Task.all().size());
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame_true() {
    Task firstTask = new Task("task1", 500);
    Task secondTask = new Task("task1", 500);
    assertTrue(firstTask.equals(secondTask));
  }

  @Test
  public void save_savesObjectIntoDatabase_true() {
    Task myTask = new Task("task1", 500);
    myTask.save();
    assertTrue(Task.all().get(0).equals(myTask));
  }

  @Test
  public void save_assignsIdToObject_int() {
    Task myTask = new Task("task1", 500);
    myTask.save();
    Task savedTask = Task.all().get(0);
    assertEquals(myTask.getId(), savedTask.getId());
  }

  @Test
  public void find_findTaskInDatabase_true() {
    Task myTask = new Task("task1", 500);
    myTask.save();
    Task savedTask = Task.find(myTask.getId());
    assertTrue(myTask.equals(savedTask));
  }

  @Test
  public void update_updateTaskInDatabase() {
    Task myTask = new Task("task1", 500);
    myTask.save();
    myTask.update("task001", 500);
    assertEquals("task001", Task.find(myTask.getId()).getName());
  }

  @Test
  public void delete_deletesAllVenuesAndTasksAssociations() {
    Task myTask = new Task("task1", 500);
    myTask.save();
    myTask.delete();
    assertEquals(0, Task.all().size());
  }

  @Test
  public void getLapTimes_returnsAllTheLapTimesOfTheTask() {
    Task testTask = new Task("Do the dishes", 1);
    LapTime testLapTime = new LapTime(1);
    testLapTime.addToTask(testTask);
    assertTrue(testLapTime.equals(testTask.getLapTimes().get(0)));
  }

  @Test
  public void saveGoal_savesGoalTimeCorrectly() {
    Task testTask = new Task("Do the dishes", 1);
    testTask.save();
    testTask.saveGoal(1,20,00);
    Task newTask = Task.find(testTask.getId());
    assertEquals(4800000,newTask.getGoalTime());
  }

}
