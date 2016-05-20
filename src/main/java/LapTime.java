import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

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

  public LapTime(long start_time){
   this.start_time = start_time;
  }

  public void addToTask(Task task) {
    try (Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO lap_times (start_time,task_id) VALUES (:start_time, :task_id)";
      con.createQuery(sql).addParameter("start_time", System.currentTimeMillis()).addParameter("task_id", this.task_id).executeUpdate();
    }
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

  public static List<LapTime> all() {
    try (Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM lap_times";
      List<LapTime> laptimes = con.createQuery(sql).executeAndFetch(LapTime.class);
      return laptimes;
    }
  }

  //COMMENT THIS BACK IN IF THINGS DON'T WORK
  // public void addToTask(Task task) {
  //   try (Connection con = DB.sql2o.open()){
  //     String sql = "INSERT INTO lap_times (start_time,task_id) VALUES (:start_time, :task_id)";
  //     this.task_id = task.getId();
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
    boolean negative = false;
    if (deltaT < 0) {
      deltaT *= -1;
      negative = true;
    }

    long hours = deltaT / MILLIS_PER_HOUR;
    deltaT -= hours * MILLIS_PER_HOUR;
    long minutes = deltaT / MILLIS_PER_MINUTE;
    deltaT -= minutes * MILLIS_PER_MINUTE;
    long seconds = deltaT / MILLIS_PER_SECOND;
    deltaT -= seconds * MILLIS_PER_SECOND;
    long milliseconds = deltaT;
    if (negative) {
      return String.format("-%02d:%02d:%02d.%02d", hours, minutes, seconds, milliseconds);
    } else {
      return String.format("+%02d:%02d:%02d.%02d", hours, minutes, seconds, milliseconds);
    }
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

  public static long getAverageTime(List<LapTime> timesList) {
    long sum_time = 0L;
    for (LapTime time : timesList) {
      if (time.getEndTime() > 0 && time.getStartTime() > 0) {
        long userTime = time.getEndTime() - time.getStartTime();
        sum_time += userTime;
      }
    }
    long averTime = 0L;
    if (timesList.size() != 0) {
      averTime = sum_time / timesList.size();
    }
    return averTime;
  }

  public static long getBestTime(List<LapTime> lapTimes) {

    ArrayList<Long> timesList = new ArrayList<Long>();

    for(LapTime lapTime : lapTimes) {
      if (lapTime.getEndTime() > 0 && lapTime.getStartTime() > 0) {
        long userTime = lapTime.getEndTime() - lapTime.getStartTime();
        timesList.add(userTime);
      }
    }
    long best_time;
    best_time = timesList.get(0);
    for (Long time : timesList) {
      if (time < best_time) {
        best_time = time;
      }
    }
    return best_time;
  }

  public static long getTotalTime(List<LapTime> timesList) {
    long total_time = 0L;
    for (LapTime time : timesList) {
      long userTime = time.getEndTime() - time.getStartTime();
      total_time += userTime;
    }
    return total_time;
  }
}
