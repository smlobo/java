#!/bin/csh
# 
# Command line: timeit program1 program2 ...
#
# program1, program 2, ... are names of programs to execute on input
# files below

# the array of programs from the commandline
set program = $argv[1]

# adjust as needed
set CPULIMIT = 30
limit cpu $CPULIMIT seconds
limit core 0

# adjust as needed
set inputpath = "/u/cos226/ftp/location"

# input files:  list all data files in increasing order of size
set input = ( input5.txt              \
              input5-2yes.txt         \
              input5-2no.txt          \
              input5-66yes.txt        \
              input5-99998yes.txt     \
              input5-99993no.txt      \
              input15.txt             \
              input15-2.txt           \
              input15-1653yes.txt     \
              input15-100000yes.txt   \
              input15-100000no.txt    \
              input100.txt            \
              input100-2.txt          \
              input100-99998no.txt    \
              input100-100000yes.txt  \
              input100-150868yes.txt  \
              input1000.txt           \
              input1000-2yes.txt      \
              input1000-3460no.txt    \
              input1000-78468yes.txt  \
              input1000-100000yes.txt \
              input1000-100000no.txt  \
)

# print header
printf "CPU limit = %d seconds\n\n" $CPULIMIT
printf "%-25s" "Data File"
foreach program ($argv)
    printf "%20s" $program
end
printf "\n"

# print right number of = for table
@ i = 25 + 20 * $#argv 
while ($i > 0)
    printf "="
    @ i = $i - 1
end
printf "\n"


# time it and print out row for each data file and  column for each program
foreach datafile ($input)
    printf "%-25s" $datafile
    if (-f $inputpath/$datafile) then
         foreach program ($argv)
              # printing running time of program on datafile
              # -p flag with time to ensure its output is measured in seconds and not minutes
              nice /usr/bin/time -p $program <                    \
                   $inputpath/$datafile |&                        \
                   egrep '^user[ ]*[0-9]' |                       \
                   awk '{ if ($2 >= '$CPULIMIT') printf "%20s", "CPU limit"; else printf("%20.2f", $2) }'
              # egrep, awk commands extract second column of row corresponding to user time

         end
    else printf "could not open" $datafile
    endif
    printf "\n"
      
end
