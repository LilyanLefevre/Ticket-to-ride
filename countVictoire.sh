#!/bin/bash
bot1=$(grep "bot 0" "./resultats.txt"|wc -l)
bot2=$(grep "bot 1" "./resultats.txt"|wc -l)
bot3=$(grep "bot 2" "./resultats.txt"|wc -l)
bot4=$(grep "bot 3" "./resultats.txt"|wc -l)

echo "le bot 0 à gagné" $bot1 " fois"
echo "le bot 1 à gagné" $bot2 " fois"
echo "le bot 2 à gagné" $bot3 " fois"
echo "le bot 3 à gagné" $bot4 " fois"
