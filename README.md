# kafka-microservices

- Order API: create orders with customerId, productId and amount. It has its own database
- Credit Card API: Contains customers' balances. Every time an order is confirmed, this API is charge of verify and process
  the payments. It has its own database.
- Notifications API: communicate the user the state of the order, when is created, confirmed and paid.
- Inventory API: verify the product stock, before proceed with the payment. It has its own database.

## Architecture diagram

![architecture_diagram](./resources/imgs/arch_diagram.jpg)
