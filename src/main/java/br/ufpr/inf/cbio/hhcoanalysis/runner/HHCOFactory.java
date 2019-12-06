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

import br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.baseline.BaselineConfiguration;
import br.ufpr.inf.cbio.hhco.config.AlgorithmConfiguration;
import br.ufpr.inf.cbio.hhco.config.AlgorithmConfigurationFactory;
import br.ufpr.inf.cbio.hhco.hyperheuristic.HHCORandom.HHCORandomConfiguration;
import br.ufpr.inf.cbio.hhco.hyperheuristic.selection.ArgMaxSelection;
import br.ufpr.inf.cbio.hhco.hyperheuristic.selection.CastroRoulette;
import br.ufpr.inf.cbio.hhco.metrics.fir.EpsilonFIR;
import br.ufpr.inf.cbio.hhco.metrics.fir.R2TchebycheffFIR;
import br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.baseline.evaluation.EvalSelected;
import br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.baseline.evaluation.EvaluateAll;
import br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.baseline.evaluation.EvaluationStrategy;
import org.uma.jmetal.problem.Problem;
import br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.baseline.migration.MigrationStrategy;
import br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.baseline.migration.ShareOffspring;
import br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.baseline.migration.SharePopulation;

/**
 *
 * @author Gian Fritsche <gmfritsche at inf.ufpr.br>
 */
public class HHCOFactory extends AlgorithmConfigurationFactory {

    private final Problem problem;
    private final int populationSize;

    public HHCOFactory(Problem problem, int populationSize) {
        this.problem = problem;
        this.populationSize = populationSize;
    }

    @Override
    public AlgorithmConfiguration getAlgorithmConfiguration(String algorithm) {
        EvaluationStrategy evaluationStrategy;
        MigrationStrategy migrationStrategy;
        switch (algorithm) {
            case "HHCORandom":
                return new HHCORandomConfiguration();
            case "HHCOEpsilon":
                evaluationStrategy = new EvaluateAll(new ArgMaxSelection(), new EpsilonFIR(problem));
                migrationStrategy = new ShareOffspring();
                return new BaselineConfiguration(algorithm, evaluationStrategy, migrationStrategy);
            case "Roulette":
                evaluationStrategy = new EvaluateAll(new CastroRoulette(), new R2TchebycheffFIR(problem, populationSize));
                migrationStrategy = new ShareOffspring();
                return new BaselineConfiguration(algorithm, evaluationStrategy, migrationStrategy);
            case "EvalSelected":
                evaluationStrategy = new EvalSelected(new ArgMaxSelection(), new R2TchebycheffFIR(problem, populationSize));
                migrationStrategy = new ShareOffspring();
                return new BaselineConfiguration(algorithm, evaluationStrategy, migrationStrategy);
            case "SharePopulation":
                evaluationStrategy = new EvaluateAll(new ArgMaxSelection(), new R2TchebycheffFIR(problem, populationSize));
                migrationStrategy = new SharePopulation();
                return new BaselineConfiguration(algorithm, evaluationStrategy, migrationStrategy);
            default:
                evaluationStrategy = new EvaluateAll(new ArgMaxSelection(), new R2TchebycheffFIR(problem, populationSize));
                migrationStrategy = new ShareOffspring();
                return new BaselineConfiguration(algorithm, evaluationStrategy, migrationStrategy);
        }
    }
}
