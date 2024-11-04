#### 3.3.2 Test the endpoints using a dev.http file. Document the output in your README.md file to verify the functionality.
#### 3.3.3 As a minimum you should request all endpoints once to get all trips, get a trip by id, adding a trip, updating a trip, and delete a trip. Also add a guide to a trip. For each request, document the response in your README.md file by copying the response.

Populate Database
```
[
  {
    "id": 1,
    "startTime": [
      10,
      0
    ],
    "endTime": [
      10,
      0
    ],
    "startPosition": "Los Angeles",
    "name": "California Beach Getaway",
    "price": 1500,
    "category": "BEACH",
    "guideDTO": {
      "id": 1,
      "firstName": "Alice",
      "lastName": "Johnson",
      "email": "alice.johnson@example.com",
      "phone": "123-456-7890",
      "yearsOfExperience": 5
    }
  },
  {
    "id": 2,
    "startTime": [
      8,
      0
    ],
    "endTime": [
      15,
      0
    ],
    "startPosition": "New York City",
    "name": "NYC Urban Adventure",
    "price": 1200,
    "category": "CITY",
    "guideDTO": {
      "id": 2,
      "firstName": "Bob",
      "lastName": "Smith",
      "email": "bob.smith@example.com",
      "phone": "234-567-8901",
      "yearsOfExperience": 8
    }
  },
  {
    "id": 3,
    "startTime": [
      9,
      20
    ],
    "endTime": [
      20,
      20
    ],
    "startPosition": "Miami",
    "name": "Miami Beach Party",
    "price": 1800,
    "category": "BEACH",
    "guideDTO": {
      "id": 3,
      "firstName": "Carol",
      "lastName": "Davis",
      "email": "carol.davis@example.com",
      "phone": "345-678-9012",
      "yearsOfExperience": 3
    }
  }
]
```

Get all trips
```
GET http://localhost:7070/api/trips

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 10:04:46 GMT
Content-Type: application/json
Content-Length: 821

[
  {
    "id": 1,
    "startTime": [
      10,
      0
    ],
    "endTime": [
      10,
      0
    ],
    "startPosition": "Los Angeles",
    "name": "California Beach Getaway",
    "price": 1500,
    "category": "BEACH",
    "guideDTO": {
      "id": 1,
      "firstName": "Alice",
      "lastName": "Johnson",
      "email": "alice.johnson@example.com",
      "phone": "123-456-7890",
      "yearsOfExperience": 5
    }
  },
  {
    "id": 2,
    "startTime": [
      8,
      0
    ],
    "endTime": [
      15,
      0
    ],
    "startPosition": "New York City",
    "name": "NYC Urban Adventure",
    "price": 1200,
    "category": "CITY",
    "guideDTO": {
      "id": 2,
      "firstName": "Bob",
      "lastName": "Smith",
      "email": "bob.smith@example.com",
      "phone": "234-567-8901",
      "yearsOfExperience": 8
    }
  },
  {
    "id": 3,
    "startTime": [
      9,
      20
    ],
    "endTime": [
      20,
      20
    ],
    "startPosition": "Miami",
    "name": "Miami Beach Party",
    "price": 1800,
    "category": "BEACH",
    "guideDTO": {
      "id": 3,
      "firstName": "Carol",
      "lastName": "Davis",
      "email": "carol.davis@example.com",
      "phone": "345-678-9012",
      "yearsOfExperience": 3
    }
  }
]
```

Get a trip by id
```
GET http://localhost:7070/api/trips/1

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 10:04:55 GMT
Content-Type: application/json
Content-Length: 282

{
  "id": 1,
  "startTime": [
    10,
    0
  ],
  "endTime": [
    10,
    0
  ],
  "startPosition": "Los Angeles",
  "name": "California Beach Getaway",
  "price": 1500,
  "category": "BEACH",
  "guideDTO": {
    "id": 1,
    "firstName": "Alice",
    "lastName": "Johnson",
    "email": "alice.johnson@example.com",
    "phone": "123-456-7890",
    "yearsOfExperience": 5
  }
}
```

Create new trip
```
POST http://localhost:7070/api/trips

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 10:05:13 GMT
Content-Type: application/json
Content-Length: 141

{
  "id": 4,
  "startTime": [
    10,
    0
  ],
  "endTime": [
    18,
    0
  ],
  "startPosition": "Tallinn",
  "name": "Trip to Tallinn",
  "price": 100,
  "category": "CITY",
  "guideDTO": null
}
```

Update trip
```
PUT http://localhost:7070/api/trips/4

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 10:05:33 GMT
Content-Type: application/json
Content-Length: 140

{
  "id": 4,
  "startTime": [
    9,
    0
  ],
  "endTime": [
    20,
    0
  ],
  "startPosition": "Tallinn",
  "name": "Trip to Tallinn",
  "price": 100,
  "category": "CITY",
  "guideDTO": null
}
```

Delete trip
```
DELETE http://localhost:7070/api/trips/1

HTTP/1.1 204 No Content
Date: Mon, 04 Nov 2024 10:05:46 GMT
Content-Type: text/plain

<Response body is empty>
```

Add guide to trip
```
PUT http://localhost:7070/api/trips/4/guides/1

HTTP/1.1 204 No Content
Date: Mon, 04 Nov 2024 10:05:59 GMT
Content-Type: text/plain

<Response body is empty>
```

Checking Trip 4 if guide was correctly added
```
GET http://localhost:7070/api/trips/4

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 10:06:33 GMT
Content-Type: application/json
Content-Length: 266

{
  "id": 4,
  "startTime": [
    9,
    0
  ],
  "endTime": [
    20,
    0
  ],
  "startPosition": "Tallinn",
  "name": "Trip to Tallinn",
  "price": 100,
  "category": "CITY",
  "guideDTO": {
    "id": 1,
    "firstName": "Alice",
    "lastName": "Johnson",
    "email": "alice.johnson@example.com",
    "phone": "123-456-7890",
    "yearsOfExperience": 5
  }
}
```

Trips By Category
```
GET http://localhost:7070/api/trips/category/CITY

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 10:06:49 GMT
Content-Type: application/json
Content-Length: 538

[
  {
    "id": 2,
    "startTime": [
      8,
      0
    ],
    "endTime": [
      15,
      0
    ],
    "startPosition": "New York City",
    "name": "NYC Urban Adventure",
    "price": 1200,
    "category": "CITY",
    "guideDTO": {
      "id": 2,
      "firstName": "Bob",
      "lastName": "Smith",
      "email": "bob.smith@example.com",
      "phone": "234-567-8901",
      "yearsOfExperience": 8
    }
  },
  {
    "id": 4,
    "startTime": [
      9,
      0
    ],
    "endTime": [
      20,
      0
    ],
    "startPosition": "Tallinn",
    "name": "Trip to Tallinn",
    "price": 100,
    "category": "CITY",
    "guideDTO": {
      "id": 1,
      "firstName": "Alice",
      "lastName": "Johnson",
      "email": "alice.johnson@example.com",
      "phone": "123-456-7890",
      "yearsOfExperience": 5
    }
  }
]
```


#### 3.3.5 Theoretical question: Why do we suggest a PUT method for adding a guide to a trip instead of a POST method? Write the answer in your README.md file.
Pust is indepotent which means we can send the same request multiple times without changing the result.

### Attach packing items to a trip 
```
GET http://localhost:7070/api/trips/1

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:10:39 GMT
Content-Type: application/json
Content-Encoding: gzip
Content-Length: 672

{
  "id": 1,
  "startTime": [
    10,
    0
  ],
  "endTime": [
    10,
    0
  ],
  "startPosition": "Los Angeles",
  "name": "California Beach Getaway",
  "price": 1500,
  "category": "BEACH",
  "guideDTO": {
    "id": 1,
    "firstName": "Alice",
    "lastName": "Johnson",
    "email": "alice.johnson@example.com",
    "phone": "123-456-7890",
    "yearsOfExperience": 5
  },
  "packingItems": [
    {
      "name": "Beach Umbrella",
      "weightInGrams": 1200,
      "quantity": 1,
      "description": "Sunshade umbrella for beach outings.",
      "category": "beach",
      "createdAt": 1730310298.547000000,
      "updatedAt": 1730310298.547000000,
      "buyingOptions": [
        {
          "shopName": "Sunny Store",
          "shopUrl": "https://shop3.com",
          "price": 50
        },
        {
          "shopName": "Beach Essentials",
          "shopUrl": "https://shop4.com",
          "price": 55
        }
      ]
    },
    {
      "name": "Beach Water Bottle",
      "weightInGrams": 500,
      "quantity": 1,
      "description": "High-capacity water bottle for hot climates.",
      "category": "beach",
      "createdAt": 1730310298.547000000,
      "updatedAt": 1730310298.547000000,
      "buyingOptions": [
        {
          "shopName": "Hydration Depot",
          "shopUrl": "https://shop6.com",
          "price": 25
        }
      ]
    },
    {
      "name": "Beach Cooler",
      "weightInGrams": 3000,
      "quantity": 1,
      "description": "Insulated cooler to keep beverages cold at the beach.",
      "category": "beach",
      "createdAt": 1730310298.547000000,
      "updatedAt": 1730310298.547000000,
      "buyingOptions": [
        {
          "shopName": "Beach Supplies",
          "shopUrl": "https://shop21.com",
          "price": 70
        }
      ]
    },
    {
      "name": "Beach Towel",
      "weightInGrams": 300,
      "quantity": 1,
      "description": "Large, quick-drying beach towel.",
      "category": "beach",
      "createdAt": 1730310298.547000000,
      "updatedAt": 1730310298.547000000,
      "buyingOptions": [
        {
          "shopName": "Beach Essentials",
          "shopUrl": "https://shop1.com",
          "price": 15
        }
      ]
    },
    {
      "name": "Beach Ball",
      "weightInGrams": 100,
      "quantity": 1,
      "description": "Inflatable beach ball for games.",
      "category": "beach",
      "createdAt": 1730310298.547000000,
      "updatedAt": 1730310298.547000000,
      "buyingOptions": [
        {
          "shopName": "Beach Fun Shop",
          "shopUrl": "https://shop2.com",
          "price": 5
        }
      ]
    },
    {
      "name": "Sunscreen SPF 50",
      "weightInGrams": 200,
      "quantity": 1,
      "description": "High-SPF sunscreen for beach days.",
      "category": "beach",
      "createdAt": 1730310298.547000000,
      "updatedAt": 1730310298.547000000,
      "buyingOptions": [
        {
          "shopName": "Sunny Shop",
          "shopUrl": "https://shop3.com",
          "price": 10
        }
      ]
    },
    {
      "name": "Beach Chair",
      "weightInGrams": 2000,
      "quantity": 1,
      "description": "Foldable, lightweight beach chair.",
      "category": "beach",
      "createdAt": 1730310298.547000000,
      "updatedAt": 1730310298.547000000,
      "buyingOptions": [
        {
          "shopName": "Beach Supplies",
          "shopUrl": "https://shop4.com",
          "price": 25
        }
      ]
    }
  ]
}
```

### Final Outputs from all endpoints (executed in this order): 

Populate
```
POST http://localhost:7070/api/trips/populate

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:52:04 GMT
Content-Type: application/json
Content-Length: 881

[
  {
    "id": 1,
    "startTime": [
      10,
      0
    ],
    "endTime": [
      10,
      0
    ],
    "startPosition": "Los Angeles",
    "name": "California Beach Getaway",
    "price": 1500,
    "category": "BEACH",
    "guideDTO": {
      "id": 1,
      "firstName": "Alice",
      "lastName": "Johnson",
      "email": "alice.johnson@example.com",
      "phone": "123-456-7890",
      "yearsOfExperience": 5
    },
    "packingItems": null
  },
  {
    "id": 2,
    "startTime": [
      8,
      0
    ],
    "endTime": [
      15,
      0
    ],
    "startPosition": "New York City",
    "name": "NYC Urban Adventure",
    "price": 1200,
    "category": "CITY",
    "guideDTO": {
      "id": 2,
      "firstName": "Bob",
      "lastName": "Smith",
      "email": "bob.smith@example.com",
      "phone": "234-567-8901",
      "yearsOfExperience": 8
    },
    "packingItems": null
  },
  {
    "id": 3,
    "startTime": [
      9,
      20
    ],
    "endTime": [
      20,
      20
    ],
    "startPosition": "Miami",
    "name": "Miami Beach Party",
    "price": 1800,
    "category": "BEACH",
    "guideDTO": {
      "id": 3,
      "firstName": "Carol",
      "lastName": "Davis",
      "email": "carol.davis@example.com",
      "phone": "345-678-9012",
      "yearsOfExperience": 3
    },
    "packingItems": null
  }
]
```

get all trips
```
GET http://localhost:7070/api/trips

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:52:16 GMT
Content-Type: application/json
Content-Length: 881

[
  {
    "id": 1,
    "startTime": [
      10,
      0
    ],
    "endTime": [
      10,
      0
    ],
    "startPosition": "Los Angeles",
    "name": "California Beach Getaway",
    "price": 1500,
    "category": "BEACH",
    "guideDTO": {
      "id": 1,
      "firstName": "Alice",
      "lastName": "Johnson",
      "email": "alice.johnson@example.com",
      "phone": "123-456-7890",
      "yearsOfExperience": 5
    },
    "packingItems": null
  },
  {
    "id": 2,
    "startTime": [
      8,
      0
    ],
    "endTime": [
      15,
      0
    ],
    "startPosition": "New York City",
    "name": "NYC Urban Adventure",
    "price": 1200,
    "category": "CITY",
    "guideDTO": {
      "id": 2,
      "firstName": "Bob",
      "lastName": "Smith",
      "email": "bob.smith@example.com",
      "phone": "234-567-8901",
      "yearsOfExperience": 8
    },
    "packingItems": null
  },
  {
    "id": 3,
    "startTime": [
      9,
      20
    ],
    "endTime": [
      20,
      20
    ],
    "startPosition": "Miami",
    "name": "Miami Beach Party",
    "price": 1800,
    "category": "BEACH",
    "guideDTO": {
      "id": 3,
      "firstName": "Carol",
      "lastName": "Davis",
      "email": "carol.davis@example.com",
      "phone": "345-678-9012",
      "yearsOfExperience": 3
    },
    "packingItems": null
  }
]
```

get trip by id
```
GET http://localhost:7070/api/trips/1

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:52:30 GMT
Content-Type: application/json
Content-Encoding: gzip
Content-Length: 672

{
  "id": 1,
  "startTime": [
    10,
    0
  ],
  "endTime": [
    10,
    0
  ],
  "startPosition": "Los Angeles",
  "name": "California Beach Getaway",
  "price": 1500,
  "category": "BEACH",
  "guideDTO": {
    "id": 1,
    "firstName": "Alice",
    "lastName": "Johnson",
    "email": "alice.johnson@example.com",
    "phone": "123-456-7890",
    "yearsOfExperience": 5
  },
  "packingItems": [
    {
      "name": "Beach Umbrella",
      "weightInGrams": 1200,
      "quantity": 1,
      "description": "Sunshade umbrella for beach outings.",
      "category": "beach",
      "createdAt": 1730310298.547000000,
      "updatedAt": 1730310298.547000000,
      "buyingOptions": [
        {
          "shopName": "Sunny Store",
          "shopUrl": "https://shop3.com",
          "price": 50
        },
        {
          "shopName": "Beach Essentials",
          "shopUrl": "https://shop4.com",
          "price": 55
        }
      ]
    },
    {
      "name": "Beach Water Bottle",
      "weightInGrams": 500,
      "quantity": 1,
      "description": "High-capacity water bottle for hot climates.",
      "category": "beach",
      "createdAt": 1730310298.547000000,
      "updatedAt": 1730310298.547000000,
      "buyingOptions": [
        {
          "shopName": "Hydration Depot",
          "shopUrl": "https://shop6.com",
          "price": 25
        }
      ]
    },
    {
      "name": "Beach Cooler",
      "weightInGrams": 3000,
      "quantity": 1,
      "description": "Insulated cooler to keep beverages cold at the beach.",
      "category": "beach",
      "createdAt": 1730310298.547000000,
      "updatedAt": 1730310298.547000000,
      "buyingOptions": [
        {
          "shopName": "Beach Supplies",
          "shopUrl": "https://shop21.com",
          "price": 70
        }
      ]
    },
    {
      "name": "Beach Towel",
      "weightInGrams": 300,
      "quantity": 1,
      "description": "Large, quick-drying beach towel.",
      "category": "beach",
      "createdAt": 1730310298.547000000,
      "updatedAt": 1730310298.547000000,
      "buyingOptions": [
        {
          "shopName": "Beach Essentials",
          "shopUrl": "https://shop1.com",
          "price": 15
        }
      ]
    },
    {
      "name": "Beach Ball",
      "weightInGrams": 100,
      "quantity": 1,
      "description": "Inflatable beach ball for games.",
      "category": "beach",
      "createdAt": 1730310298.547000000,
      "updatedAt": 1730310298.547000000,
      "buyingOptions": [
        {
          "shopName": "Beach Fun Shop",
          "shopUrl": "https://shop2.com",
          "price": 5
        }
      ]
    },
    {
      "name": "Sunscreen SPF 50",
      "weightInGrams": 200,
      "quantity": 1,
      "description": "High-SPF sunscreen for beach days.",
      "category": "beach",
      "createdAt": 1730310298.547000000,
      "updatedAt": 1730310298.547000000,
      "buyingOptions": [
        {
          "shopName": "Sunny Shop",
          "shopUrl": "https://shop3.com",
          "price": 10
        }
      ]
    },
    {
      "name": "Beach Chair",
      "weightInGrams": 2000,
      "quantity": 1,
      "description": "Foldable, lightweight beach chair.",
      "category": "beach",
      "createdAt": 1730310298.547000000,
      "updatedAt": 1730310298.547000000,
      "buyingOptions": [
        {
          "shopName": "Beach Supplies",
          "shopUrl": "https://shop4.com",
          "price": 25
        }
      ]
    }
  ]
}
```

Create new trip
```
POST http://localhost:7070/api/trips

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:52:42 GMT
Content-Type: application/json
Content-Length: 161

{
  "id": 4,
  "startTime": [
    10,
    0
  ],
  "endTime": [
    18,
    0
  ],
  "startPosition": "Tallinn",
  "name": "Trip to Tallinn",
  "price": 100,
  "category": "CITY",
  "guideDTO": null,
  "packingItems": null
}
```

Update trip
```
PUT http://localhost:7070/api/trips/4

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:52:54 GMT
Content-Type: application/json
Content-Length: 160

{
  "id": 4,
  "startTime": [
    9,
    0
  ],
  "endTime": [
    20,
    0
  ],
  "startPosition": "Tallinn",
  "name": "Trip to Tallinn",
  "price": 100,
  "category": "CITY",
  "guideDTO": null,
  "packingItems": null
}
```

Add guide to trip
```
PUT http://localhost:7070/api/trips/4/guides/1

HTTP/1.1 204 No Content
Date: Mon, 04 Nov 2024 11:54:14 GMT
Content-Type: text/plain

<Response body is empty>
```

Delete trip
```
DELETE http://localhost:7070/api/trips/4

HTTP/1.1 204 No Content
Date: Mon, 04 Nov 2024 11:53:17 GMT
Content-Type: text/plain

<Response body is empty>
```

Trips by category
```
GET http://localhost:7070/api/trips/category/CITY

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:54:31 GMT
Content-Type: application/json
Content-Length: 578

[
  {
    "id": 2,
    "startTime": [
      8,
      0
    ],
    "endTime": [
      15,
      0
    ],
    "startPosition": "New York City",
    "name": "NYC Urban Adventure",
    "price": 1200,
    "category": "CITY",
    "guideDTO": {
      "id": 2,
      "firstName": "Bob",
      "lastName": "Smith",
      "email": "bob.smith@example.com",
      "phone": "234-567-8901",
      "yearsOfExperience": 8
    },
    "packingItems": null
  },
  {
    "id": 4,
    "startTime": [
      9,
      0
    ],
    "endTime": [
      20,
      0
    ],
    "startPosition": "Tallinn",
    "name": "Trip to Tallinn",
    "price": 100,
    "category": "CITY",
    "guideDTO": {
      "id": 1,
      "firstName": "Alice",
      "lastName": "Johnson",
      "email": "alice.johnson@example.com",
      "phone": "123-456-7890",
      "yearsOfExperience": 5
    },
    "packingItems": null
  }
]
```

Trips by Guide total Price
```
GET http://localhost:7070/api/trips/guides/totalPrice

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:54:51 GMT
Content-Type: application/json
Content-Length: 97

[
  {
    "totalPrice": 1600,
    "guideId": 1
  },
  {
    "totalPrice": 1200,
    "guideId": 2
  },
  {
    "totalPrice": 1800,
    "guideId": 3
  }
]
```

Get Packing List from Category
```
GET http://localhost:7070/api/trips/category/packinglist/beach

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:55:14 GMT
Content-Type: application/json
Content-Encoding: gzip
Content-Length: 521

{
  "items": [
    {
      "name": "Beach Umbrella",
      "weightInGrams": 1200,
      "quantity": 1,
      "description": "Sunshade umbrella for beach outings.",
      "category": "beach",
      "createdAt": 1730310298.547000000,
      "updatedAt": 1730310298.547000000,
      "buyingOptions": [
        {
          "shopName": "Sunny Store",
          "shopUrl": "https://shop3.com",
          "price": 50
        },
        {
          "shopName": "Beach Essentials",
          "shopUrl": "https://shop4.com",
          "price": 55
        }
      ]
    },
    {
      "name": "Beach Water Bottle",
      "weightInGrams": 500,
      "quantity": 1,
      "description": "High-capacity water bottle for hot climates.",
      "category": "beach",
      "createdAt": 1730310298.547000000,
      "updatedAt": 1730310298.547000000,
      "buyingOptions": [
        {
          "shopName": "Hydration Depot",
          "shopUrl": "https://shop6.com",
          "price": 25
        }
      ]
    },
    {
      "name": "Beach Cooler",
      "weightInGrams": 3000,
      "quantity": 1,
      "description": "Insulated cooler to keep beverages cold at the beach.",
      "category": "beach",
      "createdAt": 1730310298.547000000,
      "updatedAt": 1730310298.547000000,
      "buyingOptions": [
        {
          "shopName": "Beach Supplies",
          "shopUrl": "https://shop21.com",
          "price": 70
        }
      ]
    },
    {
      "name": "Beach Towel",
      "weightInGrams": 300,
      "quantity": 1,
      "description": "Large, quick-drying beach towel.",
      "category": "beach",
      "createdAt": 1730310298.547000000,
      "updatedAt": 1730310298.547000000,
      "buyingOptions": [
        {
          "shopName": "Beach Essentials",
          "shopUrl": "https://shop1.com",
          "price": 15
        }
      ]
    },
    {
      "name": "Beach Ball",
      "weightInGrams": 100,
      "quantity": 1,
      "description": "Inflatable beach ball for games.",
      "category": "beach",
      "createdAt": 1730310298.547000000,
      "updatedAt": 1730310298.547000000,
      "buyingOptions": [
        {
          "shopName": "Beach Fun Shop",
          "shopUrl": "https://shop2.com",
          "price": 5
        }
      ]
    },
    {
      "name": "Sunscreen SPF 50",
      "weightInGrams": 200,
      "quantity": 1,
      "description": "High-SPF sunscreen for beach days.",
      "category": "beach",
      "createdAt": 1730310298.547000000,
      "updatedAt": 1730310298.547000000,
      "buyingOptions": [
        {
          "shopName": "Sunny Shop",
          "shopUrl": "https://shop3.com",
          "price": 10
        }
      ]
    },
    {
      "name": "Beach Chair",
      "weightInGrams": 2000,
      "quantity": 1,
      "description": "Foldable, lightweight beach chair.",
      "category": "beach",
      "createdAt": 1730310298.547000000,
      "updatedAt": 1730310298.547000000,
      "buyingOptions": [
        {
          "shopName": "Beach Supplies",
          "shopUrl": "https://shop4.com",
          "price": 25
        }
      ]
    }
  ]
}
```

Get total packing item weights from trip
```
GET http://localhost:7070/api/trips/1/packingweight

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:55:25 GMT
Content-Type: application/json
Content-Length: 20

{
  "totalWeight": 7300
}
```