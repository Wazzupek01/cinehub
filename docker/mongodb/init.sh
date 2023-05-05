#!/bin/sh
mongosh \
    -u ${MONGO_INITDB_ROOT_USERNAME} \
    -p ${MONGO_INITDB_ROOT_PASSWORD} \
    --authenticationDatabase admin ${MONGO_NAME} \
<<-EOJS
db.createUser({
    user: "${MONGO_USER}",
    pwd: "${MONGO_PASS}",
    roles: [{
        role: "readWrite",
        db: "${MONGO_NAME}"
    }]
})
EOJS