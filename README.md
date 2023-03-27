[![Linter checks](https://github.com/EdwardNee/CodeCommenterREST/actions/workflows/linter.yml/badge.svg)](https://github.com/EdwardNee/CodeCommenterREST/actions/workflows/linter.yml) 
[![Linter checks](https://github.com/EdwardNee/CodeCommenterREST/actions/workflows/ci_tests.yml/badge.svg)](https://github.com/EdwardNee/CodeCommenterREST/actions/workflows/ci_tests.yml/badge.svg) 
[![codecov](https://codecov.io/gh/EdwardNee/CodeCommenterREST/branch/ep_testing/graph/badge.svg?token=YhGoz4BNHY)](https://codecov.io/gh/EdwardNee/CodeCommenterREST)
# CodeCommenterREST  
## What will you build  
You will build a simple web application with Spring Boot and Java 17, which has a working endpoint for generating comments for a method.

## Technology stack  
* Java 17
* Apache Maven 3.8.
* Python
* Spring Boot
* Docker for running tests on GitHub Actions 

## How to start using
1. Clone the repository `https://github.com/EdwardNee/CodeCommenterREST.git`
2. Run `pip install -r script/requirements.txt`. Check the file for more information.
3. Build the project with `mvn clean install`
4. Start the app: `java -jar target/commenter-0.0.1-SNAPSHOT.jar`

The app will be available at `http://localhost:8080`.

## Server endpoints
- `/`: Shows the homepage. Suggests to redirect to `/api/v1`.
- `POST` `/generate_comment`: Generates comment for a provided method.  
Takes a json string `{ "code": "public static void Main() {}", "firstPart": "null" }`
- `POST` `/complete_comment`: Not implemented.
- `POST` `/save_to_dataset`: Not implemented.

Also, you can visit `http://localhost:8080/swagger-ui/index.html` to check available endpoints.

## Generating comments
Comments generate using serialized with [`pickle`](https://docs.python.org/3/library/pickle.html) pretrained [`T5`](https://huggingface.co/t5-base) huggingface transformer. The model was trained on [`CodeXGLUE`](https://github.com/microsoft/CodeXGLUE/tree/main/Code-Text/code-to-text) code2text dataset as well. Training steps shown in [CodeCommenter-NLP4Code](https://github.com/EdwardNee/CodeCommenter-NLP4Code).

## Issues

If you encounter any issues while using CodeCommenter Spring boot app, please open a new issue on our GitHub repository.

