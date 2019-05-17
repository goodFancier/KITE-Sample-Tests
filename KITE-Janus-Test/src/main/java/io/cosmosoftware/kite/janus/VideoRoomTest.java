package io.cosmosoftware.kite.janus;

import io.cosmosoftware.kite.janus.checks.AllVideoCheck;
import io.cosmosoftware.kite.janus.checks.FirstVideoCheck;
import io.cosmosoftware.kite.janus.steps.GetStatsStep;
import io.cosmosoftware.kite.janus.steps.JoinVideoCallStep;
import io.cosmosoftware.kite.util.TestUtils;
import org.openqa.selenium.WebDriver;
import org.webrtc.kite.steps.ScreenshotStep;
import org.webrtc.kite.tests.KiteBaseTest;
import org.webrtc.kite.tests.TestRunner;

import static org.webrtc.kite.Utils.getStackTrace;

public class VideoRoomTest extends KiteBaseTest {

  String demoName = "test";

  @Override
  public void populateTestSteps(TestRunner runner) {
    try {
      WebDriver webDriver = runner.getWebDriver();
      String roomUrl =
          getRoomManager().getRoomUrl() + "&username=user" + TestUtils.idToString(runner.getId());
//      runner.addStep(new JoinVideoCallStep(webDriver, roomUrl));
      runner.addStep(new FirstVideoCheck(webDriver));
      runner.addStep(new AllVideoCheck(webDriver, getMaxUsersPerRoom()));
      if (this.getStats()) {
        runner.addStep(new GetStatsStep(webDriver, getStatsConfig));
      }
      if (this.takeScreenshotForEachTest()) {
        runner.addStep(new ScreenshotStep(webDriver));
      }
    } catch (Exception e) {
      logger.error(getStackTrace(e));
    }
  }
}
