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
package br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.baseline.migration;

import br.ufpr.inf.cbio.hhco.hyperheuristic.CooperativeAlgorithm;
import br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.baseline.Baseline;
import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.solution.Solution;

/**
 *
 * @author Gian Fritsche <gmfritsche at inf.ufpr.br>
 * @param <S>
 */
public class SharePopulation<S extends Solution<?>> implements MigrationStrategy {

    @Override
    public void migrate(Baseline hyperheuristic) {
        // copy the solutions generatedy by selected
        List<S> population = new ArrayList<>();
        CooperativeAlgorithm<S> selected = hyperheuristic.getSelected();
        for (S s : selected.getPopulation()) {
            population.add((S) s.copy());
            // count evaluations used by selected
            hyperheuristic.setEvaluations(hyperheuristic.getEvaluations() + 1);
        }

        // cooperation phase
        List<CooperativeAlgorithm<S>> algorithms = hyperheuristic.getAlgorithms();
        for (CooperativeAlgorithm<S> neighbor : algorithms) {
            if (neighbor != selected) {
                List<S> migrants = new ArrayList<>();
                for (S s : population) {
                    migrants.add((S) s.copy());
                }
                neighbor.receive(migrants);
            }
        }
    }

}
