#!/usr/bin/env bash

set -e

source scripts/seeds.sh

if [ "$#" -ne 2 ]; then
	echo "Expected algorithm <name> and <group>"
	exit 1
fi

method="local"
dir=$(pwd)
if [ "$method" = "remote" ]; then
	execute="sbatch -J $name.run $dir/scripts/addjob.sh" # for running on process queue
	dir="/home/gian/hhcoanalysis/"
else
	execute="bash $dir/scripts/addbatch.sh" # for running locally
fi

mvn package -DskipTests

ms=(10 15)
problems=(MaF01 MaF02 MaF03 MaF04 MaF05 MaF06 MaF07 MaF08 MaF09 MaF10 MaF11 MaF12 MaF13 MaF14 MaF15)
methodology=MaFMethodology
algorithm=$1
runs=20
replace=false # execute and replace if result exists
jar=target/HHCOAnalysis-1.0-SNAPSHOT-jar-with-dependencies.jar
main=br.ufpr.inf.cbio.hhcoanalysis.runner.Main
javacommand="java -Duser.language=en -cp $jar -Xmx1g $main"
seed_index=0
group=$2

for m in "${ms[@]}"; do
	for problem in "${problems[@]}"; do
		for (( id = 0; id < $runs; id++ )); do
			# each objective, problem and independent run (id) uses a different seed
			seed=${seeds[$seed_index]}
			# different algorithms on the same problem instance uses the same seed
			output="$dir/experiment/$methodology/$m/$group/$algorithm/$problem/"
			file="$output/FUN$id.tsv"
			if [ ! -s $file ] || [ "$replace" = true ]; then
				params="-P $output -M $methodology -A OFF -a $algorithm -p $problem -m $m -id $id -s $seed"
				$execute "$javacommand $params 2>> $algorithm.$seed.log"
			fi
			seed_index=$((seed_index+1))
		done
	done
done

rm job.log


