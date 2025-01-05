# Automatic Frontend Generation Framework

![Version](https://img.shields.io/badge/version-1.0-blue)
![License](https://img.shields.io/badge/license-MIT-green)
[![Java](https://img.shields.io/badge/Java-17-brightgreen)](https://www.oracle.com/java/technologies/javase-downloads.html)
[![React](https://img.shields.io/badge/React-18.3.1-blue)](https://reactjs.org/)

Welcome to the **Automatic Frontend Generation Framework**! This project aims to streamline the process of building React-based frontends by automating the generation of UI components, pages, and client-side API integrations directly from an OpenAPI Specification.

This framework is specifically designed for backend developers and teams who need a functional, customizable frontend without diving deep into frontend technologies. By using configuration files such as the **OpenAPI Specification** and **Page Configuration File**, the framework ensures that the frontend is always in sync with the backend while allowing flexibility in styling and structure.

### Key Features
- **Automatic React Frontend Generation**: Quickly generate fully functional React frontends from your API definitions.
- **Customizable Components and Pages**: Modify predefined styles or create custom page-specific styles with ease.
- **OpenAPI-Driven**: Leverages OpenAPI as the single source of truth to maintain backend and frontend consistency.
- **Ease of Use**: Designed to be simple and intuitive, requiring minimal effort to get started.

## Background

API-driven development has become a cornerstone of modern software engineering, enabling developers to build scalable and modular systems by decoupling backend services and frontend interfaces. This approach simplifies collaboration between teams, fosters reusability, and accelerates development cycles.

The **OpenAPI Specification (OAS)** has emerged as the standard for describing RESTful APIs in a machine-readable format. Originally introduced as part of the Swagger project, OAS is now governed by the OpenAPI Initiative under the Linux Foundation. It allows developers to define API endpoints, request/response schemas, authentication methods, and more using structured YAML or JSON files.

By leveraging the OAS as a central standard, the **Automatic Frontend Generation Framework** ensures that frontends are consistent with their respective APIs. This integration eliminates common issues caused by misaligned backend and frontend implementations, paving the way for streamlined development workflows.


## Getting Started

### Platform Requirements
- **Operating System**: Windows
- **Node.js**: Users must have Node.js installed to run the framework.
- **OpenAPI Version**: Supports OpenAPI versions 3.0.x and 3.1.0.

### Input Files
The framework requires the following input files to function:
- **OpenAPI Specification**: The OpenAPI Specification used as input must be tailored to include specific extensions and configurations required by the framework. This ensures seamless integration and accurate generation of the frontend.
- **Pages Configuration File**: Maps API endpoints to frontend pages.
- **User Customization Files**: Predefined and customizable styles for components and pages.
