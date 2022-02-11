Simulating the behavior of a robotic hoover
======================

## Introduction
Web-service navigates a imaginary robotic hoover (much like a Roomba) through an equally imaginary room based on:

- room dimensions as X and Y coordinates, identifying the top right corner of the room rectangle. This room is divided up in a grid based on these dimensions; a room that has dimensions X: 5 and Y: 5 has 5 columns and 5 rows, so 25 possible hoover positions. The bottom left corner is the point of origin for our coordinate system, so as the room contains all coordinates its bottom left corner is defined by X: 0 and Y: 0.
- locations of patches of dirt, also defined by X and Y coordinates identifying the bottom left corner of those grid positions.
- an initial hoover position (X and Y coordinates like patches of dirt)
- driving instructions (as cardinal directions where e.g. N and E mean "go north" and "go east" respectively)

The room is rectangular, has no obstacles (except the room walls), no doors and all locations in the room are clean (hoovering has no effect) except for the locations of the patches of dirt presented in the program input.

Placing the hoover on a patch of dirt ("hoovering") removes the patch of dirt so that patch is then clean for the remainder of the program run. The hoover is always on - there is no need to enable it.

Driving into a wall has no effect (the robot skids in place).

## Goal
The goal of the service is to take the room dimensions, the locations of the dirt patches, the hoover location and the driving instructions as input and to then output the following:

- The final hoover position (X, Y)
- The number of patches of dirt the robot cleaned up

The service persists every input and output to a database.

## Input
Program input is in a json payload.

Example:

```yml
{
    "roomSize": [5, 5],
    "coords" : [1, 2],
    "patches" : [
        [1, 0],
        [2, 2],
        [2, 3]
    ],
    "instructions" : "NNESEESWNWW"
}
```

## Output
Service output returns a json payload.

Example (matching the input above):

```yml
{
    "coords" : [1, 3],
    "patches" : 1
}
```

## Launching
Service can be launched in several ways.
### Docker
For local running with postgres database as image use `docker-compose -f docker-compose-local.yaml up`.
For running with remote or local persistance database specify configuration in `.env` file and call `docker-compose up`.

### Maven
For local running remote or local persistance database specify configuration in `application-local.yml` file and call `mvn spring-boot:run -Dspring-boot.run.profiles=local`.
It is not recommended modifying `application.yml` because it is involved in both configurations: docker and mvn.

## Configuration
By default, service uses constraints for request values:
- MAX hoover route length (instructions): 1000
- MAX room size: 1000 x 1000
- MAX patch count: 1000

It is possible to change them in `application.yml` files.

## Request execution
Service provides 2 endpoints of API for making HTTP requests:

- POST /v1/cleanings, starts cleaning and returns result (as in the example in the section `Input`)
- GET /v1/cleanings, returns history of cleanings with paging. It is possible to specify data range using query params `size`, `page`. For example `GET /v1/cleanings?size=10&page=1`. By default, page = 0, size = 10.  

In case of a request validation error the service returns 400 HTTP status code with body like this:

```yml
{
    "description": "Request validation error",
        "timestamp": "2022-02-11 07:59:09",
        "errorDetails": [
        {
            "property": "roomSize",
            "message": "array length must be 2"
        }
    ]
}
```

## Logging
The service supports logging, by default to STDOUT. To configure this use `logback.xml` file.

## TODO

- Deep separating of models (hoover, room, patch)
- Swagger
- Missing unit-test
