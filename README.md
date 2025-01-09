# Automatic Frontend Generation Framework

![Version](https://img.shields.io/badge/version-0.1.0-blue)
[![Java](https://img.shields.io/badge/Java-17-brightgreen)](https://www.oracle.com/java/technologies/javase-downloads.html)
[![React](https://img.shields.io/badge/React-18.3.1-blue)](https://reactjs.org/)
[![OpenAPI](https://img.shields.io/badge/OpenAPI-3.0.x%20%7C%203.1.0-orange)](https://www.openapis.org/)

Welcome to the **Automatic Frontend Generation Framework**! This project aims to streamline the process of building React-based frontends by automating the generation of UI components, pages, and client-side API integrations **directly from an OpenAPI Specification**.

This framework is specifically designed for backend developers and teams who need a functional, customizable frontend without diving deep into frontend technologies. By using configuration files such as the **Page Configuration File** and **User Customization Files**, along side with the **OpenAPI Specification**, the framework ensures that the frontend is always in sync with the backend while allowing flexibility in styling and structure.

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

### System and Framework Requirements
- **Operating System**: Windows
- **Java**: Java 17 or later is required to run the framework.
- **Maven**: Required to build the project and generate the `.jar` file.
- **Node.js**: Users must have Node.js installed to run the framework.
- **OpenAPI Version**: Supports OpenAPI versions 3.0.x and 3.1.0.

### Input Files
The framework requires the following input files to function:
- **[OpenAPI Specification](#i-openapi-specification)**: The OpenAPI Specification used as input must be tailored to include specific extensions and configurations required by the framework.
- **[Pages Configuration File](#ii-pages-configuration-file)**: Maps API endpoints to frontend pages.
- **[User Customization Files](#iii-user-customization-files)**: Predefined and customizable styles for components and pages.

#### Download and Build the Framework
- **Clone the Repository**
- **Build the Project** : Run the commands **'mvn clean'** and **'mvn install'** to clean, build, and install the project.
- **Locate the .jar File** : Navigate to the **target** folder.

### 1. Run the Initialization Command
- Use the following command to initialize the project:
  ```
  java -jar FrontendGenerator-<version>.jar init <ProjectName>
  ```
- This will create a new directory named ProjectName (or Untitled by default if no name is provided) with the following structure:
  ```
  ProjectName/
  ├── build/
  ├── styles/
  ├── openapi.yaml
  └── pages.xml
  ```
i. `build/` Folder:
   - This folder will contain the initialized React application.
   - It serves as the workspace for the generated frontend code.
     
ii. `styles/` Folder:
   - Includes predefined styles such as CSS files for pages and components.
   - Users can modify these files to customize the appearance of the generated frontend.

iii. `openapi.yaml` File:
   - A null file created for the user to add their OpenAPI Specification.
   - Define your API endpoints, request/response schemas, and other specifications in this file.

iv. `pages.xml` File:
   - A null file for defining the structure of the pages in the frontend.
   - Specify the resource URL, resource method, and configurations for each page.


### 2: Add Input Files
#### i. OpenAPI Specification
The framework requires specific custom extensions in the OpenAPI Specification for it to function correctly and leverage its full capabilities. Below is an explanation of each required extension, where it must be included, and its purpose:

- `x-pageTitle` extension:
  - **Where it must be included**: Inside API operations.
  - **Purpose**: Specifies the title of the frontend page corresponding to the API operation.
  - **Example**: The generated page for the `GET /pets` endpoint will have the title "All Pets". 
    ```
    /pets:
      get:
        summary: Get all pets
        operationId: getAllPets
        x-pageTitle: All Pets
    ```

- `x-displayName` extension:
  - **Where it must be included**: Inside parameters (path or query) and schema properties.
  - **Purpose**: Provides a readable label for parameters and schema properties in the generated forms and UI.
  - **Example 01**: The `id` parameter will be labeled as "ID" in the form.
    ```
    parameters:
      - name: id
        in: path
        required: true
        description: ID of the pet
        schema:
          type: integer
        x-displayName: ID
    ```
  - **Example 02**: The `name` property will appear as "Name" in the form.
    ```
    components:
      schemas:
        Pet:
          type: object
          properties:
            name:
              type: string
              x-displayName: Name
    ```

- `x-nextPages` extension:
  - **Where it must be included**: Inside API operations.
  - **Purpose**: Defines the next pages in the navigation flow after interacting with the current page.
  - **Example**: After fetching the pet list (`200` response), users can navigate to pages for viewing pet details (`GetPetById`) or creating a new pet (`CreatePet`).   
    ```
    responses:
      '200':
        description: A list of pets
        x-nextPages:
          - GetPetById
          - CreatePet
    ```

##### Special Note:
The framework does not support defining schemas directly inside the paths object. All schemas must be defined within the components object and referenced as needed. 
- Example Correct Usage:
  ```
  paths:
    /pets:
      post:
        summary: Create a new pet
        requestBody:
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Pet'
  
  components:
    schemas:
      Pet:
        type: object
        properties:
          name:
            type: string
          species:
            type: string
  ```

#### ii. Pages Configuration File
The Page Configuration File defines the mapping between API endpoints and the frontend pages to be generated. It specifies details such as the API resource URL, HTTP method, and the corresponding page name.
##### Supported Features
1. `<page>` element:
  - Represents a single page to be generated in the frontend.
  - Each page maps to a specific API endpoint and HTTP method.
  - Example:
    ```
    <page resource-url="/pets" resource-method="GET">GetAllPets</page>
    ```
2. Attributes:
  - `resource-url`: Specifies the API endpoint for which the page is generated.
      - Example: `/pets` or `/pets/{id}`.
  - `resource-method: Indicates the HTTP method used for the API operation.
      - Supported Methods: `GET`, `POST`, `DELETE`, etc.
      - Case-insensitive.
  - Content (Text): The name of the page that will be generated in the React application.
      - Example: `GetAllPets` or `CreatePet`.
  
 An example Page Configuration File:
```
<?xml version="1.0" encoding="UTF-8"?>
<pages>
    <page resource-url="/pets" resource-method="GET">GetAllPets</page>
    <page resource-url="/pets" resource-method="POST">CreatePet</page>
    <page resource-url="/pets/{id}" resource-method="GET">GetPetById</page>
    <page resource-url="/pets/{id}" resource-method="DELETE">DeletePet</page>
</pages>
```

#### iii. User Customization Files
The User Customization Files allows users to customize the appearance and behavior of the generated frontend. These files are part of the `styles` folder, which is created after running the initialization command.
The styles folder is organized as follows:
```
styles/
├── components/
│ ├── Alert.css
│ ├── InputField.css
│ ├── KeyValuePair.css
│ └── Page.css
├── pages/
└── index.css
```
##### Customization Options
1. `components/` Folder:
  - Contains predefined CSS files for individual components such as `Alert.css`, `InputField.css`, and `KeyValuePair.css`.
  - Customization: Users can alter the styles in these files without changing the class names. This ensures the functionality of the components remains intact while allowing for appearance modifications.

2. `pages/` Folder:
  - Enables **page-specific styling** through .js files.
  - Customization:
    - Users can create a style file for each page in the format `PageNameStyles.js`.
    - For example, if a page `CreatePet.jsx` exists in the generated frontend, the user can create a corresponding `CreatePetStyles.js` file in this folder.
  - Styling Example:
    ```
    const styles = {
      pageContainer: {
        maxWidth: '700px',
        margin: '0 auto',
        padding: '20px',
        backgroundColor: '#f7f9fc',
      },
      titleBar: {
        backgroundColor: '#3498db',
        color: 'white',
        padding: '20px',
        borderRadius: '10px',
        textAlign: 'center',
      },
    };

    export default styles;
    ```
  - Naming Convention:
    - Use camelCase for class names in `.js` files (e.g., `pageContainer`).
    - These correspond to kebab-case class names in CSS files (e.g., `page-container`).
  - Integrating Prebuilt Component Styles:
    - Page-specific files can incorporate styles from prebuilt components using their names.
    - Example:
      ```
      const styles = {
          inputField: {
              container: {
                  display: 'flex',
                  flexDirection: 'column',
                  marginBottom: '20px',
              },
              label: {
                  marginBottom: '10px',
                  fontWeight: '600',
                  color: '#2c3e50',
              },
              input: {
                  padding: '14px',
                  fontSize: '16px',
                  border: '1px solid #ccd1d9',
                  borderRadius: '6px',
                  backgroundColor: '#f9f9f9',
              },
          },
      };
      
      export default styles;
      ```
      
3. `index.css` File:
  - Provides a central location for global styles applicable across the entire frontend.
  - Users can define overarching styles for the application here.

### 3: Generate Frontend Code
- After adding the input files, execute the following command to generate the required React code:
  ```
  java -jar FrontendGenerator-<version>.jar build
  ```
- This command will:
  - Create the frontend components for the pages defined in `pages.xml`.
  - Generate client-side API code based on the OpenAPI Specification in `openapi.yaml`.
  - Populate the `build/` folder with the complete React project, ready for development or deployment.

### 4: Run the Application
- To preview and test the generated frontend, execute:
  ```
  java -jar FrontendGenerator-<version>.jar run
  ```
- This will launch a local server and allow you to interact with the generated frontend in your browser.

### 5: Test, Customize, and Iterate
- Test the generated frontend and make changes to the input files (`openapi.yaml`, `pages.xml`, or CSS files) as needed.
- Re-run the build and run commands to apply updates and test your changes

### 6: View Available Commands
- To view all available commands and their usage, run:
  ```
  java -jar FrontendGenerator-<version>.jar help
  ```
- This will display detailed information about the commands you can use and their respective options.





