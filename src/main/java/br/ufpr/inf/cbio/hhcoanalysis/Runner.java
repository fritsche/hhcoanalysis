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
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.uma.jmetal.algorithm.multiobjective.moead.util.MOEADUtils;
import org.uma.jmetal.util.SolutionListUtils;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;

/**
 *
 * @author Gian Fritsche <gmfritsche at inf.ufpr.br>
 */
public class Runner extends br.ufpr.inf.cbio.hhco.runner.Runner {

    @Override
    public void printResult() {

        List population = SolutionListUtils.getNondominatedSolutions(algorithm.getResult());

        // prune output population size
        if (population.size() > 240) {
            population = MOEADUtils.getSubsetOfEvenlyDistributedSolutions(population, 240);
        }

        String folder = experimentBaseDirectory + "/";

        Utils outputUtils = new Utils(folder);
        outputUtils.prepareOutputDirectory();

        try {
            new SolutionListOutput(population).printObjectivesToFile(folder + "FUN" + id + ".tsv");
        } catch (IOException ex) {
            Logger.getLogger(Runner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
