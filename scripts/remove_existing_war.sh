#!/bin/bash
# Check if the file exists
if [ -f /opt/tomcat/webapps/transaction-recorder.war ]; then
    echo "Removing existing WAR file..."
    rm -f /opt/tomcat/webapps/transaction-recorder.war
fi