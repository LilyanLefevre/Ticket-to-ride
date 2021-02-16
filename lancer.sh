#!/bin/bash
counter=1
while [ $counter -le 15 ]
do
	echo "lancer n°"
	echo $counter
	java -jar ticket-to-ride.jar >> out.txt &
	((counter++))
done
echo All done
