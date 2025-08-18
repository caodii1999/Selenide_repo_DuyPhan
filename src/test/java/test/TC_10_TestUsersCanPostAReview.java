package test;

import model.Review;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.MyAccountPage;
import pages.ProductDetailsPage;
import pages.ProductsPage;

@Test
public class TC_10_TestUsersCanPostAReview extends TestBase {

    SoftAssert softAssert = new SoftAssert();
    HomePage homePage = new HomePage();
    MyAccountPage myAccountPage = new MyAccountPage();
    ProductsPage productsPage = new ProductsPage();
    ProductDetailsPage productDetailsPage = new ProductDetailsPage();

    Review expectedReview;
    Review actualReview;

    int expectedNumberOfReviews;
    int actualNumberOfReview;

    public void TestUsersCanPostAReview() {

        homePage.clickOnMyAccountButton();

        myAccountPage.login();

        myAccountPage.navigateToShopPage();

        productsPage.selectRandomItem();

        productDetailsPage.clickOnReviewTab();

        expectedReview = new Review(productDetailsPage.selectRandomStar(), productDetailsPage.writeComment());

        productDetailsPage.clickOnSubmitBtn();

        productDetailsPage.clickOnReviewTab();

        actualReview = new Review(productDetailsPage.getNewReviewStar(), productDetailsPage.getNewReviewComment());

        softAssert.assertEquals(actualReview, expectedReview, "Verify star and review content are correct");

        expectedNumberOfReviews = productDetailsPage.getNumberOfReviewsFromList();

        actualNumberOfReview = productDetailsPage.getActualNumberOfReview();

        softAssert.assertEquals(actualNumberOfReview, expectedNumberOfReviews, "Verify number of reviews is updated");

        softAssert.assertAll();

    }
}
