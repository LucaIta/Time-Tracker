import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;


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

    post("/tasks", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String input_task = request.queryParams("input_task");
      int input_hh = Integer.parseInt(request.queryParams("input_hh"));
      int input_mm = Integer.parseInt(request.queryParams("input_mm"));
      int input_ss = Integer.parseInt(request.queryParams("input_ss"));

      long goal = input_hh + input_mm + input_ss; // This line need to change with Millisecond convertion method

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

  }
}
