Sample App Project
application.properties to be configured here as required
Business Models,Repository,Service and Api to be defined here


Note - Always add classes under the package com.vsnm
Steps for new Model(Refer Currency/Customer implementations)
Add Model class extending BaseMongoModel/BaseJpaModel depending on the DB required
(Optional.Only for custom methods)Add Repository class extending BaseMongoRepository/BaseJpaRepository
(Optional.Only for custom methods)Add Service class extending BaseMongoService/BaseJpaService
Add Api class extending BaseMongoApi/BaseJpaApi


Db properties to be mentioned in application.properties available in resource path
