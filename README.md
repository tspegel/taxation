# Taxation

## Task 1: Taxation Calculator

### Description
Taxation is a term for when a taxing authority (usually a government) imposes a tax. Imagine having multiple web sites in many countries (let's call these sites traders) out of which each must follow different local taxation rules and calculations. Site users bets an amount on the give odd, e.g. 5 EUR * 1.5 = 7.5 EUR. Everything will be done in EUR currency to keep it simple.

### Test data
##### Traders

| id  | method | name | rate | trader_id | type | winning_amount |
| ----|--------|----- | -----|---------- | ---- | -------------- |
| 1 | rate | phoenix | 10% | 1 | general | null |
| 2 | amount | dementor | 2 | 2 | general | null |
| 3 | rate | goblet | 10% | 3 | winnings | 5 |
| 4 | amount | snitch | 1 | 4 | winnings | 5 |


### Prerequisites

+	Maven installed
+	Download postgresql (https://www.postgresql.org/download/)
+	Install postgresql and create database whit following characteristics:
    +	Hostename: localhost
    +	Port: 5432
    +	Username: postgres
    +	Password: postgres
    +	Name: taxation_calculator

### Available actions

+	Calculate taxation for specific bet. 

### Staring application

+	cd [project-path]/taxation-calculator
+   mvn clean install
+	mvn spring-boot:run

or

+ cd [project-path]/scripts
+ ./run.sh

### Testing available actions with Postman

#### Played Amount: 5
#### Odd: 1.5

##### Type: General, Method: Rate 
```POST http://localhost:8080/taxation/taxation-calculator?traderId=1&playedAmount=5&odd=1.5```
##### Type: General, Method: Amount 
```POST http://localhost:8080/taxation/taxation-calculator?traderId=2&playedAmount=5&odd=1.5```
##### Type: Winning, Method: Rate 
```POST http://localhost:8080/taxation/taxation-calculator?traderId=3&playedAmount=5&odd=1.5```
##### Type: Winning, Method: Amount 
```POST http://localhost:8080/taxation/taxation-calculator?traderId=4&playedAmount=5&odd=1.5```

### Footnote
+ Debug port is open on 8787
+ Variable names, Class names and comments are named/written badly because I don't know betting lingo. 

## Task 3: Questions

##### Q1: Can you describe your latest performance solving issue? What was the problem? How did you fix and how much time did you spent solving it?

We had a problem when we were fetching users. We were only able to fetch all users from specific organisation and then we had to loop through all users to get wanted one. I added field to user that enabled us to perform search that returned single user. 

##### Q2: How do you manage conflicts in web applications when different user are managing and working on data concurrently?

If I could assumes that multiple transactions can frequently complete without interfering with each other I would use Optimistic concurrency control (https://en.wikipedia.org/wiki/Optimistic_concurrency_control). For general conflict menagment I would use "Semaphore" (https://en.wikipedia.org/wiki/Semaphore_(programming)). 
But in practice this differs from case to case and I would choose algorithm best suited for specific type of usecase.

##### Q3: Can you describe your idea of design (architecture) on how to make a big scale e-commerce website with at least 100K concurrent users.
I would use IDP, something like https://www.keycloak.org/ to handle users (registration, login,...). 
I would separate frontend and backend (each on its own servers). For frontend and backend I would implement load balancer and that would enable me to add servers as the website would grow. I don't have much experience in this field. Design I described is the only one I ever encountered and was used on project that is expected to have up to 3mil users so I think it should work fine. 
