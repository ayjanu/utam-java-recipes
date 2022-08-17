/*
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: MIT
 * For full license text, see the LICENSE file in the repo root
 * or https://opensource.org/licenses/MIT
 */
package utam.examples.salesforce.web;


import java.text.DateFormat;
import java.util.Calendar;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utam.force.pageobjects.ListViewManagerHeader;
import utam.force.pageobjects.OutputLookup;
import utam.global.pageobjects.AppNavBar;
import utam.global.pageobjects.ConsoleObjectHome;
import utam.global.pageobjects.RecordActionWrapper;
import utam.global.pageobjects.RecordHomeFlexipage2;
import utam.lightning.pageobjects.Input;
import utam.lightning.pageobjects.Picklist;
import utam.navex.pageobjects.DesktopLayoutContainer;
import utam.records.pageobjects.BaseRecordForm;
import utam.records.pageobjects.LwcHighlightsPanel;
import utam.records.pageobjects.LwcRecordLayout;
import utam.records.pageobjects.RecordLayoutInputName;
import utam.records.pageobjects.RecordLayoutItem;
import utam.utils.salesforce.RecordType;
import utam.utils.salesforce.TestEnvironment;

/**
 * IMPORTANT: Page objects and tests for Salesforce UI are compatible with the
 * application version
 * mentioned in published page objects Test environment is private SF sandbox,
 * not available for
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

  private void gotoRecordHomeByUrl(RecordType recordType, String recordId) {
    String recordHomeUrl = recordType.getRecordHomeUrl(testEnvironment.getBaseUrl(), recordId);

    log("Navigate to the Record Home by URL: " + recordHomeUrl);
    getDriver().get(recordHomeUrl);
  }

  //@Test
  public void leadCreation() {
    getDriver().get(testEnvironment.getRedirectUrl());
    DesktopLayoutContainer layoutContainer = from(DesktopLayoutContainer.class);
    AppNavBar navBar = layoutContainer.getAppNav().getAppNavBar();
    navBar.getNavItem("Lead").clickAndWaitForUrl("Lead");
    ConsoleObjectHome objectHome = from(ConsoleObjectHome.class);
    ListViewManagerHeader listViewHeader = objectHome.getListView().getHeader();
    listViewHeader.waitForAction("New").click();
    RecordActionWrapper recordTypeModal = from(RecordActionWrapper.class);
    BaseRecordForm recordForm = recordTypeModal.getRecordForm();
    LwcRecordLayout recordLayout = recordForm.getRecordLayout();
    RecordLayoutItem item = recordLayout.getItem(1, 1, 2);
    item.getInput().setText("9725551234");
    item = recordLayout.getItem(1, 2, 1);
    RecordLayoutInputName name = item.getInputName();
    Picklist salutation = name.getInputName().getSalutationPicklist();
    salutation.getBaseCombobox().expand();
    salutation.getBaseCombobox().pickItem(2);
    Input firstName = name.getInputName().getFirstNameInput();
    firstName.setText("Ayan");
    Input lastName = name.getInputName().getLastNameInput();
    lastName.setText("Jannu");
    item = recordLayout.getItem(1, 3, 1);
    final String companyName = "Solonus";
    item.getInput().setText(companyName);
    item = recordLayout.getItem(1, 3, 2);
    item.getInput().setText("98484848");
    item = recordLayout.getItem(1, 4, 1);
    item.getTextInput().setText("Java Developer");
    item = recordLayout.getItem(1, 4, 2);
    item.getInput().setText("ayansjannu@gmail.com");
    item = recordLayout.getItem(1, 5, 1);
    item.getPicklist().getBaseCombobox().expand();
    item.getPicklist().getBaseCombobox().pickItem(2);
    recordForm.clickFooterButton("Save");
  }

  @Test
  public void leadConversion() {
    // todo - replace with existing Lead Id for the environment
    final String leadId = "00Q8X00001m54HMUAY";
    gotoRecordHomeByUrl(RecordType.Lead, leadId);

    log("Load Lead Record Home page");
    RecordHomeFlexipage2 recordHome = from(RecordHomeFlexipage2.class);


    log("Access Record Highlights panel");
    LwcHighlightsPanel highlightsPanel = recordHome.getHighlights();

    log("Click dropdown");
    highlightsPanel.getActions().getDropdownButton().clickButton();

  }

  //@AfterTest
  public final void tearDown() {
    // quitDriver();
  }

}
