CODEBASE="http://l040101-ws09.ua.pt/"$1"/classes/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=true\
     -Djava.security.policy=java.policy\
     ServerSide.main.ServerRegisterRemoteObject 22101 l040101-ws09.ua.pt 22100
