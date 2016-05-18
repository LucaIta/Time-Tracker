import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Routine {
  private int id;
  private String name;
  private int taskIndex;

  public Routine (String name) {
    this.name = name;
    this.taskIndex = 0;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public static List<Routine> all() {
    String sql = "SELECT * FROM routines;";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Routine.class);
    }
  }

  @Override
  public boolean equals(Object otherRoutine) {
    if (!(otherRoutine instanceof Routine)) {
      return false;
    } else {
      Routine newRoutine = (Routine) otherRoutine;
      return this.getName().equals(newRoutine.getName()) && this.getId() == newRoutine.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO routines(name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Routine find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM routines WHERE id = :id";
      Routine routine = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Routine.class);
      return routine;
    }
  }

  public void update(String name) {
    if (name.trim().length() != 0) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "UPDATE routines SET name = :name WHERE id = :id";
        con.createQuery(sql)
          .addParameter("name", name)
          .addParameter("id", this.getId())
          .executeUpdate();
      }
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM routines WHERE id = :id";
        con.createQuery(sql)
          .addParameter("id", this.getId())
          .executeUpdate();

      String joinDelete = "DELETE FROM routines_tasks WHERE routine_id = :routine_id";
        con.createQuery(joinDelete)
          .addParameter("routine_id", this.getId())
          .executeUpdate();
    }
  }

  public void addTask(Task task) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "INSERT INTO routines_tasks (routine_id, task_id) VALUES (:routine_id, :task_id)";
        con.createQuery(sql)
          .addParameter("routine_id", this.getId())
          .addParameter("task_id", task.getId())
          .executeUpdate();
      }
    }

  public List<Task> getTasks() {
    try(Connection con = DB.sql2o.open()){
      String joinQuery = "SELECT task_id FROM routines_tasks WHERE routine_id = :routine_id";
      List<Integer> task_ids = con.createQuery(joinQuery)
        .addParameter("routine_id", this.getId())
        .executeAndFetch(Integer.class);

      List<Task> tasks = new ArrayList<Task>();

      for (Integer task_id : task_ids) {
        String taskQuery = "SELECT * FROM tasks WHERE id = :task_id";
        Task task = con.createQuery(taskQuery)
          .addParameter("task_id", task_id)
          .executeAndFetchFirst(Task.class);
        tasks.add(task);
      }
      return tasks;
    }
  }

  public void logLap () {
    ArrayList<Task> tasks = getTasks();
    long time = System.currentTimeMillis();

    //this should be an END() method in Task
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE tasks SET end_time = :end_time WHERE id = :task_id";
      con.createQuery(sql)
        .addParameter("end_time", time)
        .addParameter("task_id", tasks.get(taskIndex).getId())
        .executeUpdate();
    }
    taskIndex++;
    if (taskIndex >= tasks.size()) {
      //then the list is over and the routine done
    } else {
      //this should be a START() method in Task
      try(Connection con = DB.sql2o.open()) {
        String sql = "UPDATE tasks SET end_time = :end_time WHERE id = :task_id";
        con.createQuery(sql)
          .addParameter("start_time", time)
          .addParameter("task_id", tasks.get(taskIndex).getId())
          .executeUpdate();
      }
    }
  }

}
