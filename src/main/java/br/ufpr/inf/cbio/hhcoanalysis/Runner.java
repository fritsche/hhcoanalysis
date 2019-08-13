/*
 * Copyright (C) 2019 Gian Fritsche <gmfritsche at inf.ufpr.br>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package br.ufpr.inf.cbio.hhcoanalysis;

import br.ufpr.inf.cbio.hhco.util.output.Utils;
import java.util.List;
import org.uma.jmetal.algorithm.multiobjective.moead.util.MOEADUtils;
import org.uma.jmetal.util.SolutionListUtils;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;

/**
 *
 * @author Gian Fritsche <gmfritsche at inf.ufpr.br>
 */
public class Runner extends br.ufpr.inf.cbio.hhco.runner.Runner {

    private boolean prune = false;

    public Runner(boolean prune) {
        this.prune = prune;
    }

    @Override
    public void printResult() {
        List population = SolutionListUtils.getNondominatedSolutions(algorithm.getResult());

        if (prune) {
            // final population size of MaF
            if (problem.getName().startsWith("MaF")) {
                popSize = 240;
            }
            // prune output population size
            if (population.size() > popSize) {
                population = MOEADUtils.getSubsetOfEvenlyDistributedSolutions(population, popSize);
            }
        }
        String folder = experimentBaseDirectory + "/";

        Utils outputUtils = new Utils(folder);
        outputUtils.prepareOutputDirectory();

        new SolutionListOutput(population).setSeparator("\t")
                .setVarFileOutputContext(new DefaultFileOutputContext(folder + "VAR" + id + ".tsv"))
                .setFunFileOutputContext(new DefaultFileOutputContext(folder + "FUN" + id + ".tsv"))
                .print();
    }

}