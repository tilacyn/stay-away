### requirements

jdk 11, maven

### mongodb configuration

#### Common mongodb installation and configuration

##### <a href="https://www.mongodb.com/docs/manual/tutorial/install-mongodb-on-os-x/">Mac OS</a>

###### Install mongodb

`
brew tap mongodb/brew
brew update
brew install mongodb-community@6.0
`
###### Mongodb start service

`
brew services start mongodb-community@6.0
`

#### Configuration for project

###### Inside terminal

`mongosh`

###### Inside mongosh

`
use stayaway
db.createUser({ user: "admin", pwd: "admin", roles: [ "dbOwner" ] })
`

### build

`mvn clean install`

### build fast (skipping tests and installation)

`mvn package -DskipTests`

### run

`java -jar target/stay-away-0.0.1-SNAPSHOT.jar`
