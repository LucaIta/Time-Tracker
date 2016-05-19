import org.sql2o.*;
import java.time.*;
import java.util.List;

public class Task {
  private int id;
  private String name;
  private long goal_time;
  private final long MILLIS_PER_HOUR = 3600000L;
  private final long MILLIS_PER_MINUTE = 60000L;
  private final long MILLIS_PER_SECOND = 1000L;
  // private long goal_time_secs;//??!~?!?!!?

  //not stored in database?
  // private LocalTime starting_time;
  // private LocalTime end_time;

  public Task (String name, long goal_time) {
    this.name = name;
    this.goal_time = goal_time;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public long getGoalTime() {
    return goal_time;
  }

  public static List<Task> all() {
    String sql = "SELECT * FROM tasks ORDER BY name;";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Task.class);
    }
  }

  @Override
  public boolean equals(Object otherTask) {
    if (!(otherTask instanceof Task)) {
      return false;
    } else {
      Task newTask = (Task) otherTask;
      return this.getName().equals(newTask.getName()) &&
             this.getId() == newTask.getId() &&
             this.getGoalTime() == newTask.getGoalTime();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tasks(name, goal_time) VALUES (:name, :goal_time)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("goal_time", this.goal_time)
        .executeUpdate()
        .getKey();
    }
  }

  public static Task find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tasks WHERE id = :id";
      Task task = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Task.class);
      return task;
    }
  }

  public void update(String name, long goal_time) {
    if (name.trim().length() != 0) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "UPDATE tasks SET name = :name WHERE id = :id";
        con.createQuery(sql)
          .addParameter("name", name)
          .addParameter("id", this.getId())
          .executeUpdate();
      }
    }
    if (goal_time != 0) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "UPDATE tasks SET goal_time = :goal_time WHERE id = :id";
        con.createQuery(sql)
          .addParameter("goal_time", goal_time)
          .addParameter("id", this.getId())
          .executeUpdate();
      }
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM tasks WHERE id = :id";
        con.createQuery(sql)
          .addParameter("id", this.getId())
          .executeUpdate();
    }
  }

  public List<LapTime> getLapTimes() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM lap_times WHERE task_id = :task_id";
      List<LapTime> lapTimes = con.createQuery(sql).addParameter("task_id", this.getId()).executeAndFetch(LapTime.class);
      return lapTimes;
    }
  }

  public void saveGoal(int hours, int minutes, int seconds) {
    long goal = 0;
    goal += hours * MILLIS_PER_HOUR;
    goal += minutes * MILLIS_PER_MINUTE;
    goal += seconds * MILLIS_PER_SECOND;
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE tasks SET goal_time = :goal_time WHERE id = :id";
      con.createQuery(sql).addParameter("goal_time", goal).addParameter("id", this.id).executeUpdate();
    }
  }

  public String getTimeAsString(long deltaT) {
    long hours = deltaT / MILLIS_PER_HOUR;
    deltaT -= hours * MILLIS_PER_HOUR;
    long minutes = deltaT / MILLIS_PER_MINUTE;
    deltaT -= minutes * MILLIS_PER_MINUTE;
    long seconds = deltaT / MILLIS_PER_SECOND;
    deltaT -= seconds * MILLIS_PER_SECOND;
    long milliseconds = deltaT;
    return String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, milliseconds);
  }
}
