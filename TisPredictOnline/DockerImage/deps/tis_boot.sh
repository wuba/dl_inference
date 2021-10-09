#!/bin/bash

cd /opt && tar xvf server.tar && ls
chmod 777 ./start_prepared_server.sh
./start_prepared_server.sh
