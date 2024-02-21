# Alea

Alea Knowledge Test.  
Using PokéAPI (https://pokeapi.co/api/v2/), expose an API with following scenarios

### Main Requirements

1. Retrieve the 5 heaviest Pokémon.
2. Retrieve the 5 tallest Pokémon.
3. Retrieve the 5 Pokémon with the highest base experience.

### Must to have

- Create a Java/SpringBoot application
- Test coverage of at least 90%

### Nice to have

- Integration test
- Production ready 

# Setup

To run this application, you need to have Docker installed and running to create images and containers.

# Run

With Docker running, open a console and execute the following command:  

    docker compose up   

# Endpoints

Once you have the application running, you have this three endpoints to test the app:

    http://localhost:8080/pokemon/heaviest
    http://localhost:8080/pokemon/highest
    http://localhost:8080/pokemon/experience

# Features

 - This application is built with Java 21 and SpringBoot 3.2.2.  
 - It uses NoSQL MongoDB database to store data
 - It uses project Reactor to take advantage of working with Streams and reactive programing.
 - In addition to basic libraries, the project uses Lombok and MapStruct libraries to reduce boilerplate code.
 - The code is structured in a Hexagonal architecture for the separation concerns and facilitate System evolution and Maintenance

# How it works
We need MongoDB to be running before the application starts.  
Once Spring has initialized the context, the application listens for the ApplicationReadyEvent and begins fetching data from the public PokeAPI to MongoDB in the background.    
If a request is made to the API while the data is being fetched, the request will wait until all the data is fetched.  
Once the data has been fetched, the endpoints retrieve the data from our MongoDB to serve the request quickly.  
In any API call, the application compare the num of records in our database and in the PokeAPI. If there are new Pokémon or if we are missing some of them, the application initiates the process to fetch the missing ones to retrieve the most updated data.