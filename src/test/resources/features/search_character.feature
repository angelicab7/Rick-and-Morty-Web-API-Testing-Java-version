@search_character
Feature: Search and filter Rick and Morty characters

  Background:
    Given I am on the Rick and Morty web page

  @characters_displayed
  Scenario: Verify Characters List is displayed
    Then I should see the list of Characters displayed

  @search_and_filter
  Scenario Outline: Search and filter combined
    Given I am on Characters Page
    When I click on the sort dropdown and select "A - Z" option
    Then I should see characters sorted from A to Z
    When I search for character "<character>"
    And I select "<species>" from the species filter
    Then I should see search results for "<character>"
    And I should see only "<species>" characters displayed

    Examples:
      | character | species |
      | Rick      | Human   |
      | Morty     | Human   |

