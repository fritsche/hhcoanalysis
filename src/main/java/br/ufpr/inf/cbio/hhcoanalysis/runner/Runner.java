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
package br.ufpr.inf.cbio.hhcoanalysis.runner;

import br.ufpr.inf.cbio.hhcoanalysis.util.SolutionListUtils;
import br.ufpr.inf.cbio.hhco.hyperheuristic.HHCO.HHCO;
import br.ufpr.inf.cbio.hhco.problem.ProblemFactory;
import br.ufpr.inf.cbio.hhco.runner.methodology.ArionMethodology;
import br.ufpr.inf.cbio.hhco.runner.methodology.MaFMethodology;
import br.ufpr.inf.cbio.hhco.runner.methodology.Methodology;
import br.ufpr.inf.cbio.hhco.runner.methodology.NSGAIIIMethodology;
import br.ufpr.inf.cbio.hhco.hyperheuristic.HHCO.observer.HHCOLogger;
import br.ufpr.inf.cbio.hhco.hyperheuristic.HHCO.observer.SelectedMOEALogger;
import br.ufpr.inf.cbio.hhco.util.output.OutputWriter;
import br.ufpr.inf.cbio.hhco.util.output.Utils;
import br.ufpr.inf.cbio.hhcoanalysis.problem.HypervolumeImprovement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.uma.jmetal.algorithm.multiobjective.moead.util.MOEADUtils;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

/**
 *
 * @author Gian Fritsche <gmfritsche at inf.ufpr.br>
 */
public class Runner extends br.ufpr.inf.cbio.hhco.runner.Runner {

    private boolean prune = false;
    private String analysis = "OFF";

    public Runner(boolean prune) {
        this.prune = prune;
    }

    @Override
    public void printResult() {
        List population = SolutionListUtils.removeRepeatedSolutions(SolutionListUtils.getNondominatedSolutions(algorithm.getResult()));

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

    @Override
    public void run() {
        OutputWriter ow = new OutputWriter(experimentBaseDirectory + "/output/", "hvimprovement." + id);
        if (analysis.equals("ON")) {
            problem = new HypervolumeImprovement((AbstractDoubleProblem) ProblemFactory.getProblem(problemName, m), ow);
        } else {
            problem = ProblemFactory.getProblem(problemName, m);
        }
        JMetalLogger.logger.log(Level.CONFIG, "Problem: {0} with {1} objectives", new Object[]{problemName, m});

        Methodology methodology = null;
        if (methodologyName.equals(NSGAIIIMethodology.class.getSimpleName())) {
            methodology = new NSGAIIIMethodology(problemName, m);
        } else if (methodologyName.equals(MaFMethodology.class.getSimpleName())) {
            methodology = new MaFMethodology(m, problem.getNumberOfVariables());
        } else if (methodologyName.equals(ArionMethodology.class.getSimpleName())) {
            methodology = new ArionMethodology(problemName);
        } else {
            throw new JMetalException("There is no configuration for " + methodologyName + " methodology.");
        }
        JMetalLogger.logger.log(Level.CONFIG, "Methodology: {0}", methodologyName);

        int maxFitnessevaluations = methodology.getMaxFitnessEvaluations();
        JMetalLogger.logger.log(Level.CONFIG, "Max Fitness Evaluations: {0}", maxFitnessevaluations);
        popSize = methodology.getPopulationSize();

        // set seed
        JMetalRandom.getInstance().setSeed(seed);
        JMetalLogger.logger.log(Level.CONFIG, "Seed: {0}", seed);

        algorithm = factory
                .getAlgorithmConfiguration(algorithmName)
                .configure(popSize, maxFitnessevaluations, problem);

        JMetalLogger.logger.log(Level.CONFIG, "Algorithm: {0}", algorithmName);

        if (analysis.equals("ON")) {
            // create loggers
            List<HHCOLogger> loggers = new ArrayList<>();
            String outputfolder = experimentBaseDirectory + "/output/";
            // loggers.add(new MOEASFIRLogger(outputfolder, "moeasfir." + id));
            loggers.add(new SelectedMOEALogger(outputfolder, "selected." + id));

            HHCO hhco = (HHCO) algorithm;
            // append loggers to algorithm
            loggers.forEach((logger) -> {
                hhco.addObserver(logger);
            });

            AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(hhco)
                    .execute();

            // close loggers (write to file)
            loggers.forEach((logger) -> {
                logger.close();
            });
            ow.close();

            long computingTime = algorithmRunner.getComputingTime();
            JMetalLogger.logger.log(Level.INFO, "Total execution time: {0}ms", computingTime);
        } else {
            AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                    .execute();
            long computingTime = algorithmRunner.getComputingTime();
            JMetalLogger.logger.log(Level.INFO, "Total execution time: {0}ms", computingTime);
        }

    }

    public void toggleAnalysis(String analysis) {
        this.analysis = analysis;
    }

}
