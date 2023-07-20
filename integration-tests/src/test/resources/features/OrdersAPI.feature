Feature: verify order creation

  Scenario Outline: List orders
    Given Get call to "<url>"
    Then Response is "<statusCode>"

    Examples:
    | url | statusCode |
    | /v1/orders | 200    |


  Scenario Outline: Create Order
    Given Post call to "<url>"
    Then Response is "<statusCode>"
    Given Get call to "<listUrl>"
    Then Response is "<statusCode>"
    Then Response contains


    Examples:
      | url | statusCode | listUrl |
      | /v1/order | 200    | /v1/orders |


