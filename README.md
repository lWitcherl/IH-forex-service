# IH-forex-service
is a microservice for getting information about exchange rates, written within the InfoHub project to practice writing projects with microservice architecture.
End points for communication: receiving real currencies: /currency/all GET

response:
  [
    {
        "code": "USD",
        "name": "United States dollar"
    },
    {
        "code": "EUR",
        "name": "Euro"
    },
    {
        "code": "UAH",
        "name": "Ukrainian hryvnia"
    }
  ]
Obtaining explicit currency codes: /currency/codes GET

response:
  [
  "EUR",
  "UAH",
  "USD"
  ]
Getting up-to-date exchange rates: /exchangerate/latest?from=EUR&to=UAH&to=USD GET

params: 
  from - target currency, By default  EUR
  to - final currency multiple choice is possible, By default  UAH
  
  response:
    [
      {
          "from": "EUR",
          "to": "UAH",
          "rate": 36.160765,
          "serviceName": "APILayer",
          "date": "2022-10-07"
      },
      {
          "from": "EUR",
          "to": "USD",
          "rate": 0.978855,
          "serviceName": "Currencyapi",
          "date": "2022-10-07"
      }
   ]
Getting exchange rates according to the criteria: /exchangerate/param?from=EUR&to=UAH&to=USD&startdate=2022-10-03 GET

  params: 
    from - target currency
    to - final currency multiple choice is possible
    startdate - the date from which the selection starts, (example 2022-10-03)
    endtdate - the date from which the selection finish, (example 2022-10-03)
    service - provider service name 
    morerate - the price from which the selection will start
    lessrate - the price at which it ends
    
  response:
    [
      {
          "from": "EUR",
          "to": "UAH",
          "rate": 36.160765,
          "serviceName": "APILayer",
          "date": "2022-10-07"
      },
      {
          "from": "EUR",
          "to": "USD",
          "rate": 0.979648,
          "serviceName": "APILayer",
          "date": "2022-10-07"
      },
      {
          "from": "EUR",
          "to": "UAH",
          "rate": 36.188038,
          "serviceName": "Currencyapi",
          "date": "2022-10-07"
      },
      {
          "from": "EUR",
          "to": "USD",
          "rate": 0.978855,
          "serviceName": "Currencyapi",
          "date": "2022-10-07"
      }
    ]
