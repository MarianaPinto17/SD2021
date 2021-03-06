#!/usr/bin/env bash

echo "Transfering data to the Hostess node."
sshpass -f password ssh sd101@l040101-ws06.ua.pt 'mkdir -p test/AirLift'
sshpass -f password ssh sd101@l040101-ws06.ua.pt 'rm -rf test/AirLift/*'
sshpass -f password scp dirHostess.zip sd101@l040101-ws06.ua.pt:test/AirLift
echo "Decompressing data sent to the hostess node."
sshpass -f password ssh sd101@l040101-ws06.ua.pt 'cd test/AirLift ; unzip -uq dirHostess.zip'
echo "Executing program at the hostess node."
sshpass -f password ssh sd101@l040101-ws06.ua.pt 'cd test/AirLift/dirHostess ; ./Hostess_com_d.sh'
