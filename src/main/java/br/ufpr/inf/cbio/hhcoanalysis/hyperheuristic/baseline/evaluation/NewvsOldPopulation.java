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
import br.ufpr.inf.cbio.hhco.metrics.fir.FitnessImprovementRateCalculator;
import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.solution.Solution;

/**
 *
 * @author Gian Fritsche <gmfritsche at inf.ufpr.br>
 * @param <S>
 */
public class NewvsOldPopulation<S extends Solution<?>> implements EvaluationStrategy<S> {

    @Override
    public double evaluate(FitnessImprovementRateCalculator calculator, CooperativeAlgorithm<S> selected, List<S> parents) {
        List<S> newpop = new ArrayList<>();
        for (S s : selected.getPopulation()) {
            newpop.add((S) s.copy());
        }
        return calculator.computeFitnessImprovementRate(parents, newpop);
    }
}
