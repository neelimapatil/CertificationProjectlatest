#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template

Feature: Product selection
 @Smoke
  Scenario: Product selection by the user
  	Given User login to the application with valid credentails
  	When User selects the category as per test data sheet 
    And User selects the subcateogy as per test data sheet
    And User clicks on Add to Cart button and navigates to Cart Page
    Then User validates that selected item is added in Cart
    And User nagivates back to Home Page
    Then User adds remaining products from test data sheet to cart
     
