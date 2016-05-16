import org.sql2o.*;

class LapTime {
  private int id;
  private long start_time;
  private long end_time;
  private int task_id;
  private final long MILLIS_PER_HOUR = 3600000L;
  private final long MILLIS_PER_MINUTE = 60000L;
  private final long MILLIS_PER_SECOND = 1000L;

  public LapTime(long start_time, long end_time, int task_id){
    this.start_time = start_time;
    this.end_time = end_time;
    this.task_id = task_id;
  }

  public long getStartTime() {
    return start_time;
  }

  public long getEndTime() {
    return end_time;
  }

  public long getId() {
    return task_id;
  }

  public int getTaskId() { // to be completed

  }

  public List<LapTime> all() { // not tested yet
    try (Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM lap_times";
      List<LapTime> laptimes = con.createQuery(sql).executeAndFetch(LapTime.class);
      return laptimes;
    }
  }

  public void addToTask(Task task) { // not tested yet
    try (Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO lap_times (start_time,end_time,task_id int) VALUES (:start_time, :end_time, :task_id)";
      con.createQuery(sql).addParameter("start_time",this.start_time)
                          .addParameter("end_time", this.end_time)
                          .addParameter("task_id", task.getId())
                          .executeUpdate();
    }
  }

  @Override
  public boolean equals(Laptime otherLapTime){ // not tested yet
    return this.id == otherLapTime.getId()
    && this.start_time == otherLapTime.getStartTime()
    && this.end_time == otherLapTime.getEndTime()
    && this.task_id == otherLapTime.getTaskId() // to be created
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
