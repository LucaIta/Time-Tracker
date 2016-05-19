import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class LapTime {
  private int id;
  private long start_time;
  private long end_time;
  private int task_id;
  private final long MILLIS_PER_HOUR = 3600000L;
  private final long MILLIS_PER_MINUTE = 60000L;
  private final long MILLIS_PER_SECOND = 1000L;

  public LapTime(long start_time){
    this.start_time = start_time;
    // this.end_time = end_time;
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

  public static List<LapTime> all() { // not tested yet
    try (Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM lap_times";
      List<LapTime> laptimes = con.createQuery(sql).executeAndFetch(LapTime.class);
      return laptimes;
    }
  }


  public void addToTask(Task task) { // not tested yet
    try (Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO lap_times (start_time,task_id) VALUES (:start_time, :task_id)";
      this.task_id = task.getId();
      this.id = (int) con.createQuery(sql, true)
                          .addParameter("task_id", this.task_id)
                          .addParameter("start_time", this.start_time)
                          .executeUpdate().getKey();
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
           this.task_id == laptime.getTaskId();
    }
  }

  public long getDifference() {
    long difference = this.end_time - this.start_time;
    return difference;
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

  public static long getAverageTime(ArrayList<Long> timesList) {
    long sum_time = 0L;
    for (Long time : timesList) {
      sum_time += time;
    }
    long averTime;
    averTime = sum_time / timesList.size();
    return averTime;
  }

  public static long getBestTime(ArrayList<Long> timesList) {
    long best_time;
    best_time = timesList.get(0);
    for (Long time : timesList) {
      if (time < best_time) {
        best_time = time;
      }
    }
    return best_time;
  }


}
