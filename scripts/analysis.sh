#!/usr/bin/env bash

set -e

if [ "$#" -lt 5 ]; then
	echo "Expected: indicator [HV|IGD], <experiment>, <group> and list of algorithms <Alg1 Alg2 ...>"
	exit 1
fi

ms=(5 10 15)
problems=(MaF01 MaF02 MaF03 MaF04 MaF05 MaF06 MaF07 MaF08 MaF09 MaF10 MaF11 MaF12 MaF13 MaF14 MaF15)
methodology=MaFMethodology
ind=$1; shift
experiment=$1; shift
group=$1; shift
confidence=0.95
printSTD=FALSE
algorithms=( "$@" )
 
if [ "${#algorithms[@]}" -lt "2" ] ; then
	(>&2 echo "ERROR: the number of algorithms should be higher than 1")
	exit 1
fi

echo $ind ${#algorithms[@]} ${algorithms[@]} ${#problems[@]} ${problems[@]} ${#ms[@]} ${ms[@]} experiment/$methodology $experiment $confidence

jar=target/HHCOAnalysis-1.0-SNAPSHOT-jar-with-dependencies.jar
main=br.ufpr.inf.cbio.statistics.StatisticalTests
javacommand="java -Duser.language=en -cp $jar -Xmx1g $main"
command="$javacommand $ind ${#algorithms[@]} ${algorithms[@]} ${#problems[@]} ${problems[@]} ${#ms[@]} ${ms[@]} experiment/$methodology $experiment $group $confidence $printSTD"

echo "$command"
eval "$command"

output=experiment/$methodology/R/$experiment/KruskalTest$ind.tex

echo "%$output" > $output
echo "\documentclass[]{article}" >> $output
echo "\usepackage{colortbl}" >> $output
echo "\usepackage[table*]{xcolor}" >> $output
echo "\usepackage{multirow}" >> $output
echo "\usepackage{fixltx2e}" >> $output
echo "\usepackage{stfloats}" >> $output
echo "\usepackage{psfrag}" >> $output
echo "\usepackage[]{threeparttable}" >> $output
echo "\usepackage{multicol}" >> $output
echo "\usepackage{lscape}" >> $output
echo "\xdefinecolor{gray95}{gray}{0.75}" >> $output
echo "\begin{document}" >> $output

if (( ${#algorithms[@]} > 4 )); then
  echo "\begin{landscape}" >> $output
fi

echo "\begin{table}" >> $output
echo "\caption{$ind. Mean and standard deviation}" >> $output
echo "\label{table:mean.$ind}" >> $output
echo "\centering" >> $output
echo "\begin{footnotesize}" >> $output
aux=""
head=""
for (( i = 0; i < ${#algorithms[@]}; i++ )); do
  aux=$aux"l|"
  head=$head" & "${algorithms[i]}
done
echo "\begin{tabular}{|l|l|$aux}" >> $output
echo "\hline" >> $output
echo "Obj. & problem $head \\\\ \hline" >> $output
for (( i = 0; i < ${#ms[@]}; i++ )); do
  cat "experiment/$methodology/R/$experiment/${ms[i]}/$ind.tex" >> $output
  echo "\hline" >> $output
done
echo "\end{tabular}" >> $output
echo "\end{footnotesize}" >> $output
echo "\end{table}" >> $output

if (( ${#algorithms[@]} > 4 )); then
  echo "\end{landscape}" >> $output
fi

echo "\end{document}" >> $output

pdflatex $output
Rscript experiment/$methodology/R/$experiment/$ind/friedmanplot.R

echo "done."
