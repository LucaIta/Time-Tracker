import org.sql2o.*;
import java.time.*;
import java.util.List;

public class Run {
  private int routine_id;
  private int id;

  public Run (int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public int getRoutineId() {
    return routine_id;
  }
  //edit this
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
      String sql = "INSERT INTO runs(routine_id) VALUES (:routine_id)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("routine_id", this.getRoutineId())
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


}
