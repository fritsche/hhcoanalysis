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

import br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.HHcMOEA.HHcMOEA;
import br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.HHcMOEA.HHcMOEABuilder;
import br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.baseline.evaluation.EvaluationStrategy;
import br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.baseline.evaluation.NewvsOldPopulation;
import br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.baseline.migration.MigrationStrategy;
import br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.baseline.migration.ShareOffspring;
import br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.baseline.migration.SharePopulation;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;

/**
 *
 * @author Gian Fritsche <gmfritsche at inf.ufpr.br>
 * @param <S>
 */
public class BaselineBuilder<S extends Solution<?>> extends HHcMOEABuilder<S> {

    
    private MigrationStrategy migration;
    private EvaluationStrategy evaluation;
    
    public BaselineBuilder(Problem problem) {
        super(problem);
        migration = new SharePopulation();
        evaluation = new NewvsOldPopulation();
    }

    @Override
    public Baseline build() {
        return new Baseline(algorithms, populationSize, maxEvaluations, problem, name,
                selection, fir, migration, evaluation);
    }

    public MigrationStrategy getMigration() {
        return migration;
    }

    public BaselineBuilder setMigration(MigrationStrategy migration) {
        this.migration = migration;
        return this;
    }

    public EvaluationStrategy getEvaluation() {
        return evaluation;
    }

    public BaselineBuilder setEvaluation(EvaluationStrategy evaluation) {
        this.evaluation = evaluation;
        return this;
    }

}
