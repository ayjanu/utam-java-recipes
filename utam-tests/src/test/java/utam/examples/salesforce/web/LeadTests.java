/*
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: MIT
 * For full license text, see the LICENSE file in the repo root
 * or https://opensource.org/licenses/MIT
 */
package utam.examples.salesforce.web;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.util.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import net.bytebuddy.dynamic.scaffold.TypeWriter.FieldPool.Record;
import utam.global.pageobjects.AppNavBar;
import utam.global.pageobjects.ConsoleObjectHome;
import utam.global.pageobjects.RecordActionWrapper;
import utam.aura.pageobjects.InputText.InputElement;
import utam.force.pageobjects.ListViewManagerHeader;
import utam.navex.pageobjects.DesktopLayoutContainer;
import utam.records.pageobjects.BaseRecordForm;
import utam.records.pageobjects.LwcRecordLayout;
import utam.records.pageobjects.RecordLayoutItem;
import utam.sfdx.pageobjects.LeadTestCase;
import utam.sfdx.pageobjects.LeadTestCase.LeadsElement;
import utam.utils.salesforce.TestEnvironment;
import utam.utils.salesforce.RecordType;

/**
 * IMPORTANT: Page objects and tests for Salesforce UI are compatible with the application version
 * mentioned in published page objects Test environment is private SF sandbox, not available for
 * external users and has DEFAULT org setup
 *
 * @author Salesforce
 * @since June 2022
 */
public class LeadTests extends SalesforceWebTestBase {

  private final TestEnvironment testEnvironment = getTestEnvironment("sandbox");

  @BeforeTest
  public void setup() {
    setupFirefox();
    login(testEnvironment, "home");
  }

  @Test
  public void testNavigateToNanBarItem() {
    getDriver().get(testEnvironment.getRedirectUrl());
    log("Load Desktop layout container");
    DesktopLayoutContainer layoutContainer = from(DesktopLayoutContainer.class);

    log("Navigate to nav bar item accounts");
    AppNavBar navBar = layoutContainer.getAppNav().getAppNavBar();
    navBar.getNavItem("Lead").clickAndWaitForUrl("Lead");
  }

  @Test
  public void testNavigateToNanBarOverflowItem() {
    getDriver().get(testEnvironment.getRedirectUrl());
    log("Load Desktop layout container");
    DesktopLayoutContainer layoutContainer = from(DesktopLayoutContainer.class);

    AppNavBar navBar = layoutContainer.getAppNav().getAppNavBar();
    log("Navigate to overflow menu item");
    navBar.getShowMoreMenuButton().expand();
    // menu item with name 'Forecasts' should present in overflow items
    navBar.getShowMoreMenuButton().getMenuItemByText("Forecasts").clickAndWaitForUrl("forecasting");
  }


  // @Test
  // public void testClickNew() {
  //   getDriver().get(testEnvironment.getRedirectUrl());
  //   log("Load Desktop layout container");
  //   DesktopLayoutContainer layoutContainer = from(DesktopLayoutContainer.class);
  //   log("Navigate to nav bar item accounts");
  //   AppNavBar navBar = layoutContainer.getAppNav().getAppNavBar();
  //   navBar.getNavItem("Lead").clickAndWaitForUrl("Lead");
  //   openRecordModal(RecordType.Lead);
  //   // final LeadTestCase homePage = navigateToPortalHome();
  //   // homePage.waitForVisible();
  //   // assert homePage.isPresent();
  //   // assert homePage.isVisible();
  //   // LeadsElement user = homePage.getLeads();
  //   // user.clearAndType("manoj");
  //   RecordActionWrapper recordTypeModal = from(RecordActionWrapper.class);
  //   // recordTypeModal.waitForChangeRecordFooter().clickButton("New");
  //   BaseRecordForm recordForm = recordTypeModal.getRecordForm();
  //   LwcRecordLayout recordLayout = recordForm.getRecordLayout();
  //   RecordLayoutItem item = recordLayout.getItem(1,2,1);
  //   final String firstName = "Ayan";
  //   item.getTextInput().setText(firstName);
  // }

   /**
   * navigate to object home via URL and click New
   *
   * @param recordType record type affects navigation url
   */
  private void openRecordModal(RecordType recordType) {

    log("Navigate to an Object Home for " + recordType.name());
    getDriver().get(recordType.getObjectHomeUrl(testEnvironment.getRedirectUrl()));

    log("Load Accounts Object Home page");
    ConsoleObjectHome objectHome = from(ConsoleObjectHome.class);
    ListViewManagerHeader listViewHeader = objectHome.getListView().getHeader();

    log("List view header: click button 'New'");
    listViewHeader.waitForAction("New").click();

    log("Load Record Form Modal");
   }

  @Test
  public void testAccountRecordCreation() {
    openRecordModal(RecordType.Lead);
    // todo - depending on org setup, modal might not present, then comment next lines
    log("Load Change Record Type Modal");
    RecordActionWrapper recordTypeModal = from(RecordActionWrapper.class);
    log("Change Record Type Modal: click button 'New'");
    // recordTypeModal.waitForChangeRecordFooter().clickButton("Next");

    log("Load Record Form Modal");
    // RecordActionWrapper recordFormModal = from(RecordActionWrapper.class);
    BaseRecordForm recordForm = recordTypeModal.getRecordForm();
    LwcRecordLayout recordLayout = recordForm.getRecordLayout();
    // System.out.println(recordLayout.getInputName().toString());
    log("Access record form item by index");
    RecordLayoutItem item = recordLayout.getItem(1,3,1);
    log("Enter Company name");
    final String companyName = "Solonus";
    item.getTextInput().setText(companyName);

    item = recordLayout.getItem(1,4,1);
    log("Enter Title name");
    final String fax = "972";
    item.getTextInput().setText(fax);

    // log("Save new record");
    // recordForm.clickFooterButton("Save");
    // recordFormModal.waitForAbsence();

  }
  
  @AfterTest
  public final void tearDown() {
    quitDriver();
  }
}
