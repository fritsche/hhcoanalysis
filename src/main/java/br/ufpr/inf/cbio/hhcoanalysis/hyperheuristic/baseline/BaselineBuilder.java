/*
 * Copyright (C) 2018 Gian Fritsche <gmfritsche@inf.ufpr.br>
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
import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.AlgorithmBuilder;
import br.ufpr.inf.cbio.hhco.metrics.fir.FitnessImprovementRateCalculator;
import br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.baseline.evaluation.EvaluationStrategy;
import br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.baseline.migration.MigrationStrategy;

/**
 *
 * @author Gian Fritsche <gmfritsche at inf.ufpr.br>
 * @param <S>
 */
public class BaselineBuilder<S extends Solution<?>> implements AlgorithmBuilder<Baseline<S>> {

    protected List<CooperativeAlgorithm<S>> algorithms;
    protected int populationSize;
    protected int maxEvaluations;
    protected final Problem problem;
    protected String name;
    protected FitnessImprovementRateCalculator fir;
    protected EvaluationStrategy evaluationStrategy;
    private MigrationStrategy migrationStrategy;

    public BaselineBuilder(Problem problem) {
        this.problem = problem;
        name = "Baseline"; // default name
    }

    public String getName() {
        return name;
    }

    public BaselineBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public List<CooperativeAlgorithm<S>> getAlgorithms() {
        return algorithms;
    }

    public BaselineBuilder setAlgorithms(List<CooperativeAlgorithm<S>> algorithms) {
        this.algorithms = algorithms;
        return this;
    }

    public BaselineBuilder addAlgorithm(CooperativeAlgorithm algorithm) {
        if (algorithms == null) {
            algorithms = new ArrayList<>();
        }
        algorithms.add(algorithm);
        return this;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public BaselineBuilder setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
        return this;
    }

    public int getMaxEvaluations() {
        return maxEvaluations;
    }

    public BaselineBuilder setMaxEvaluations(int maxEvaluations) {
        this.maxEvaluations = maxEvaluations;
        return this;
    }

    public FitnessImprovementRateCalculator getFir() {
        return fir;
    }

    public BaselineBuilder setFir(FitnessImprovementRateCalculator fir) {
        this.fir = fir;
        return this;
    }

    public EvaluationStrategy getEvaluationStrategy() {
        return evaluationStrategy;
    }

    public BaselineBuilder setEvaluationStrategy(EvaluationStrategy evaluationStrategy) {
        this.evaluationStrategy = evaluationStrategy;
        return this;
    }

    public BaselineBuilder setMigrationStrategy(MigrationStrategy migrationStrategy) {
        this.migrationStrategy = migrationStrategy;
        return this;
    }

    @Override
    public Baseline build() {
        return new Baseline<>(name, algorithms, populationSize, maxEvaluations, evaluationStrategy, migrationStrategy);
    }

}
