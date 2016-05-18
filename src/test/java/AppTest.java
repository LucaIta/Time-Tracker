import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.sql2o.*;
import org.junit.*;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
    public DatabaseRule database = new DatabaseRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Time Tracker");
  }

  @Test
  public void taskIsCreateAndDisplayedTest() {
    goTo("http://localhost:4567/");
    fill("#input_task").with("Jumping jack 50 times");
    fill("#input_hh").with("0");
    fill("#input_mm").with("0");
    fill("#input_ss").with("60");
    submit("#input_goal_button");
    assertThat(pageSource()).contains("Jumping jack 50 times");
  }

// Routine section --------------------
  @Test
  public void newRoutineIsFilledAndDisplayedTest() {
    goTo("http://localhost:4567/routines");
    fill("#input_routine").with("Routine Number 1");
    submit("#add_routine_button");
    assertThat(pageSource()).contains("Routine Number 1");
  }

  @Test
  public void routineIsDeletedTest() {
    Routine testRoutine = new Routine("Routine Number 1");
    testRoutine.save();
    goTo("http://localhost:4567/routines");
    click("option", withText("Routine Number 1"));
    submit("#delete_routine_button");
    assertThat(pageSource()).doesNotContain("Routine Number 1");
  }

  @Test
  public void routineIsUpdatedTest() {
    Routine testRoutine = new Routine("Routine Number 1");
    testRoutine.save();
    goTo("http://localhost:4567/routines");
    click("option", withText("Routine Number 1"));
    fill("#update_input").with("My Morning");
    submit("#update_routine_button");
    assertThat(pageSource()).contains("My Morning");
  }

}
