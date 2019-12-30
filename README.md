# poja-doc

> !!!! Currently a WIP !!!!

> Existing frameworks such as [Swagger](https://swagger.io/) and [APIDOC](https://apidocjs.com/) provide much more features and overall a lot more sophisticated. This project aims to simplify API documentation to meet specific and opinionated goals and use cases. 

# Overview

Plain Old Java API (POJA) Documentation is a simplified (and opinionated) approach to documenting Java-based APIs. The primary goal is to provide a "vanilla Java" library and Gradle Plugin for developers to document their APIs outside of Javadoc without any overhead of additional COTs dependencies.

It provides a set of annotations similar to [Swagger](https://swagger.io/) but only adopts relevant attributes for intended use cases. The core logic uses reflection to detect these annotations from post-compiled classes and generate output file(s). Other processing can be extended via the `PojaDocumentBuilder` interface specifications and can be configured by the Gradle extension.

```groovy
// build.gradle

poja {
    // or your own custom PojaDocumentBuilder implementation
    builderType = 'poja.core.DefaultDocumentBuilder'
}
```  

## Documenting Root Endpoint

```java
@RestController
@Api(name = "poja", value = "/poja", description = "POJA Annotation Test Endpoint")
public class PojaEndpoint {}
```

## Documenting Operations

```java
@ApiOperation(path = "/info", methods = { "GET" }, produces = { "test/plain" } )
@RequestMapping(value = "/info", method = { RequestMethod.GET }, produces = { "test/plain" })
public String info() {}

@ApiOperation(path = "/submit", methods = { "POST" } )
@RequestMapping(value = "/submit", method = { RequestMethod.POST })
public void submit() {}
```

# Quick Start

```bash
# puts poja-core and poja-gradle-plugin to ~/.m2
gradle publishToMavenLocal

# builds sample project that uses the Gradle plugin from ~/.m2
gradle -b api/build.gradle :clean :pojadoc

# outputs the sample API
cat api/build/pojadoc/poja.json
```

# Sample Ouptput

```json
{
    "value": "/poja",
    "description": "POJA Annotation Test Endpoint",
    "operations": [
        {
            "consumes": null,
            "produces": null,
            "description": "",
            "methods": [
                "POST"
            ],
            "path": "/submit"
        },
        {
            "consumes": null,
            "produces": [
                "test/plain"
            ],
            "description": "",
            "methods": [
                "GET"
            ],
            "path": "/info"
        }
    ],
    "name": "poja"
}
```