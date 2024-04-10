# shop-points

## Project was written by DDD(Domain-driven design Structure)
* shop-points-app: main program entry and configuration
* shop-points-common: common support for project
* shop-points-domain: core business
* shop-points-infrastructure: DB support
* shop-points-trigger: project exposed apis(controller)

## Procedure to start the project
1. Open project with IDE
2. Run application at shop-points-app/src/main/java/coding/task/shoppoints/ShopPointsApplication.class
3. Open H2 DB console http://localhost:8080/h2-console
4. Login with url, username and password in shop-points-app/src/main/resources/application.yml
5. Run sql query in docs/shop_points.sql for providing test data
6. Use Postman to test the api, see the controller in shop-points-trigger module
   * http://localhost:8080/v1/shop/reward/1 GET
   * http://localhost:8080/v1/shop/reward/summary GET
   * http://localhost:8080/v1/shop/transaction POST, create single transaction
     ```json
     {
        "customerId": 1,
        "amount": 110,
        "issuedAt": "2024-03-05T19:48:30"
     }
     ```
   * http://localhost:8080/v1/shop/transaction PATCH, update single transaction
     ```json
     {
        "transactionId": 1,
        "amount": 110
     }
     ```
   * http://localhost:8080/v1/shop/transaction/batch POST, create a list of transactions
     ```json
     [
      {
        "customerId": 1,
        "amount": 50,
        "issuedAt": "2024-01-05T09:15:30"
      },
      {
        "customerId": 1,
        "amount": 100,
        "issuedAt": "2024-02-05T13:45:20"
      },
      {
        "customerId": 1,
        "amount": 75,
        "issuedAt": "2024-03-05T17:30:10"
      }
     ]
     ```
   * http://localhost:8080/v1/shop/transaction/barch PATCH, update a list of transactions
     ```json
     [
      {
        "transactionId": 1,
        "amount": 50
      },
      {
        "transactionId": 2,
        "amount": 100
      },
      {
        "transactionId": 3,
        "amount": 75
      }
     ]
     ```
7. Or run unit test and integration test in shop-points-app module
8. Thanks, feel free to ask me if you have any questions