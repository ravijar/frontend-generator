# Automatic Frontend Generation Framework

## Overview
This section is for developers who will refer to and work on the source code of the Automatic Frontend Generation Framework.

The framework automates the process of generating frontend components based on the OpenAPI Specification (OAS). It uses a modular and extensible architecture to ensure flexibility, maintainability, and ease of contribution.

This documentation provides an in-depth explanation of the architecture, key packages, and components of the FrontendGenerator Framework. It is intended for developers who wish to understand, extend, or maintain the codebase.

## Table of Contents
- [Introduction](#1-introduction)
- [Technology Stack](#2-technology-stack)
- [Project Architecture](#3-project-architecture)
  - [High-Level Design](#high-level-design)
  - [Directory Structure](#directory-structure)
- [Key Packages](#4-key-packages)
  - [com.ravjar.cli](#comravjarcli)
  - [com.ravjar.config](#comravjarconfig)
  - [com.ravjar.core](#comravjarcore)
  - [com.ravjar.generator](#comravjargenerator)
  - [com.ravjar.handler](#comravjarhandler)
  - [com.ravjar.helper](#comravjarhelper)
  - [com.ravjar.model](#comravjarmodel)
  - [com.ravjar.parser](#comravjarparser)
  - [com.ravjar.populator](#comravjarpopulator)
- [How It Works](#5-how-it-works)
- [Testing and Debugging](#6-testing-and-debugging)
- [Future Enhancements](#7-future-enhancements)

## 1. Introduction
The FrontendGenerator automates the generation of React-based frontends using an OpenAPI Specification, Pages Configuration File, and User Customization Files. It bridges the gap between backend and frontend teams by streamlining UI creation through code generation.

## 2. Technology Stack
- **Language:** Java  
- **Build Tool:** Maven  
- **Code Generation:** Freemarker templates  
- **Parsing:** OpenAPI and XML parsers  
- **Frontend Framework:** React (generated code)  
- **Testing:** JUnit  

## 3. Project Architecture
### High-Level Design
![high-level-architecture.png](docs/resources/documentation/high-level-architecture.png)
### Detailed Architecture
![detailed-architecture.png](docs/resources/documentation/detailed-architecture.png)
### Directory Structure
```plaintext
FrontendGeneratorJava/
├── docs/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.ravjar/
│   │   │       ├── cli/
│   │   │       ├── config/
│   │   │       ├── core/
│   │   │       ├── generator/
│   │   │       ├── handler/
│   │   │       ├── helper/
│   │   │       ├── model/
│   │   │       │   ├── freemarker/
│   │   │       │   ├── openapi/
│   │   │       │   └── xml/
│   │   │       ├── parser/
│   │   │       └── populator/
│   │   └── resources/
│   │           └── templates/
│   └── test/
└── README.md
```

## 4. Key Packages
### com.ravjar.core
**Purpose:** Manages project lifecycle operations.

**Key Classes:**
- `AppCLI`: CLI entry point, mapping commands to subcommands.
- `ProjectManager`: Coordinates handlers, parsers, generators, and file operations.

### com.ravjar.cli
**Purpose:** Provides CLI functionality.

**Key Classes:**
- `MainCommand`: Manages subcommands like init, generate, apply, run, and test.
- `InitCommand`: Initializes the project structure.
- `GenerateCommand`: Generates React frontends and client-side APIs.
- `ApplyCommand`: Applies user styles.
- `RunCommand`: Runs the React project.
- `TestCommand`: Runs tests.

### com.ravjar.handler
**Purpose:** Manages file operations and project configurations.

**Key Classes:**
- `CommandHandler`: Executes external commands.
- `ConfigHandler`: Manages project properties.
- `FileHandler`: Handles file and directory operations.
- `TemplatesConfigLoader`: Loads template configurations.

### com.ravjar.parser
**Purpose:** Parses OpenAPI specifications and XML configuration files.

**Key Classes:**
- `OpenAPIParser`: Extracts API operations and schemas.
- `XMLParser`: Extracts page and component definitions.

### com.ravjar.config
**Purpose:** Configures external libraries.

**Key Components:**
- `FreeMarkerConfig`: Manages FreeMarker template configurations.

### com.ravjar.generator
**Purpose:** Generates frontend assets.

**Key Classes:**
- `ClientAPIGenerator`: Generates client-side API code.
- `CSSGenerator`: Creates CSS files.
- `JSGenerator`: Generates JavaScript logic.
- `ReactGenerator`: Generates React components and pages.

### com.ravjar.helper
**Purpose:** Provides utility functions.

**Key Classes:**
- `OpenAPIConverter`: Converts HTTP methods.
- `PopulatorHelper`: Maps XML components to FreeMarker models.
- `StringConverter`: Converts string formats.

### com.ravjar.model
**Purpose:** Defines foundational data models.

**Sub-Packages:**
- `freemarker`: Models for FreeMarker templates.
- `openapi`: Models for OpenAPI specification elements.
- `xml`: Models for XML components.

### com.ravjar.populator
**Purpose:** Transforms XML components into FreeMarker models.

## 5. How It Works
### Execution Flow and Command Lifecycle
#### Initialization (`init --name <project-name>`)
#### Frontend and Client API Generation (`generate`)
#### Apply User Styles (`apply --styles`)
#### Running the Application (`run`)

## 6. Testing and Debugging
- **Unit Tests:** Located in `test/`.
- **Logging:** Uses `java.util.logging`.
- **Error Handling:** Custom exceptions for invalid inputs.

## 7. Future Enhancements
- Support additional frontend frameworks (Angular, Vue).
- Introduce more component types.
- Improve CLI interactivity.
