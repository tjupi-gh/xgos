# Christmas Gifts Ordering System (XGOS)

## Abstract

System exposes API to create a new order and retrieve order details. The system design is depicted in xgos.pdf.

## Start application

`mvn spring-boot:run`

## Access points

### Details

Endpoints
1. `POST /orders` Creates new order derived from kids' wishes. Assumes a wish is photoed and uploaded to S3 before. Returns new order id in Location header.

2. `GET /orders/{orderId}` returns an order details.

### Testing.

1. Create an order

`curl localhost:8080/orders -X POST -d '{"requestor":{"address": "Krakow, PL", "name": "John Doe"}, "xgPictureS3Location": "s3://aaa.png"}' -H 'Content-Type: application/json' -v`

Note order ID, ex

`curl localhost:8080/orders -X POST -d '{"requestor":{"address": "Krakow, PL", "name": "John Doe"}, "xgPictureS3Location": "s3://aaa.png"}' -H 'Content-Type: application/json' -v  2>&1 | grep Location`

2. Retrieve order details

`curl localhost:8080/orders/0c60f686-e14c-45b4-ba11-c71f2831b977`
