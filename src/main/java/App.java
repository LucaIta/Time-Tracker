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

    // post(new Route("/insertElement") {
    //   @Override
    //   public Object handle(Request request, Response response) {
    //     String item = (String) request.attribute("item");
    //     String value = (String) request.attribute("value");
    //     String dimension = (String) request.attribute("dimension");
    //     Element e = new Element(item, value, dimension);
    //     ElementDAO edao = new ElementDAO();
    //     edao.insert(e);
    //     JSONObject json = JSONObject.fromObject( e );
    //     return json;
    //   }
    // });
  }
}
