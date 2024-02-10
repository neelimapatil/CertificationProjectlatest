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

Feature: Place order functionality
 @Placeorder
  Scenario: Place order for selected items from Cart
  	Given User login to the application with valid credentails
  	And User clicks on Cart link
    And User checks if items are already added on Cart Page
    And User clicks on Place order button and validates window is opened
    And User fills all required details on place order window
    And User clicks on Purchase button and validates purchase completion message
 
     
