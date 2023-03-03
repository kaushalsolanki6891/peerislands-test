# peerislands-test
peerislands-search-test

Assuming that final requirement will be provide dynamic search functionality.

swagger :  http://localhost:8080/swagger-ui.html
h2 console : http://localhost:8080/h2

#sample payload

########################################################
Payload 1 : Test Equal operator
########################################################
curl -X 'POST' \
  'http://localhost:8080/employees/search/query' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
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
}'


OUTPUT Query : 

select employee0_.id as id1_0_, employee0_.age as age2_0_, employee0_.email as email3_0_, employee0_.first_name as first_na4_0_, employee0_.last_name as last_nam5_0_ from tbl_employees employee0_ inner join tbl_hobbies hobbies1_ on employee0_.id=hobbies1_.employee_id 
where hobbies1_.hobby=? order by employee0_.first_name asc, employee0_.last_name desc


########################################################
Payload 2 : IN Operator 
########################################################
curl -X 'POST' \
  'http://localhost:8080/employees/search/query' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
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
}'

OUTPUT Query : 

select employee0_.id as id1_0_, employee0_.age as age2_0_, employee0_.email as email3_0_, employee0_.first_name as first_na4_0_, employee0_.last_name as last_nam5_0_ from tbl_employees employee0_ inner join tbl_hobbies hobbies1_ on employee0_.id=hobbies1_.employee_id 
where hobbies1_.hobby in (?) order by employee0_.first_name asc, employee0_.last_name desc

########################################################
Payload 3 :  age = 31 and hobby is Chess.
########################################################

curl -X 'POST' \
  'http://localhost:8080/employees/search/query' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "searchFilter": [
    {
      "operator": "=",
      "fieldName": "age",
      "fieldValue": 31
    }
  ],
  "pageNumber": 0,
  "pageSize": 10,
  "sortOrder": {
    "ascendingOrder": [
      "firstName"
    ],
    "descendingOrder": [
      "lastName"
    ]
  },
  "joinColumnProps": [
    {
      "joinColumnName": "hobbies",
      "searchFilter": {
        "operator": "=",
        "fieldName": "hobby",
        "fieldValue": "Chess"
      }
    }
  ]
}'


OUTPUT Query : 

select employee0_.id as id1_0_, employee0_.age as age2_0_, employee0_.email as email3_0_, employee0_.first_name as first_na4_0_, employee0_.last_name as last_nam5_0_ from tbl_employees employee0_ inner join tbl_hobbies hobbies1_ on employee0_.id=hobbies1_.employee_id 
where hobbies1_.hobby=? and employee0_.age=31 order by employee0_.first_name asc, employee0_.last_name desc
########################################################
