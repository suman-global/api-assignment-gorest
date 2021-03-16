Feature: To validate GoRest API functionality

  @API @Positive
  Scenario: 1.To validate Add user api
    Given I have the endpoint as "baseURL/ENDPOINT_ADD_USER"
    And I have valid Authorization token
    When I send the "post" request using request body as "AddUser"
      | ContentType      | application/json |                    |
      | key to be update | name             | Suman              |
      | key to be update | gender           | Female             |
      | key to be update | email            | testSumanNew@p.com |
      | header           | Authorization    | token              |
    Then I should see the response status code as 200
    And I should see the following parameters in response as
      | Description | Key  | Value |
      | Code        | code |   201 |
    And I fetched the following parameters in response payload as
      | Description | path    |
      | userId      | data.id |

  @API @Positive
  Scenario: 2.To validate Get user API
    Given I have the endpoint as "baseURL/ENDPOINT_GET_USER"
    And I have valid Authorization token
    When I send the "get" request to "ENDPOINT_GET_USER" using
      | ContentType | application/json |        |
      | path        | id               | userId |
    Then I should see the response status code as 200
    And I should see the following parameters in response as
      | Description | Key     | Value  |
      | Code        | data.id | userId |

  @API @Positive
  Scenario: 3.To validate Update user api
    Given I have the endpoint as "baseURL/ENDPOINT_GET_USER"
    And I have valid Authorization token
    When I send the "patch" request using request body as "AddUser"
      | ContentType      | application/json |                          |
      | key to be update | name             | SumanUpdate              |
      | key to be update | gender           | Female                   |
      | key to be update | email            | testSumanNewUpdate@p.com |
      | header           | Authorization    | token                    |
      | header           | Accept           | application/json         |
      | path             | id               | userId                   |
    Then I should see the response status code as 200
    And I should see the following parameters in response as
      | Description | Key        | Value                    |
      | Email       | data.email | testSumanNewUpdate@p.com |
      | Name        | data.name  | SumanUpdate              |

  @API @Positive
  Scenario: 4.To validate Delete user API
    Given I have the endpoint as "baseURL/ENDPOINT_GET_USER"
    And I have valid Authorization token
    When I send the "delete" request to "ENDPOINT_GET_USER" using
      | ContentType | application/json |        |
      | path        | id               | userId |
      | header      | Authorization    | token  |
    Then I should see the response status code as 200
    And I should see the following parameters in response as
      | Description | Key  | Value |
      | Code        | code |   204 |

  @API @Negative
  Scenario: 5.To validate Add user api without name value
    Given I have the endpoint as "baseURL/ENDPOINT_ADD_USER"
    And I have valid Authorization token
    When I send the "post" request using request body as "AddUser"
      | ContentType      | application/json |                    |
      | key to be update | gender           | Female             |
      | key to be update | email            | testSumanNew@p.com |
      | header           | Authorization    | token              |
    Then I should see the response status code as 200
    And I should see the following parameters in response as
      | Description  | Key             | Value          |
      | Code         | code            |            422 |
      | Name Message | data[0].message | can't be blank |

  @API @Negative
  Scenario: 6.To validate Add user api without gender value
    Given I have the endpoint as "baseURL/ENDPOINT_ADD_USER"
    And I have valid Authorization token
    When I send the "post" request using request body as "AddUser"
      | ContentType      | application/json |                    |
      | key to be update | name             | Suman              |
      | key to be update | email            | testSumanNew@p.com |
      | header           | Authorization    | token              |
    Then I should see the response status code as 200
    And I should see the following parameters in response as
      | Description  | Key             | Value          |
      | Code         | code            |            422 |
      | Name Message | data[0].message | can't be blank |

  @API @Negative
  Scenario: 7.To validate Add user api without email value
    Given I have the endpoint as "baseURL/ENDPOINT_ADD_USER"
    And I have valid Authorization token
    When I send the "post" request using request body as "AddUser"
      | ContentType      | application/json |        |
      | key to be update | name             | Suman  |
      | key to be update | gender           | Female |
      | header           | Authorization    | token  |
    Then I should see the response status code as 200
    And I should see the following parameters in response as
      | Description  | Key             | Value          |
      | Code         | code            |            422 |
      | Name Message | data[0].message | can't be blank |

  @API @Negative
  Scenario: 8.To validate Get invalid user API
    Given I have the endpoint as "baseURL/ENDPOINT_GET_USER"
    And I have valid Authorization token
    When I send the "get" request to "ENDPOINT_GET_USER" using
      | ContentType | application/json |     |
      | path        | id               | 000 |
    Then I should see the response status code as 200
    And I should see the following parameters in response as
      | Description | Key          | Value              |
      | Message     | data.message | Resource not found |
      | Code        | code         |                404 |

  @API @Negative
  Scenario: 9.To validate delete invalid user API
    Given I have the endpoint as "baseURL/ENDPOINT_GET_USER"
    And I have valid Authorization token
    When I send the "delete" request to "ENDPOINT_GET_USER" using
      | ContentType | application/json |       |
      | path        | id               |   000 |
      | header      | Authorization    | token |
    Then I should see the response status code as 200
    And I should see the following parameters in response as
      | Description | Key          | Value              |
      | Message     | data.message | Resource not found |
      | Code        | code         |                404 |

  @API @Negative
  Scenario: 10.To validate Add user api without token
    Given I have the endpoint as "baseURL/ENDPOINT_ADD_USER"
    And I have valid Authorization token
    When I send the "post" request using request body as "AddUser"
      | ContentType      | application/json |                    |
      | key to be update | name             | Suman              |
      | key to be update | gender           | Female             |
      | key to be update | email            | testSumanNew@p.com |
    Then I should see the response status code as 200
    And I should see the following parameters in response as
      | Description | Key          | Value                 |
      | Code        | code         |                   401 |
      | Message     | data.message | Authentication failed |
