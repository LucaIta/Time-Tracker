import org.sql2o.*;
import java.time.*;
import java.util.List;

public class Run {
  private int routine_id;
  private int id;
  private int task_index;
  private int lap_id;

  public Run (int routine) {
    this.routine_id = routine;
  }

  public int getId() {
    return id;
  }

  public int getRoutineId() {
    return routine_id;
  }

  public int getTaskIndex() {
    return task_index;
  }

  public static List<Run> all() {
    String sql = "SELECT * FROM runs;";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Run.class);
    }
  }

  @Override
  public boolean equals(Object otherRun) {
    if (!(otherRun instanceof Run)) {
      return false;
    } else {
      Run newRun = (Run) otherRun;
      return this.getRoutineId() == newRun.getRoutineId() && this.getId() == newRun.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO runs(routine_id, task_index) VALUES (:routine_id, :task_index)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("routine_id", this.getRoutineId())
        .addParameter("task_index", this.getTaskIndex())
        .executeUpdate()
        .getKey();
    }
  }

  public static Run find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM runs WHERE id = :id";
      Run task = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Run.class);
      return task;
    }
  }

  public void start() {
    Routine routine = Routine.find(routine_id);
    List<Task> tasks = routine.getTasks();
    int task_id = tasks.get(this.task_index).getId();

    //CREATE NEW LAPTIME IN DATABASE//
    long time = System.currentTimeMillis();
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO lap_times (start_time, task_id, run_id) VALUES (:start_time, :task_id, :run_id)";
      this.lap_id = (int) con.createQuery(sql, true)
        .addParameter("start_time", time)
        .addParameter("task_id", task_id)
        .addParameter("run_id", this.id)
        .executeUpdate()
        .getKey();

      //REMEMBER WHICH LAP WE ARE ON IN DATABASE//
      String sql2 = "UPDATE runs SET lap_id = :lap_id WHERE id = :id";
      con.createQuery(sql2)
        .addParameter("lap_id", lap_id)
        .addParameter("id", this.getId())
        .executeUpdate();
    }
  }

  public void end() {
    long time = System.currentTimeMillis();
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE lap_times SET end_time = :end_time WHERE id = :lap_id";
      con.createQuery(sql)
        .addParameter("end_time", time)
        .addParameter("lap_id", this.lap_id)
        .executeUpdate();
    }
  }

  public void logLap () {
    Routine routine = Routine.find(routine_id);
    List<Task> tasks = routine.getTasks();
    end();
    task_index++;
    if (task_index < tasks.size()) {
      start();
    } else {
      task_index--;
    }
    //UPDATE RUN IN DATABASE TO REMEMBER NEW CURRENT TASK//
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE runs SET task_index = :task_index WHERE id = :id";
      con.createQuery(sql)
        .addParameter("task_index", this.task_index)
        .addParameter("id", this.getId())
        .executeUpdate();
    }
  }

  public long getTotal() {
    long sum = 0L;
    int runId = this.id;
    int routineId = this.routine_id;
    Routine newRoutine = Routine.find(routineId);
    List<Task> tasks = newRoutine.getTasks();
    System.out.println(tasks);
    for (Task task : tasks) {
      List<LapTime> lapTimes = task.getLapTimes();
      for (LapTime laptime : lapTimes) {
        if (laptime.getId() == runId
            && laptime.getEndTime() > 0
            && laptime.getStartTime() > 0) {
          sum += laptime.getEndTime() - laptime.getStartTime();
        }
    //
      }
    }
    return sum;
  }

}
