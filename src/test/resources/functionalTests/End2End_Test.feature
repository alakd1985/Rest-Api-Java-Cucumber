Feature: End to end Tests for ToolsQA Book Store Api
  Description: The purpose of these tests are to cover end to end happy flows for customer
  Book store Swagger URL: http://bookstore.toosqa.com/swagger/index/html
#  Background: User generates token for Authorisation

  Scenario: the Authorized user can Add and Remove a book.

    Given I am an authorized user
    When A list of books are available
    And I add a book to my reading list
    And I remove a book from my reading list
    Then the book is removed


