import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.ArrayList;
import java.util.List;


public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("tasks", Task.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Noah's routes for the timer
    post("/timer/createrun/:routine_id", (request, response) -> {
      int routineId = Integer.parseInt(request.params("routine_id"));
      Routine routine = Routine.find(routineId);
      Run newRun = new Run(routine.getId());
      newRun.save();
      String url = String.format("http://localhost:4567/timer/%d", newRun.getId());
      response.redirect(url);
      return null;
    });

    get("/timer/:run_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int runId = Integer.parseInt(request.params("run_id"));
      model.put("run", Run.find(runId));
      model.put("template", "templates/home.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/timer/start/:run_id", (request, response) -> {
      int runId = Integer.parseInt(request.params("run_id"));
      Run run = Run.find(runId);
      Routine routine = Routine.find(run.getRoutineId());
      routine.start();
      String url = String.format("http://localhost:4567/timer/%d", runId);
      response.redirect(url);
      return null;
    });

    post("/timer/logLap/:run_id", (request, response) -> {
      int runId = Integer.parseInt(request.params("run_id"));
      Run run = Run.find(runId);
      Routine routine = Routine.find(run.getRoutineId());
      long time = System.currentTimeMillis();
      routine.logLap(time);
      String url = String.format("http://localhost:4567/timer/%d", runId);
      response.redirect(url);
      return null;
    });

    post("/timer/end/:run_id", (request, response) -> {
      int runId = Integer.parseInt(request.params("run_id"));
      Run run = Run.find(runId);
      Routine routine = Routine.find(run.getRoutineId());
      routine.end();
      String url = String.format("http://localhost:4567/timer/%d", runId);
      response.redirect(url);
      return null;
    });
    //end Noah's routes

    post("/tasks", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String input_task = request.queryParams("input_task");
      String hour = request.queryParams("input_hh");
      String minute = request.queryParams("input_mm");
      String second = request.queryParams("input_ss");
      int number_hh;
      int number_mm;
      int number_ss;

      if (hour.equals("")) {
        number_hh = 0;
      } else {
        number_hh = Integer.parseInt(hour);
      }

      if (minute.equals("")) {
        number_mm = 0;
      } else {
        number_mm = Integer.parseInt(minute);
      }

      if (second.equals("")) {
        number_ss = 0;
      } else {
        number_ss = Integer.parseInt(second);
      }

      long goal = number_hh + number_mm + number_ss; // This line need to change with Millisecond convertion method

      Task newTask = new Task(input_task, goal);
      newTask.save();

      response.redirect("/");
      return null;
    });

    get("/timerDisplayer", (request, response) -> {  // this is just a test
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("tasks", Task.all());
      model.put("testLapTime", Task.all().get(0).getLapTimes().get(0));
      System.out.println(Task.all().get(0).getLapTimes().get(0).getId());

      // probably later they will be only the tasks of this routine
      model.put("template", "templates/timer_board.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

// Routine section --------------------
    get("/routines", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("routines", Routine.all());
      model.put("template", "templates/routines.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("routines/:id/lap", (request, response) -> {
      int routineId = Integer.parseInt(request.params("id"));
      Routine routine = Routine.find(routineId);
      long time = System.currentTimeMillis();
      routine.logLap(time);

      String url = String.format("http://localhost:4567/routines/%d", routineId);
      response.redirect(url);
      return null;
    });

    get("/routines/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int routineId = Integer.parseInt(request.params("id"));
      Routine routine = Routine.find(routineId);
      model.put("routine", routine);
      model.put("template", "templates/timer.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/routines/add_new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String input_routine = request.queryParams("input_routine");
      Routine newRoutine = new Routine(input_routine);
      newRoutine.save();
      response.redirect("/routines");
      return null;
    });

    post("/routines/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String delete_routine = request.queryParams("delete_routine");
      if (!(delete_routine.equals(""))) {
        Routine routine = Routine.find(Integer.parseInt(delete_routine));
        routine.delete();
      }
      response.redirect("/routines");
      return null;
    });

    post("/routines/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String update_routine = request.queryParams("update_routine");
      Routine routine = Routine.find(Integer.parseInt(update_routine));

      String update_input = request.queryParams("update_input");
      routine.update(update_input);

      response.redirect("/routines");
      return null;
    });

  }
}
