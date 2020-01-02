#!/usr/bin/env bash

set -e

if [ "$#" -ne 3 ]; then
	echo "Expected: algorithm <name> and indicator [HV|IGD] and <group>"
	exit 1
fi

methodology=MaFMethodology
algorithm=$1
runs=20
dir=$(pwd)
ind=$2
group=$3
error=0

m=5
problems=(MaF01 MaF02 MaF03 MaF04 MaF05 MaF06 MaF07 MaF08 MaF09 MaF10 MaF11 MaF12 MaF13 MaF14 MaF15)
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

m=10
problems=(MaF01 MaF04 MaF05 MaF06 MaF07 MaF09 MaF10 MaF13 MaF15)
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

m=15
problems=(MaF01 MaF04 MaF06 MaF07 MaF15)
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

if [ $error -ne 0 ]; then
    (>&2 echo "Something went wrong")
    exit 1
fi
