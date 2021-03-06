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
package br.ufpr.inf.cbio.hhcoanalysis.util;

import br.ufpr.inf.cbio.hhco.util.SolutionListUtils;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ1;
import org.uma.jmetal.solution.Solution;

/**
 *
 * @author Gian Fritsche <gmfritsche at inf.ufpr.br>
 */
public class SolutionListUtilsTest {
  
    
    public SolutionListUtilsTest() {
    }
    
    public static Solution createSolution(double... objectiveValues) {
        
        DTLZ1 p = new DTLZ1(1, objectiveValues.length);

        Solution s = p.createSolution();

        for (int i = 0; i < objectiveValues.length; i++) {
            s.setObjective(i, objectiveValues[i]);
        }

        return s;
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void sholdThrowIllegalArgumentExceptionWhenSolution1IsNull() {
        SolutionListUtils.isEqual(null, createSolution(0.1, 0.2, 0.3));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void sholdThrowIllegalArgumentExceptionWhenSolution2IsNull() {
        SolutionListUtils.isEqual(createSolution(0.1, 0.2, 0.3), null);
    }
    
    @Test
    public void shouldReturnTheCorrectNonRepeatedSolutions() {
        
        List<Solution<?>> population = Arrays.asList(
            createSolution(0.1, 0.2, 0.3),
            createSolution(0.1, 0.2, 0.3),
            createSolution(0.3, 0.4, 0.5),
            createSolution(0.3, 0.4, 0.5)
        );
        
        List<Solution<?>> newPopulation = SolutionListUtils.removeRepeatedSolutions(population);
        
        assertEquals(2, newPopulation.size());
        assertTrue(SolutionListUtils.contains(newPopulation, createSolution(0.1, 0.2, 0.3)));
        assertTrue(SolutionListUtils.contains(newPopulation, createSolution(0.3, 0.4, 0.5)));
    }
    
    @Test
    public void shouldReturnTrueWhenThePopulationContainsTheSolution() {
        
        List<Solution<?>> population = Arrays.asList(
            createSolution(0.1, 0.2, 0.3),
            createSolution(0.2, 0.3, 0.4),
            createSolution(0.3, 0.4, 0.5)
        );
        
        Solution s = createSolution(0.1, 0.2, 0.3);
        
        assertTrue(SolutionListUtils.contains(population, s));
    }
    
    @Test
    public void shouldReturnFalseWhenThePopulationDoesNotContainTheSolution() {
        
        List<Solution<?>> population = Arrays.asList(
            createSolution(0.1, 0.2, 0.3),
            createSolution(0.2, 0.3, 0.4),
            createSolution(0.3, 0.4, 0.5)
        );
        
        Solution s = createSolution(0.3, 0.2, 0.3);
        
        assertFalse(SolutionListUtils.contains(population, s));
    }

    @Test
    public void shouldReturnTrueWhenTwoSolutionsAreEqual() {
        
        Solution s1 = createSolution(0.1, 0.2, 0.3);
        Solution s2 = createSolution(0.1, 0.2, 0.3);
        
        assertTrue(SolutionListUtils.isEqual(s1, s2));
    }
    
    @Test
    public void shouldReturnFalseWhenTwoSolutionsAreDifferent() {
        
        Solution s1 = createSolution(0.1, 0.2, 0.3);
        Solution s2 = createSolution(0.1, 0.2, 0.5);
        
        assertFalse(SolutionListUtils.isEqual(s1, s2));
    }
    
   
}
