Feature: Verify Balance API endpoints

  Scenario Outline: Get available balances
    Given Get balances "<url>"
    Then Response contains balances

    Examples:
      | url |
      | /v1/balances |

    Scenario Outline: Get balance for given customerId
      Given Get balance by customerId "<url>" "<customerId>"
      Then Response contain balance

      Examples:
        | url | customerId |
        | /v1/balances/<customerId> | 1 |