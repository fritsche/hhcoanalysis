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

import br.ufpr.inf.cbio.hhco.runner.methodology.MaFMethodology;
import java.util.logging.Level;
import org.apache.commons.cli.CommandLine;
import org.uma.jmetal.util.JMetalLogger;

/**
 *
 * @author Gian Fritsche <gmfritsche at inf.ufpr.br>
 */
public class Main extends br.ufpr.inf.cbio.hhco.runner.Main {

    public static Runner getRunner(CommandLine cmd) {
        String problemName = "MaF02", aux;
        int m = 5;
        long seed = System.currentTimeMillis();
        String experimentBaseDirectory = "experiment/";
        String methodologyName = MaFMethodology.class.getSimpleName();
        int id = 0;
        String algorithmName = "HHCO";

        Runner runner = new Runner();

        if ((aux = cmd.getOptionValue("a")) != null) {
            algorithmName = aux;
        }
        if ((aux = cmd.getOptionValue("p")) != null) {
            problemName = aux;
        }
        if ((aux = cmd.getOptionValue("P")) != null) {
            experimentBaseDirectory = aux;
        }
        if ((aux = cmd.getOptionValue("M")) != null) {
            methodologyName = aux;
        }
        if ((aux = cmd.getOptionValue("m")) != null) {
            m = Integer.parseInt(aux);
        }
        if ((aux = cmd.getOptionValue("id")) != null) {
            id = Integer.parseInt(aux);
        }
        if ((aux = cmd.getOptionValue("s")) != null) {
            seed = Long.parseLong(aux);
        }

        runner.setAlgorithmName(algorithmName);
        runner.setProblemName(problemName);
        runner.setObjectives(m);
        runner.setExperimentBaseDirectory(experimentBaseDirectory);
        runner.setId(id);
        runner.setMethodologyName(methodologyName);
        runner.setSeed(seed);
        return runner;

    }
    
    public static void main(String[] args) {
        JMetalLogger.logger.setLevel(Level.CONFIG);
        Runner runner = getRunner(parse(args));
        runner.setFactory(new HHCOFactory());
        runner.run();
        runner.printResult();
    }
}
