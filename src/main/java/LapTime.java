import org.sql2o.*;
import java.util.List;

public class LapTime {
  private int id;
  private long start_time;
  private long end_time;
  private int task_id;
  private int run_id;     //this is just a test
  private final long MILLIS_PER_HOUR = 3600000L;
  private final long MILLIS_PER_MINUTE = 60000L;
  private final long MILLIS_PER_SECOND = 1000L;

  public LapTime(int run_id, int task_id) {
    this.run_id = run_id;
    this.task_id = task_id;
  }

  public long getStartTime() {
    return start_time;
  }

  public long getEndTime() {
    return end_time;
  }

  public int getId() {
    return id;
  }

  public int getTaskId() {
    return task_id;
  }

  public int getRunId () {
    return run_id;
  }

  public static List<LapTime> all() { // not tested yet
    try (Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM lap_times";
      List<LapTime> laptimes = con.createQuery(sql).executeAndFetch(LapTime.class);
      return laptimes;
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO lap_times(start_time, end_time, task_id, run_id) VALUES (:start_time, :end_time, :task_id, :run_id)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("start_time", this.start_time)
        .addParameter("end_time", this.end_time)
        .addParameter("task_id", this.task_id)
        .addParameter("run_id", this.run_id)
        .executeUpdate()
        .getKey();
    }
  }

  @Override
  public boolean equals(Object otherLapTime){
    if(!(otherLapTime instanceof LapTime)) {
      return false;
    }else{
      LapTime laptime = (LapTime) otherLapTime;
    return this.id == laptime.getId()&&
           this.start_time == laptime.getStartTime()&&
           this.end_time == laptime.getEndTime()&&
           this.task_id == laptime.getTaskId() &&
           this.run_id == laptime.getRunId();
    }
  }

  public long getCompletionTime() {
    return this.end_time - this.start_time;
  }

  public long getOffsetFromGoal(long goal_time) {
    long actualTime = getCompletionTime();
    return actualTime - goal_time;
  }

  public String getTimeAsString(long deltaT) {
    long hours = deltaT / MILLIS_PER_HOUR;
    deltaT -= hours * MILLIS_PER_HOUR;
    long minutes = deltaT / MILLIS_PER_MINUTE;
    deltaT -= minutes * MILLIS_PER_MINUTE;
    long seconds = deltaT / MILLIS_PER_SECOND;
    deltaT -= seconds * MILLIS_PER_SECOND;
    long milliseconds = deltaT;
    return String.format("%02d:%02d:%02d.%02d", hours, minutes, seconds, milliseconds);
  }

  public static LapTime find(int id) {
    try (Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM lap_times WHERE id = :id";
      return con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(LapTime.class);
    }
  }

  public void saveStartTime() {
    long start_time = System.currentTimeMillis();
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE lap_times SET start_time = :start_time WHERE id = :id";
      con.createQuery(sql).addParameter("start_time", start_time).addParameter("id", this.id).executeUpdate();
      this.start_time = start_time;
    }
  }

  public void saveEndTime() {
    long end_time = System.currentTimeMillis();
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE lap_times SET end_time = :end_time WHERE id = :id";
      con.createQuery(sql).addParameter("end_time", end_time).addParameter("id", this.id).executeUpdate();
      this.end_time = end_time;
    }
  }
}
