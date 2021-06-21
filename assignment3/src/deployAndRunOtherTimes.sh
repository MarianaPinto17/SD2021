#!/usr/bin/env bash

xterm  -T "General Repository" -hold -e "./GeneralRepositoryDeployAndRun.sh" &
sleep 2
xterm  -T "Departure Airport" -hold -e "./DepartureAirportDeployAndRun.sh" &
sleep 2
xterm  -T "Plane" -hold -e "./PlaneDeployAndRun.sh" &
sleep 2
xterm  -T "Destination Airport" -hold -e "./DestinationAirportDeployAndRun.sh" &
sleep 1
xterm  -T "Pilot" -hold -e "./PilotDeployAndRun.sh" &
sleep 1
xterm  -T "Hostess" -hold -e "./HostessDeployAndRun.sh" &
sleep 1
xterm  -T "Passenger" -hold -e "./PassengerDeployAndRun.sh" &
