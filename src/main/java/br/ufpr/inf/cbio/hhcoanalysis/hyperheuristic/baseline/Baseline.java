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
import br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.baseline.evaluation.EvaluationStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.SolutionListUtils;

/**
 *
 * @author Gian Fritsche <gmfritsche at inf.ufpr.br>
 * @param <S>
 */
public class Baseline<S extends Solution<?>> extends Observable implements Algorithm<List<S>> {

    protected final String name;
    protected final List<CooperativeAlgorithm<S>> algorithms;
    protected final int populationSize;
    protected final int maxEvaluations;
    protected final EvaluationStrategy evaluationStrategy;

    protected int evaluations;
    protected CooperativeAlgorithm<S> selected;

    public Baseline(String name, List<CooperativeAlgorithm<S>> algorithms, int populationSize, int maxEvaluations, EvaluationStrategy evaluationStrategy) {
        this.name = name;
        this.algorithms = algorithms;
        this.populationSize = populationSize;
        this.maxEvaluations = maxEvaluations;
        this.evaluationStrategy = evaluationStrategy;
        JMetalLogger.logger.log(Level.CONFIG, "Selection Function: {0}", evaluationStrategy.getSelection().getClass().getSimpleName());
        JMetalLogger.logger.log(Level.CONFIG, "Evaluation Strategy: {0}", evaluationStrategy.getClass().getSimpleName());
    }

    @Override
    public void run() {
        SelectionFunction<CooperativeAlgorithm> selection = evaluationStrategy.getSelection();
        setEvaluations(0);
        for (CooperativeAlgorithm<S> alg : getAlgorithms()) {
            alg.init(populationSize);
            setEvaluations(getEvaluations() + alg.getPopulation().size());
            selection.add(alg);
        }
        selection.init();
        while (getEvaluations() < getMaxEvaluations()) {

            JMetalLogger.logger.log(Level.FINE, "Progress: {0}",
                    String.format("%.2f%%", getEvaluations() / (double) getMaxEvaluations() * 100.0));

            // heuristic selection
            selected = selection.getNext(getEvaluations() / populationSize);

            // copy the population to evaluation
            evaluationStrategy.copyPopulation(this);

            // apply selected heuristic
            selected.doIteration();

            // copy the solutions generatedy by selected
            List<S> offspring = new ArrayList<>();
            for (S s : selected.getOffspring()) {
                offspring.add((S) s.copy());
                // count evaluations used by selected
                setEvaluations(getEvaluations() + 1);
            }

            // cooperation phase
            for (CooperativeAlgorithm<S> neighbor : getAlgorithms()) {
                if (neighbor != selected) {
                    List<S> migrants = new ArrayList<>();
                    for (S s : offspring) {
                        migrants.add((S) s.copy());
                    }
                    neighbor.receive(migrants);
                }
            }

            // credit assignment
            evaluationStrategy.creditAssignment(this);

            { // move acceptance
                // ALL MOVES
            }

            { // notify observers
                setChanged();
                notifyObservers();
            }
        }
    }

    public int getMaxEvaluations() {
        return maxEvaluations;
    }

    public int getEvaluations() {
        return evaluations;
    }

    public List<CooperativeAlgorithm<S>> getAlgorithms() {
        return algorithms;
    }

    public void setEvaluations(int evaluations) {
        this.evaluations = evaluations;
    }

    @Override
    public List<S> getResult() {
        List<S> union = new ArrayList<>();
        for (CooperativeAlgorithm alg : algorithms) {
            union.addAll(alg.getPopulation());
        }
        return SolutionListUtils.getNondominatedSolutions(union);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "Cooperative based Hyper-heuristics for Many-Objective Optimization";
    }

    public CooperativeAlgorithm<S> getSelected() {
        return selected;
    }

}
