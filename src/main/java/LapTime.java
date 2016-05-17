import org.sql2o.*;
import java.util.List;

class LapTime {
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

  public long getId() {
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

  public String getDifferenceAsString() {
    long deltaT = end_time - start_time;
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
