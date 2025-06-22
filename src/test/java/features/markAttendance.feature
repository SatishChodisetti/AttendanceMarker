Feature: Attendance Marking

  Scenario: Mark attendance on HR One portal
    Given user logs into HR One portal
    When user clicks on Mark Attendance
    And confirms attendance in the dialog
    Then attendance should be marked successfully
    
  
