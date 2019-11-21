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

import br.ufpr.inf.cbio.hhco.hyperheuristic.CooperativeAlgorithm;
import br.ufpr.inf.cbio.hhco.hyperheuristic.selection.SelectionFunction;
import br.ufpr.inf.cbio.hhco.metrics.fir.FitnessImprovementRateCalculator;
import br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.HHcMOEA.HHcMOEA;
import br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.baseline.evaluation.EvaluationStrategy;
import br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.baseline.migration.MigrationStrategy;
import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;

/**
 *
 * @author Gian Fritsche <gmfritsche at inf.ufpr.br>
 * @param <S>
 */
public class Baseline<S extends Solution<?>> extends HHcMOEA<S> {

    private final MigrationStrategy migration;
    private final EvaluationStrategy evaluation;

    public Baseline(List<CooperativeAlgorithm<S>> algorithms, int populationSize,
            int maxEvaluations, Problem problem, String name, SelectionFunction<CooperativeAlgorithm> selection,
            FitnessImprovementRateCalculator fir, MigrationStrategy migration, EvaluationStrategy evaluation) {

        super(algorithms, populationSize, maxEvaluations, problem, name, selection, fir);

        this.migration = migration;
        this.evaluation = evaluation;
    }

    /**
     * @TODO Parameterize 2. evaluation: 2.1 the new population vs parent
     * population of the selected MOEA; 2.2 the offspring vs parents of the
     * selected MOEAs.
     */
    @Override
    public void run() {

        evaluations = 0;
        for (CooperativeAlgorithm alg : algorithms) {
            alg.init(populationSize);
            evaluations += alg.getPopulation().size();
            selection.add(alg);
        }
        selection.init();

        while (evaluations < maxEvaluations) {

            // heuristic selection
            selected = selection.getNext(evaluations / populationSize);

            // apply selected heuristic
            List<S> parents = new ArrayList<>();
            for (S s : selected.getPopulation()) {
                parents.add((S) s.copy());
            }
            selected.doIteration();

            {// increment evaluations
                evaluations += selected.getOffspring().size();
            }
            {// compute reward
                setFir(evaluation.evaluate(calculator, selected, parents));
                selection.creditAssignment(getFir());
            }
            {// move acceptance
                // ALL MOVES
            }
            {// cooperation phase
                migration.migrate(algorithms, selected);
            }
            
            setChanged();
            notifyObservers();
        }

    }

}
