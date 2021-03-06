# poja-doc

> Existing frameworks such as [Swagger](https://swagger.io/) and [APIDOC](https://apidocjs.com/) provide much more features and overall a lot more sophisticated. This project aims to simplify API documentation to meet specific and opinionated goals and use cases - mainly for internal APIs. 

# Overview

Plain Old Java API (POJA) Documentation is a simplified (and opinionated) approach to documenting Java-based APIs. The primary goal is to provide a "vanilla Java" library and Gradle Plugin for developers to document their APIs outside of Javadoc without any overhead of additional COTs dependencies.

It provides a set of annotations similar to [Swagger](http://docs.swagger.io/swagger-core/v1.5.0/apidocs/io/swagger/annotations/package-summary.html) but only adopts relevant attributes for intended use cases. The core logic uses reflection to detect these annotations from compiled classes and generate output API doumentations in json format. Other formats and documentation logic can be extended via the `PojaDocumentation` interface specifications and can be configured via the Gradle `pojadoc` task.

```groovy
// build.gradle

buildscript {
    repositories {
        mavenLocal()
    }

    dependencies {
        classpath 'poja:poja-gradle-plugin:0.2'
    }
}

apply plugin: 'java-library'
apply plugin: 'pojadoc'

pojadoc {
    // default if not set; otherwise, your own custom PojaDocumentation implementation
    documentationType = 'poja.core.DefaultDocumentation'
    
    // default potential implementations to look for, if not set
    potentialImplementations = ['javax.ws.rs', 'org.springframework.web.bind.annotation']
    
    // default output pojadoc output directory, if not set
    docOutputDir = "${project.buildDir}/pojadoc"
    
    // default if not set; any class that cannot be processed through reflection due to dependencies issues will be ignored
    ignoreNoClassDefFoundError = true
}
```

Documenting API implementations involves two annotations `@Api` and `@ApiOperation`. Unlike Swagger, documentation details are all captured within each annotation and not outside, i.e. within implementation methods, method parameters, method body, etc.

## Documenting Root Endpoint

Annotation `@Api`
```
1. name: API name/identifier
2. description: Short description
3. value: The API root path value
3. url: Additional details can be externally linked (i.e. to javadoc, confluence, etc)
```

Example
```java
@RestController
@Api(name = "poja", value = "/poja", description = "POJA Annotation Test Endpoint")
public class PojaEndpoint {}
```

## Documenting Operations

Annotation `@ApiOperation`
```
1. path: Operation full path
2. description: Short description
3. produces: String[] Allowable formats this operation supports
4. methods: String[] HTTP methods that this operation supports
5. url: Additional details can be externally linked (i.e. to javadoc, confluence, etc)
6. parameters: ApiParam[] Parameters into this operation includes url parameters, request body, header attributes
```

Example
```java
@ApiOperation(path = "/info", methods = { "GET" }, produces = { "test/plain" } )
@RequestMapping(value = "/info", method = { RequestMethod.GET }, produces = { "test/plain" })
public String info() {}
```

## Documenting Parameters

Annotation `@ApiParam`, parameters to an API operation can be located in the URL path, the request body, or in header attributes.
```
1. name: Parameter name
2. description: Short description
3. location: url, body, header attribute
4. type: Paramter class type, default to String
5. url: Additional details can be externally linked (i.e. to javadoc, confluence, etc)
6. required: Required for operation
```

Example
```java
@ApiOperation(path = "/submit", methods = { "POST" }, url = "http://javadoc/poja-sumbit.html", parameters = {
   @ApiParam(name = "model", location = "body", type = PojaModel.class, url  = "http://javadoc/model/PojaModel.html")
})
@RequestMapping(value = "/submit", method = { RequestMethod.POST })
public void submit(PojaModel model) {}
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

# Output

There will be 3 different types of outputs if using the default `documentationType` -

1. Each API root will contain its own output files
2. `undocumented.json` contains the potential API implementations that contain no documentation
3. `uncategorized.json` contains operations that are not tied to a root API

Sample API Endpoint
```json
{
    "value": "/poja",
    "type": "",
    "description": "POJA Annotation Test Endpoint",
    "url": "",
    "operations": [
        {
            "parameters": [
                {
                    "required": true,
                    "type": "poja.test.api.PojaModel",
                    "url": "http://javadoc/model/PojaModel.html",
                    "location": "body",
                    "name": "model"
                }
            ],
            "consumes": null,
            "produces": null,
            "description": "",
            "methods": [
                "POST"
            ],
            "url": "http://javadoc/poja-sumbit.html",
            "path": "/submit"
        },
        {
            "parameters": [
                
            ],
            "consumes": null,
            "produces": [
                "test/plain"
            ],
            "description": "",
            "methods": [
                "GET"
            ],
            "url": "",
            "path": "/info"
        }
    ],
    "name": "poja"
}
```

Sample undocumented APIs
```json
[
    {
        "value": "undocumented",
        "type": "poja.test.api.PotentialJaxwsOperation",
        "description": "Undocumented API",
        "url": "",
        "operations": [
            
        ],
        "name": "undocumented"
    },
    {
        "value": "undocumented",
        "type": "poja.test.api.PotentialRestControllerOperation",
        "description": "Undocumented API",
        "url": "",
        "operations": [
            
        ],
        "name": "undocumented"
    },
    {
        "value": "undocumented",
        "type": "poja.test.api.PotentialJaxwsEndpoint",
        "description": "Undocumented API",
        "url": "",
        "operations": [
            
        ],
        "name": "undocumented"
    },
    {
        "value": "undocumented",
        "type": "poja.test.api.PotentialRestControllerEndpoint",
        "description": "Undocumented API",
        "url": "",
        "operations": [
            
        ],
        "name": "undocumented"
    }
]
```

Sample uncategorized operations
```json
[
    {
        "value": "uncategorized",
        "type": "",
        "description": "Uncategorized API: poja.test.api.UncategorizedPersonEndpoint",
        "url": "",
        "operations": [
            [
                {
                    "parameters": [
                        {
                            "required": true,
                            "type": "java.lang.String",
                            "url": "",
                            "location": "url",
                            "name": "userid"
                        }
                    ],
                    "consumes": null,
                    "produces": null,
                    "description": "",
                    "methods": [
                        "POST"
                    ],
                    "url": "http://javadoc/poja-sumbit.html",
                    "path": "/submit/{person}"
                }
            ]
        ],
        "name": "uncategorized"
    },
    {
        "value": "uncategorized",
        "type": "",
        "description": "Uncategorized API: poja.test.api.UncategorizedEndpoint",
        "url": "",
        "operations": [
            [
                {
                    "parameters": [
                        {
                            "required": true,
                            "type": "java.lang.String",
                            "url": "",
                            "location": "url",
                            "name": "userid"
                        },
                        {
                            "required": true,
                            "type": "java.lang.String",
                            "url": "",
                            "location": "url",
                            "name": "ssn"
                        }
                    ],
                    "consumes": null,
                    "produces": null,
                    "description": "",
                    "methods": [
                        "POST"
                    ],
                    "url": "http://javadoc/poja-sumbit.html",
                    "path": "/submit/{userid}/{ssn}"
                },
                {
                    "parameters": [
                        {
                            "required": true,
                            "type": "java.lang.String",
                            "url": "",
                            "location": "url",
                            "name": "userid"
                        }
                    ],
                    "consumes": null,
                    "produces": null,
                    "description": "",
                    "methods": [
                        "POST"
                    ],
                    "url": "http://javadoc/poja-sumbit.html",
                    "path": "/submit/{userid}"
                }
            ]
        ],
        "name": "uncategorized"
    }
]
```

# Resources

1. Example showing how to integrate Spring Boot and Swagger - [here](https://dzone.com/articles/spring-boot-2-restful-api-documentation-with-swagg)

2. Swagger annotation package - [here](http://docs.swagger.io/swagger-core/v1.5.0/apidocs/io/swagger/annotations/package-summary.html)