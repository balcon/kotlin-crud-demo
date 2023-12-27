package com.github.balcon.kotlincruddemo.exception

class ResourceNotExistsException(
    resource: String,
    id: Int,
) : RuntimeException("Resource not exists [$resource id: $id]")
