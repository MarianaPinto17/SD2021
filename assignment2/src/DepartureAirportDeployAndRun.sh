#!/usr/bin/env bash

echo "Transfering data to the Departure Airport node."
sshpass -f password ssh sd101@l040101-ws02.ua.pt 'mkdir -p test/AirLift'
sshpass -f password ssh sd101@l040101-ws02.ua.pt  'rm -rf test/AirLift/*'
sshpass -f password scp test/out/departureairportDir.zip sd101@l040101-ws02.ua.pt:test/AirLift
echo "Decompressing data sent to the departure airport node."
sshpass -f password ssh sd101@l040101-ws02.ua.pt 'cd test/AirLift ; unzip -uq departureairportDir.zip'
echo "Executing program at the departure airport node."
sshpass -f password ssh sd101@l040101-ws02.ua.pt 'cd test/AirLift/departureairportDir ; java ServerSide.main.DepartureAirportMain 22105 l040101-ws01.ua.pt 22100'
