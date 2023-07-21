Feature: Verify Inventory API endpoints

  Scenario Outline: Get all available inventories
    Given Get inventories "<url>"
    Then Response contains inventories

    Examples:
      | url |
      | /v1/inventories |

  Scenario Outline: Get inventory for given productId
    Given Get inventory by productId "<url>"
    Then Response contains inventory

    Examples:
      | url | productId |
      | /v1/inventories/<productId> | 2 |

  Scenario Outline: Update inventory for given productId in Order
    Given Get inventory by productId "<urlInventoryByProductId>"
    Given Send order to "<updateInventory>"
    Then Inventory is updated

    Examples:
      | urlInventoryByProductId | productId | updateInventory |
      | /v1/inventories/<productId> | 1 | v1/inventory |

