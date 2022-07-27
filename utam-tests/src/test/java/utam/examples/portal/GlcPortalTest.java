/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: MIT
 * For full license text, see the LICENSE file in the repo root
 * or https://opensource.org/licenses/MIT
 */
package utam.examples.portal;

import static org.testng.Assert.expectThrows;

import org.openqa.selenium.StaleElementReferenceException;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utam.base.UtamWebTestBase;
import utam.core.element.BasicElement;
import utam.core.selenium.element.LocatorBy;
import utam.force.pageobjects.RecordType;
import utam.global.pageobjects.ConsoleObjectHome;
import utam.global.pageobjects.RecordActionWrapper;
import utam.force.pageobjects.ListViewManagerHeader;
import utam.records.pageobjects.BaseRecordForm;
import utam.records.pageobjects.LwcRecordLayout;
import utam.records.pageobjects.RecordLayoutItem;
import utam.sfdx.pageobjects.Glc;
import utam.sfdx.pageobjects.Glc.InputElement;

/**
 * Example of tests for https://utam.dev
 *
 * @author Salesforce
 * @since Dec 2021
 */
public class GlcPortalTest extends UtamWebTestBase {



  @BeforeTest
  public void setup() {
    setupFirefox();
  }

  private Glc navigateToPortalHome() {

    log("Navigate to portal");
    getDriver().get("https://mygreenlight.biz/login");

    log("Load Home Page");
    return from(Glc.class);
  }

  /** example of an element to become stale */
  @Test
  public void testUser() {
    final Glc homePage = navigateToPortalHome();

    log("Confirm that everything is present and visible");
    homePage.waitForVisible();
    assert homePage.isPresent();
    assert homePage.isVisible();

    log("Typed Username");
    RecordActionWrapper recordTypeModal = from(RecordActionWrapper.class);
    // recordTypeModal.waitForChangeRecordFooter().clickButton("New");
    BaseRecordForm recordForm = recordTypeModal.getRecordForm();
    LwcRecordLayout recordLayout = recordForm.getRecordLayout();
    RecordLayoutItem item = recordLayout.getItem(1,1,1);
    final String firstName = "Ayan";
    item.getTextInput().setText(firstName);
    // InputElement user = homePage.getInput("username");
    // user.clearAndType("manoj");

    log("Typed Password");
    InputElement pass = homePage.getInput("password");
    pass.clearAndType("Gr33nL1ght1$");

    log("Reload web page by navigating to its URL again");
    getDriver().get("https://utam.dev");
  }


  @AfterTest
  public final void tearDown() {
    quitDriver();
  }

  
}
