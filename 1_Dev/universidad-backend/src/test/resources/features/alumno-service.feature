Feature: Alumno Service Integration Tests

  Scenario: Save a new alumno
    Given a new alumno with name "Carlos"
    Then the alumno should be saved with name "Carlos"

  Scenario: Update an existing alumno
    Given a new alumno with name "Carla"
    When I save the alumno
    Then the alumno should be saved with name "Carla"
    When I update the alumno name to "Carla Actualizada"
    Then the alumno should be updated with the name "Carla Actualizada"

  Scenario: Read all alumnos
    Given there are alumnos in the system
    Then I should see a non-empty list of alumnos

  Scenario: Delete an existing alumno
    Given a new alumno with name "Carmen"
    When I save the alumno
    Then the alumno should be saved with name "Carmen"
    When I delete the alumno
    Then the alumno should no longer exist in the system
