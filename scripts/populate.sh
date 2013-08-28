#! /bin/sh
# This script takes two arguments:
#    1) the type of the file (ie., conductivity, concentrated, etc)
#    2) path to the data file

if [ $# -ne 2 ]
then
    echo "Usage: $0 <file type> <path to file>"
    exit 1
fi;

#TODO: set up a DB server at umb?
HOST="localhost"
USERNAME="vy"
PASSWORD="vy"

TARGET_TABLE="UNKNOWN"
case $1 in
    # conductivity
    *COND*)
	TARGET_TABLE="conductivities"
	# TODO
	;;

    # concentrated
    *IONS*)
	TARGET_TABLE="concentrations"
	# TODO
	;;

    # ELSE
    esac
done
	
#TODO: GREP for header
HEADER = "TODO"

while line read-line
do
    # TODO skip over un-related lines?
    psql -h $LOCALHOST -U $USERNAME -s "INSERT INTO $TARGET_TABLE($HEADER) VALUES ($line);"
end < $2;


echo "DONE!"



