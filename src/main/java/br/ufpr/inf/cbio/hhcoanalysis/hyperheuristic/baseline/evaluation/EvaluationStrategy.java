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

/**
 *
 * @author Gian Fritsche <gmfritsche at inf.ufpr.br>
 */
public abstract class EvaluationStrategy {
    
    protected final SelectionFunction<CooperativeAlgorithm> selection;
    protected final FitnessImprovementRateCalculator calculator;

    public EvaluationStrategy(SelectionFunction<CooperativeAlgorithm> selection, FitnessImprovementRateCalculator calculator) {
        this.selection = selection;
        this.calculator = calculator;
    }
    
    public abstract void copyPopulation(Baseline hyperheuristic);
    
    public abstract void creditAssignment(Baseline hyperheuristic);    

    public SelectionFunction<CooperativeAlgorithm> getSelection() {
        return selection;
    }
    
    
    
}
