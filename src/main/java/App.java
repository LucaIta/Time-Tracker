import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;


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

// Routine section --------------------
    get("/routines", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("routines", Routine.all());
      model.put("template", "templates/routines.vtl");
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

  }
}
