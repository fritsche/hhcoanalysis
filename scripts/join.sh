#!/usr/bin/env bash

set -e

if [ "$#" -ne 2 ]; then
	echo "Expected: algorithm <name> and indicator [HV|IGD]"
	exit 1
fi

ms=(5 10 15)
problems=(MaF01 MaF02 MaF03 MaF04 MaF05 MaF06 MaF07 MaF08 MaF09 MaF10 MaF11 MaF12 MaF13 MaF14 MaF15)
methodology=MaFMethodology
algorithm=$1
runs=20
dir=$(pwd)
ind=$2
group=cec
error=0
for m in "${ms[@]}"; do
    for problem in "${problems[@]}"; do
        # remove old $ind file before computing
        rm -f $dir/experiment/$methodology/$m/$group/$algorithm/$problem/$ind
        for (( id = 0; id < $runs; id++ )); do
            file=$dir/experiment/$methodology/$m/$group/$algorithm/$problem/FUN$id.tsv.$ind
            if [ ! -s $file ]; then
                (>&2 echo "File '$file' does not exist or is empty")
                error=1
            else
                cat $file >> $dir/experiment/$methodology/$m/$group/$algorithm/$problem/$ind
            fi
        done
    done
done

if [ $error -ne 0 ]; then
    (>&2 echo "Something went wrong")
    exit 1
fi
