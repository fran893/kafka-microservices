Feature: Verify microservices communication through Kafka. Order creation, payment processing and inventory update

  Scenario Outline: 1. Get current inventory by productId
    Given Get current inventory by productId "<inventoryByProductId>"
    Then Validate current inventory

    Examples:
      | inventoryByProductId | productId |
      | /v1/inventories/<productId> | 2 |

  Scenario Outline: 2. Get current balance by customerId
    Given Get current balance by customerId "<balanceByCustomerId>"
    Then Validate current balance

    Examples:
      | balanceByCustomerId | customerId |
      | /v1/balances/<customerId> | 1 |

  Scenario Outline: 3. Create Order
    Given Create order "<orderCreation>"
    Then Validate order "<statusCode>"

    Examples:
      | orderCreation | statusCode |
      | /v1/order | 200 |

  Scenario Outline: 4. Get new balance by customerId
    Given Get new balance after update "<balanceByCustomerId>"
    Then Check new balance

    Examples:
      | balanceByCustomerId | customerId |
      | /v1/balances/<customerId> | 1 |

  Scenario Outline: 5. Get new inventory by productId
    Given Get new inventory after update "<inventoryByProductId>"
    Then Check new inventory

    Examples:
      | inventoryByProductId | productId |
      | /v1/inventories/<productId> | 2 |