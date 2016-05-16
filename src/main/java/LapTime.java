import org.sql2o.*;

class LapTime {
  private int id;
  private long start_time;
  private long end_time;
  private int task_id;

  public LapTime(long start_time, long end_time, int task_id){
    start_time = this.start_time;
    end_time = this.end_time;
    task_id = this.task_id;
  }

  public long getStartTime() {
    return start_time;
  }

}
