import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/time_tracker_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteTasksQuery = "DELETE FROM tasks *;";
      String deleteLapTimesQuery = "DELETE FROM lap_times *;";
      String deleteRoutinesQuery = "DELETE FROM routines *;";
      String deleteRoutinesTasksQuery = "DELETE FROM routines_tasks *;";
      String deleteRunsQuery = "DELETE FROM runs *";
      con.createQuery(deleteTasksQuery).executeUpdate();
      con.createQuery(deleteLapTimesQuery).executeUpdate();
      con.createQuery(deleteRoutinesQuery).executeUpdate();
      con.createQuery(deleteRoutinesTasksQuery).executeUpdate();
      con.createQuery(deleteRunsQuery).executeUpdate();
    }
  }

}
