package io.cosmosoftware.kite.openvidu;

import io.cosmosoftware.kite.openvidu.checks.AllVideoCheck;
import io.cosmosoftware.kite.openvidu.checks.FirstVideoCheck;
import io.cosmosoftware.kite.openvidu.steps.GoJoinPageStep;
import io.cosmosoftware.kite.openvidu.steps.JoinRoomStep;
import io.cosmosoftware.kite.openvidu.steps.SetUserIdStep;
import org.openqa.selenium.WebDriver;
import org.webrtc.kite.steps.ScreenshotStep;
import org.webrtc.kite.tests.KiteBaseTest;
import org.webrtc.kite.tests.TestRunner;

import static org.webrtc.kite.Utils.getStackTrace;

public class KiteOpenViduTest extends KiteBaseTest {
  public KiteOpenViduTest() {
    super();
  }

  @Override
  protected void populateTestSteps(TestRunner runner) {
    try {
      WebDriver webDriver = runner.getWebDriver();
      runner.addStep(new GoJoinPageStep(webDriver, getRoomManager().getRoomUrl()));
      runner.addStep(new SetUserIdStep(webDriver, "user" + runner.getId()));
      runner.addStep(new JoinRoomStep(webDriver));
      if (!fastRampUp()) {
        runner.addStep(new FirstVideoCheck(webDriver));
        runner.addStep(new AllVideoCheck(webDriver, getMaxUsersPerRoom()));
      }
      if (takeScreenshotForEachTest()) {
        runner.addStep(new ScreenshotStep(webDriver));
      }
    } catch (Exception e) {
      logger.error(getStackTrace(e));
    }
  }
}
