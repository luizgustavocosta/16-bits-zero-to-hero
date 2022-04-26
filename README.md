# 16-bits-zero-to-hero

### Build Status
<hr>

[![Node.js CI](https://github.com/luizgustavocosta/16-bits-zero-to-hero/actions/workflows/node.js.yml/badge.svg)](https://github.com/luizgustavocosta/16-bits-zero-to-hero/actions/workflows/node.js.yml)

[![Java CI](https://github.com/luizgustavocosta/16-bits-zero-to-hero/actions/workflows/gradle.yml/badge.svg)](https://github.com/luizgustavocosta/16-bits-zero-to-hero/actions/workflows/gradle.yml)

### Project title
<hr>
🗒 Zero to hero 🚀 using React and SpringBoot

### Motivation
<hr>
Create a project with front-end and back-end to run easily locally or through the browser

### Solution
<hr>

![Architecture](documentation/images/Architectural_diagram.png)

### Database diagram
![database](documentation/images/ER_Diagram.png)

### Tech / Framework used

![fe](documentation/images/front-end-language-32.png)
- JavaScript
- React 17.0.2
- MUI 5
- Jest
- GitHub actions

![be](documentation/images/java-development-32.png)
- Java 11
- SpringBoot 2.6.3
- Spring
- Hibernate
- Swagger - OpenAPI 1.6.7
- MapStruct
- JUnit 5
- GitHub actions

### Back-end code style
<hr>

[Java Code Conventions](https://www.oracle.com/java/technologies/javase/codeconventions-contents.html)

<hr>

### Installation
<hr>

### How to use it?
<hr>

#### Locally
```
 cd back-end/zero2hero && ./gradlew bootRun
```
[Swagger documentation](http://localhost:8080/swagger-ui/index.html)

##### Disable security
#### Back-end

```
 cd back-end/zero2hero && ./gradlew bootRun
```

##### Endpoints
[Swagger documentation](http://localhost:8080/swagger-ui/index.html)


#### Front-end

```
cd front-end/ui-app && npm install && npm start
```

Open this [link](http://localhost:3000/) and use the Username and Password on the login screen

<kdb><img src="https://github.com/luizgustavocosta/16-bits-zero-to-hero/blob/main/documentation/images/login.png"/></kdb>

#### Gitpod

Steps

1. Clone the repo https://github.com/luizgustavocosta/16-bits-zero-to-hero
2. Open the [Gitpod](https://www.gitpod.io/), be sure you're logged in
3. Add the project hitting the button New Workspace
4. Open the terminal for the back-end
5. type ```cd back-end/zero2hero && ./gradlew bootRun```
6. Make the URL public
7. Back-end up and running ![Back-end](documentation/images/Gitpod-backend.png) 
8. Open the file ``application.json`` inside the UI project and update the value for ``SERVER_URL``. The server URL should be the back-end URL.
9. Type the command ``cd front-end/ui-app && npm install && npm start``
10. Voilá app up and running ![Front-end](documentation/images/React-frontend.png)

### References

- https://www.veryicon.com/
- https://spring-petclinic.github.io/
- https://github.com/in28minutes/spring-boot-react-fullstack-examples
- https://github.com/joelparkerhenderson/architecture-decision-record

### License
<hr>

+ [MIT](https://choosealicense.com/licenses/mit/)