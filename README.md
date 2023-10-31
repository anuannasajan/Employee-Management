# Employee API

This API allows you to manage employee data.

## Endpoints

### GET /employees
Retrieve all employees.

### POST /employees
Add a new employee.

### GET /employees/{id}
Retrieve an employee by ID.

### PUT /employees/{id}
Update an employee by ID.

### DELETE /employees/{id}
Delete an employee by ID.

## Usage

To use this API, send a HTTP request to the desired endpoint. For endpoints that require an ID, replace `{id}` with the ID of the employee you want to retrieve, update, or delete.

For `POST` and `PUT` requests, include the employee data in the body of the request in JSON format.

## Error Handling

If an error occurs, the API will return a HTTP status code and a message describing the error.
