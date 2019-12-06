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
package br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.baseline.evaluation;

import br.ufpr.inf.cbio.hhco.hyperheuristic.CooperativeAlgorithm;
import br.ufpr.inf.cbio.hhco.hyperheuristic.selection.SelectionFunction;
import br.ufpr.inf.cbio.hhco.metrics.fir.FitnessImprovementRateCalculator;
import br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.baseline.Baseline;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.uma.jmetal.solution.Solution;

/**
 *
 * @author Gian Fritsche <gmfritsche at inf.ufpr.br>
 * @param <S>
 */
public class EvaluateAll<S extends Solution<?>> extends EvaluationStrategy {

    private Map<CooperativeAlgorithm<S>, List<S>> populations;
    private ArrayList<Double> moeasfir;

    public EvaluateAll(SelectionFunction<CooperativeAlgorithm> selection, FitnessImprovementRateCalculator calculator) {
        super(selection, calculator);
    }

    @Override
    public void copyPopulation(Baseline hyperheuristic) {
        populations = new HashMap(hyperheuristic.getAlgorithms().size());
        List<CooperativeAlgorithm<S>> algorithms = hyperheuristic.getAlgorithms();
        for (CooperativeAlgorithm<S> algorithm : algorithms) {
            List<S> population = new ArrayList<>(algorithm.getPopulation().size());
            for (S s : algorithm.getPopulation()) {
                population.add((S) s.copy());
            }
            populations.put(algorithm, population);
        }
    }

    @Override
    public void creditAssignment(Baseline hyperheuristic) {
        List<CooperativeAlgorithm<S>> algorithms = hyperheuristic.getAlgorithms();
        moeasfir = new ArrayList<>(algorithms.size());
        int i = 0;
        for (CooperativeAlgorithm<S> algorithm : algorithms) {
            List<S> oldpop = populations.get(algorithm);
            List<S> newpop = algorithm.getPopulation();
            moeasfir.add(i, calculator.computeFitnessImprovementRate(oldpop, newpop));
            i++;
        }
        selection.creditAssignment(moeasfir);
    }

}
