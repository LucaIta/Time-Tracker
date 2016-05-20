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

// Home section --------------------
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
      Run run = Run.find(runId);
      Routine routine = Routine.find(run.getRoutineId());
      model.put("run", run);
      model.put("routine", routine);
      model.put("template", "templates/timer.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/timer/:run_id/start", (request, response) -> {
      int runId = Integer.parseInt(request.params("run_id"));
      Run run = Run.find(runId);
      run.start();
      String url = String.format("http://localhost:4567/timer/%d", runId);
      response.redirect(url);
      return null;
    });

    post("/timer/:run_id/lap", (request, response) -> {
      int runId = Integer.parseInt(request.params("run_id"));
      Run run = Run.find(runId);
      run.logLap();
      String url = String.format("http://localhost:4567/timer/%d", runId);
      response.redirect(url);
      return null;
    });

    post("/timer/:run_id/stop", (request, response) -> {
      int runId = Integer.parseInt(request.params("run_id"));
      Run run = Run.find(runId);
      run.end();
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
      long number_hh;
      long number_mm;
      long number_ss;

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

      long goal = (number_hh * 3600000L) + (number_mm  * 60000L) + (number_ss * 1000L);
      Task newTask = new Task(input_task, goal);
      newTask.save();

      response.redirect("/");
      return null;
    });

    get("/timerDisplayer", (request, response) -> {  // this is just a test, should change the path
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("tasks", Task.all());
      model.put("testLapTime", Task.all().get(0).getLapTimes().get(0));
      // probably later they will be only the tasks of this routine
      model.put("template", "templates/timer_board.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/routineDisplayer/:routine_id", (request, response) -> {  // this is just a test, should change the path
      Map<String, Object> model = new HashMap<String, Object>();
      //model.put("tasks", Task.all()); // they should be only the task related to the routine
      int routineId = Integer.parseInt(request.params("routine_id"));
      Routine routine = Routine.find(routineId);
      model.put("routine", routine);
      model.put("LapTime", LapTime.class);
      model.put("template", "templates/routine.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

// Routine section --------------------
    get("/routines", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("routines", Routine.all());
      model.put("template", "templates/routines.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

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

    get("/routine_start_button/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int routineId = Integer.parseInt(request.params(":id"));
      Routine routine = Routine.find(routineId);
      model.put("routine", routine);
      model.put("template", "templates/routine_start_button.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

// Add Tasks to Routine section --------------------
    get("/routines_tasks", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("allRoutines", Routine.all());
      model.put("allTasks", Task.all());
      model.put("template", "templates/routines_tasks.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/routines_tasks", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String selectedRoutine = request.queryParams("select_routine");
      Routine routine = Routine.find(Integer.parseInt(selectedRoutine));

      String[] selectedTaskIds = request.queryParamsValues("task_id");
      if(selectedTaskIds != null) {
        for (String eachTaskId : selectedTaskIds) {
          Task task = Task.find(Integer.parseInt(eachTaskId));
          routine.addTask(task);
        }
        response.redirect("/routines_tasks");
        return null;
      } else {
        model.put("template", "templates/error.vtl");
        return new ModelAndView(model, layout);
      }
    }, new VelocityTemplateEngine());

// Routine section --------------------
    get("/tasks/update_delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("tasks", Task.all());
      model.put("template", "templates/tasks.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/tasks/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String delete_task = request.queryParams("delete_task");
      if (!(delete_task.equals(""))) {
        Task task = Task.find(Integer.parseInt(delete_task));
        task.delete();
      }
      response.redirect("/tasks/update_delete");
      return null;
    });

    post("/tasks/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String select_task = request.queryParams("select_task");
      Task task = Task.find(Integer.parseInt(select_task));

      if (!(select_task.equals(""))) {
        String new_name = request.queryParams("update_name");
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

        long new_goal = (number_hh * 3600000L) + (number_mm * 60000L) + (number_ss * 1000L); // This line need to change with Millisecond convertion method

        task.update(new_name, new_goal);
      }

      response.redirect("/tasks/update_delete");
      return null;
    });
  }
}
