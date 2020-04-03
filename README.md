# simple-web-service
The server is based on JDK HttpServer and tested by JUnit 5

* HTTP GET /v1/entity?id=\<ID> -- get data by key <ID>. Returns 200 OK and data, or 404 Not Found.
* HTTP PUT /v1/entity?id=\<ID>&name=\<NAME> -- create / overwrite (insert) data for the <ID>key. Returns 201 Created.
* HTTP DELETE /v1/entity?id=\<ID> -- delete data by key <ID>. Returns 202 Accepted.
  
  In Java package ru.sberbank.dao, implement the Dao interface. The class must provide the ability to work with file storage, where the key is the name of the file.
- get () - get data from a file using the <ID>key.
- insert () - create / overwrite data by the <ID>key.
- delete () - delete the file using the <ID>key.
