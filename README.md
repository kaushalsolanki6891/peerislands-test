# peerislands-test
peerislands-search-test

Assuming that final requirement will be provide dynamic search functionality.

swagger :  http://localhost:8080/swagger-ui.html
h2 console : http://localhost:8080/h2

#sample payload

1.Get Employee with age = 31

{
  "searchQueries": [
    {
      "parentOperator": "",
      "searchFilter": [
        {
          "operator": "EQUALS",
          "fieldName": "age",
          "fieldValue": "31"
        }
      ]
    }
  ],
  "sortOrder": {
    "ascendingOrder": [
      "firstName"
    ],
    "descendingOrder": [
      "lastName"
    ]
  }
}

OUTPUT QUERY 

select 
  employee0_.id as id1_0_, 
  employee0_.age as age2_0_, 
  employee0_.email as email3_0_, 
  employee0_.first_name as first_na4_0_, 
  employee0_.last_name as last_nam5_0_ 
from 
  tbl_employees employee0_ 
where 
  employee0_.age = 31 
order by 
  employee0_.first_name asc, 
  employee0_.last_name desc


2.Get Employess age between 30-45

{
  "searchQueries": [
    {
      "parentOperator": "",
      "searchFilter": [
        {
          "operator": "BETWEEN",
          "fieldName": "age",
          "fieldValue": "30,45"
        }
      ]
    }
  ],
  "sortOrder": {
    "ascendingOrder": [
      "firstName"
    ],
    "descendingOrder": [
      "lastName"
    ]
  }
}

OUTPUT QUERY 

select 
  employee0_.id as id1_0_, 
  employee0_.age as age2_0_, 
  employee0_.email as email3_0_, 
  employee0_.first_name as first_na4_0_, 
  employee0_.last_name as last_nam5_0_ 
from 
  tbl_employees employee0_ 
where 
  employee0_.age between 30 
  and 45 
order by 
  employee0_.first_name asc, 
  employee0_.last_name desc


3.Get Employess age between 35-45 and email like peerislands.io

{
  "searchQueries": [
    {
      "parentOperator": "",
      "searchFilter": [
        {
          "operator": "BETWEEN",
          "fieldName": "age",
          "fieldValue": "35,45"
        },
        {
          "operator": "LIKE",
          "fieldName": "email",
          "fieldValue": "peerislands.io"
        }
      ]
    }
  ],
  "sortOrder": {
    "ascendingOrder": [
      "firstName"
    ],
    "descendingOrder": [
      "lastName"
    ]
  }
}

OUTPUT QUERY 

select 
  employee0_.id as id1_0_, 
  employee0_.age as age2_0_, 
  employee0_.email as email3_0_, 
  employee0_.first_name as first_na4_0_, 
  employee0_.last_name as last_nam5_0_ 
from 
  tbl_employees employee0_ 
where 
  (
    employee0_.age between 35 
    and 45
  ) 
  and (employee0_.email like ?) 
order by 
  employee0_.first_name asc, 
  employee0_.last_name desc


4.Get Employess age > 35 and email like peerislands.io

{
  "searchQueries": [
    {
      "parentOperator": "",
      "childOperator": "AND",
      "searchFilter": [
        {
          "operator": "GRATER_THAN",
          "fieldName": "age",
          "fieldValue": "35"
        },
        {
          "operator": "LIKE",
          "fieldName": "email",
          "fieldValue": "peerislands.io"
        }
      ]
    }
  ],
  "sortOrder": {
    "ascendingOrder": [
      "firstName"
    ],
    "descendingOrder": [
      "lastName"
    ]
  }
}

OUTPUT QUERY 

select 
  employee0_.id as id1_0_, 
  employee0_.age as age2_0_, 
  employee0_.email as email3_0_, 
  employee0_.first_name as first_na4_0_, 
  employee0_.last_name as last_nam5_0_ 
from 
  tbl_employees employee0_ 
where 
  employee0_.age > 35 
  and (employee0_.email like ?) 
order by 
  employee0_.first_name asc, 
  employee0_.last_name desc


6.Get Employess age > 35 OR email like peerislands.io

{
  "searchQueries": [
    {
      "parentOperator": "",
      "childOperator": "OR",
      "searchFilter": [
        {
          "operator": "GRATER_THAN",
          "fieldName": "age",
          "fieldValue": "35"
        },
        {
          "operator": "LIKE",
          "fieldName": "email",
          "fieldValue": "peerislands.io"
        }
      ]
    }
  ],
  "sortOrder": {
    "ascendingOrder": [
      "firstName"
    ],
    "descendingOrder": [
      "lastName"
    ]
  }
}

OUTPUT QUERY 

select 
  employee0_.id as id1_0_, 
  employee0_.age as age2_0_, 
  employee0_.email as email3_0_, 
  employee0_.first_name as first_na4_0_, 
  employee0_.last_name as last_nam5_0_ 
from 
  tbl_employees employee0_ 
where 
  employee0_.age > 35 
  or employee0_.email like ? 
order by 
  employee0_.first_name asc, 
  employee0_.last_name desc

7. Get Employess age >=35 AND email like peerislands.io AND hobby = Football

{
  "searchQueries": [
    {
      "parentOperator": "",
      "childOperator": "AND",
      "searchFilter": [
        {
          "operator": "GRATER_THAN_EQ",
          "fieldName": "age",
          "fieldValue": "35"
        },
        {
          "operator": "LIKE",
          "fieldName": "email",
          "fieldValue": "peerislands.io"
        }
      ],
     "joinColumnProps": [
        {
          "joinColumnName": "hobbies",
          "searchFilter": {
            "operator": "EQUALS",
            "fieldName": "hobby",
            "fieldValue": "Football"
          }
        }
      ]
    }
  ],      
  "sortOrder": {
    "ascendingOrder": [
      "firstName"
    ],
    "descendingOrder": [
      "lastName"
    ]
  }
}

OUTPUT QUERY 

select 
  employee0_.id as id1_0_, 
  employee0_.age as age2_0_, 
  employee0_.email as email3_0_, 
  employee0_.first_name as first_na4_0_, 
  employee0_.last_name as last_nam5_0_ 
from 
  tbl_employees employee0_ 
  inner join tbl_hobbies hobbies1_ on employee0_.id = hobbies1_.employee_id 
where 
  hobbies1_.hobby = ? 
  and employee0_.age >= 35 
  and (employee0_.email like ?) 
order by 
  employee0_.first_name asc, 
  employee0_.last_name desc
