#!/usr/bin/env bash

set -e

source scripts/seeds.sh

if [ "$#" -ne 2 ]; then
	echo "Expected algorithm <name> and indicator [HV|IGD]"
	exit 1
fi

ms=(5 10 15)
problems=(MaF01 MaF02 MaF03 MaF04 MaF05 MaF06 MaF07 MaF08 MaF09 MaF10 MaF11 MaF12 MaF13 MaF14 MaF15)
methodology=MaFMethodology
algorithm=$1
runs=20
replace=false # execute and replace if result exists
jar=target/HHCOAnalysis-1.0-SNAPSHOT-jar-with-dependencies.jar
main=br.ufpr.inf.cbio.hhco.runner.CommandLineIndicatorRunner
javacommand="java -Duser.language=en -cp $jar -Xmx1g $main"
dir=$(pwd)
ind=$2
group=cec
seed_index=0
for problem in "${problems[@]}"; do
    for m in "${ms[@]}"; do
        for (( id = 0; id < $runs; id++ )); do
            # each objective, problem and independent run (id) uses a different seed
            seed=${seeds[$seed_index]}
            # different algorithms on the same problem instance uses the same seed
            base="$dir/experiment/$methodology/$m/$group/$algorithm/$problem/FUN$id.tsv"
            file="$base.$ind"
            if [ ! -s $file ] || [ "$replace" = true ]; then
                rm -f $file
                params="$ind $dir/experiment/referenceFronts/"$problem"_"$m".ref $base $norm $seed"
                echo "$javacommand $params > $file" > job.log
				cat job.log
				batch < job.log
            fi
            seed_index=$((seed_index+1))
        done
    done
done
