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
package br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.baseline;

import br.ufpr.inf.cbio.hhco.algorithm.HypE.COHypEConfiguration;
import br.ufpr.inf.cbio.hhco.algorithm.MOEAD.COMOEADConfiguration;
import br.ufpr.inf.cbio.hhco.algorithm.MOEADD.COMOEADDConfiguration;
import br.ufpr.inf.cbio.hhco.algorithm.MOMBI2.COMOMBI2Configuration;
import br.ufpr.inf.cbio.hhco.algorithm.NSGAII.CONSGAIIConfiguration;
import br.ufpr.inf.cbio.hhco.algorithm.NSGAIII.CONSGAIIIConfiguration;
import br.ufpr.inf.cbio.hhco.algorithm.SPEA2.COSPEA2Configuration;
import br.ufpr.inf.cbio.hhco.algorithm.SPEA2SDE.COSPEA2SDEConfiguration;
import br.ufpr.inf.cbio.hhco.algorithm.ThetaDEA.COThetaDEAConfiguration;
import br.ufpr.inf.cbio.hhco.config.AlgorithmConfiguration;
import br.ufpr.inf.cbio.hhco.hyperheuristic.CooperativeAlgorithm;
import br.ufpr.inf.cbio.hhco.hyperheuristic.selection.CastroRoulette;
import br.ufpr.inf.cbio.hhco.hyperheuristic.selection.SelectionFunction;
import br.ufpr.inf.cbio.hhco.metrics.fir.FitnessImprovementRateCalculator;
import br.ufpr.inf.cbio.hhco.metrics.fir.R2TchebycheffFIR;
import br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.HHcMOEA.HHcMOEA;
import br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.HHcMOEA.HHcMOEABuilder;
import br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.HHcMOEA.HHcMOEAConfiguration;
import br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.HHcMOEA.SelectedMOEALogger;
import br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.baseline.evaluation.NewvsOldPopulation;
import br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.baseline.migration.SharePopulation;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;

/**
 *
 * @author Gian Fritsche <gmfritsche at inf.ufpr.br>
 * @param <S>
 */
public class BaselineConfiguration<S extends Solution<?>> implements AlgorithmConfiguration<Baseline> {

    protected final String name;
    protected SelectionFunction<CooperativeAlgorithm> selection;
    protected FitnessImprovementRateCalculator fir;
    protected Problem problem;
    protected int popSize;
    private final boolean analysis;
    private String id;
    private String outputfolder;

    public BaselineConfiguration(String name, boolean analysis, String id, String outputfolder) {
        this.name = name;
        this.analysis = analysis;
        this.id = id;
        this.outputfolder = outputfolder;
    }

    @Override
    public Baseline configure(int popSize, int maxFitnessEvaluations, Problem problem) {

        this.problem = problem;
        this.popSize = popSize;

        setup();

        BaselineBuilder builder = new BaselineBuilder(problem);

        switch (name) {
            case "CONSGAII":
                builder.addAlgorithm(new CONSGAIIConfiguration().configure(popSize, maxFitnessEvaluations, problem));
                break;
            case "CONSGAIII":
                builder.addAlgorithm(new CONSGAIIIConfiguration().configure(popSize, maxFitnessEvaluations, problem));
                break;
            case "COSPEA2":
                builder.addAlgorithm(new COSPEA2Configuration().configure(popSize, maxFitnessEvaluations, problem));
                break;
            case "COSPEA2SDE":
                builder.addAlgorithm(new COSPEA2SDEConfiguration().configure(popSize, maxFitnessEvaluations, problem));
                break;
            case "COHypE":
                builder.addAlgorithm(new COHypEConfiguration().configure(popSize, maxFitnessEvaluations, problem));
                break;
            case "COMOMBI2":
                builder.addAlgorithm(new COMOMBI2Configuration().configure(popSize, maxFitnessEvaluations, problem));
                break;
            case "COThetaDEA":
                builder.addAlgorithm(new COThetaDEAConfiguration().configure(popSize, maxFitnessEvaluations, problem));
                break;
            case "COMOEAD":
                builder.addAlgorithm(new COMOEADConfiguration().configure(popSize, maxFitnessEvaluations, problem));
                break;
            case "COMOEADD":
                builder.addAlgorithm(new COMOEADDConfiguration().configure(popSize, maxFitnessEvaluations, problem));
                break;
            default: // ALL
                builder
                        .addAlgorithm(new COSPEA2Configuration().configure(popSize, maxFitnessEvaluations, problem))
                        .addAlgorithm(new COMOEADConfiguration().configure(popSize, maxFitnessEvaluations, problem))
                        .addAlgorithm(new CONSGAIIConfiguration().configure(popSize, maxFitnessEvaluations, problem))
                        .addAlgorithm(new COMOEADDConfiguration().configure(popSize, maxFitnessEvaluations, problem))
                        .addAlgorithm(new COMOMBI2Configuration().configure(popSize, maxFitnessEvaluations, problem))
                        .addAlgorithm(new CONSGAIIIConfiguration().configure(popSize, maxFitnessEvaluations, problem))
                        .addAlgorithm(new COThetaDEAConfiguration().configure(popSize, maxFitnessEvaluations, problem))
                        .addAlgorithm(new COSPEA2SDEConfiguration().configure(popSize, maxFitnessEvaluations, problem))
                        .addAlgorithm(new COHypEConfiguration().configure(popSize, maxFitnessEvaluations, problem));
        }

        builder = (BaselineBuilder) builder.setName(name).setSelection(selection).setFir(fir).
                setMaxEvaluations(maxFitnessEvaluations).setPopulationSize(popSize);

        Baseline baseline = builder.setEvaluation(new NewvsOldPopulation()).setMigration(new SharePopulation()).build();

        if (analysis) {
            baseline.addObserver(new SelectedMOEALogger(outputfolder, "selected." + id));
        }

        return baseline;
    }

    @Override
    public void setup() {
        this.selection = new CastroRoulette<>();
        this.fir = new R2TchebycheffFIR(problem, popSize);
    }

}
