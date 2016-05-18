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
  }
}
