package utam.examples.salesforce.web;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utam.global.pageobjects.AppNavBar;
import utam.navex.pageobjects.DesktopLayoutContainer;
import utam.utils.salesforce.TestEnvironment;

public class LeadTestCaseTests extends SalesforceWebTestBase {
    
   private final TestEnvironment testEnvironment = getTestEnvironment("sandbox");

    @BeforeTest
    public void setup() {
        setupFirefox();
        login(testEnvironment, "home");
    }

    public void testNavigateToNanBarItem() {
        getDriver().get(testEnvironment.getRedirectUrl());
        DesktopLayoutContainer layoutContainer = from(DesktopLayoutContainer.class);
        AppNavBar navBar = layoutContainer.getAppNav().getAppNavBar();
        navBar.getNavItem("Lead").clickAndWaitForUrl("Lead");
    }


    @AfterTest
    public final void tearDown() {
        quitDriver();
    }

    
}