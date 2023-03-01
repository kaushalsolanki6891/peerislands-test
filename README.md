# peerislands-test
peerislands-search-test

#sample payload
Payload 1 : Equal operator
{
  "pageNumber": 0,
  "pageSize": 10,
  "searchFitler": [
  ],
  "sortOrder": {
    "ascendingOrder": [
      "firstName"
    ],
    "descendingOrder": [
      "lastName"
    ]
  },
  "joinColumnProps" : [
  {
    "joinColumnName" : "hobbies",
    "searchFilter" : {
      "operator": "=",
      "fieldName": "hobby",
      "fieldValue": "Football"
    }
   }
  ]
}

Payload 2 : IN Operator 
{
  "pageNumber": 0,
  "pageSize": 10,
  "searchFitler": [
  ],
  "sortOrder": {
    "ascendingOrder": [
      "firstName"
    ],
    "descendingOrder": [
      "lastName"
    ]
  },
  "joinColumnProps" : [
  {
    "joinColumnName" : "hobbies",
    "searchFilter" : {
      "operator": "IN",
      "fieldName": "hobby",
      "fieldValue": ["Football", "Blogging"]
    }
   }
  ]
}

Payload 3 :  age = 32 and hobby is Chess.

{
  "pageNumber": 0,
  "pageSize": 10,
  "searchFitler": [
    {
      "operator": "=",
      "fieldName": "age",
      "fieldValue": 31
    }
  ],
  "sortOrder": {
    "ascendingOrder": [
      "firstName"
    ],
    "descendingOrder": [
      "lastName"
    ]
  },
  "joinColumnProps" : [
  {
    "joinColumnName" : "hobbies",
    "searchFilter" : {
      "operator": "=",
      "fieldName": "hobby",
      "fieldValue": "Chess"
    }
   }
  ]
}
