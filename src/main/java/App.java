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

      model.put("template", "templates/home.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("routines/:id/lap", (request, response) -> {
      int routineId = Integer.parseInt(request.params("id"));
      Routine routine = Routine.find(routineId);
      long time = System.currentTimeMillis();
      routine.lap(time);

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
