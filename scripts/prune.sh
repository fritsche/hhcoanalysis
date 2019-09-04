#!/usr/bin/env bash

set -e

if [ "$#" -ne 4 ]; then
	echo "Expected: algorithm <name> and prune method [MINMAX|SDE|LPNORM] <group> <replace[true|false]>"
	exit 1
fi

ms=(5 10 15)
problems=(MaF01 MaF02 MaF03 MaF04 MaF05 MaF06 MaF07 MaF08 MaF09 MaF10 MaF11 MaF12 MaF13 MaF14 MaF15)
methodology=MaFMethodology
algorithm=$1
runs=20
replace=$4 # execute and replace if result exists
jar=target/HHCOAnalysis-1.0-SNAPSHOT-jar-with-dependencies.jar
main=br.ufpr.inf.cbio.hhcoanalysis.prune.Prune
javacommand="java -Duser.language=en -cp $jar -Xmx1g $main"
dir=$(pwd)
# method
method=$2
group=$3

function prune {
    m=$1
    size=$2
    folder=$3
    for problem in "${problems[@]}"; do
        mkdir -p "$dir/experiment/$methodology/$m/$folder/$algorithm$method/$problem"
        for (( id = 0; id < $runs; id++ )); do
            # input file
            input="$dir/experiment/$methodology/$m/$group/$algorithm/$problem/FUN$id.tsv"
            # output file
            output="$dir/experiment/$methodology/$m/$folder/$algorithm$method/$problem/FUN$id.tsv"
            if [ ! -s $output ] || [ "$replace" = true ]; then
                rm -f $output
                params="-in $input -out $output -m $method -s $size"
                echo "$javacommand $params 2>> err.log" > job.log
                cat job.log
                batch < job.log
            fi
        done
    done
}

# prune to pool size
prune 5 210 pool
prune 10 275 pool
prune 15 135 pool

# prune to cec size
prune 5 240 cec
prune 10 240 cec
prune 15 240 cec

echo "Done."
