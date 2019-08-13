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
package br.ufpr.inf.cbio.hhcoanalysis.prune;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ1;
import org.uma.jmetal.solution.Solution;

/**
 *
 * @author Gian Fritsche <gmfritsche at inf.ufpr.br>
 */
public class LpNormDistanceBetweenSolutionsInObjectiveSpaceTest {

    public LpNormDistanceBetweenSolutionsInObjectiveSpaceTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getDistance method, of class
     * LpNormDistanceBetweenSolutionsInObjectiveSpace.
     */
    @org.junit.jupiter.api.Test
    public void testGetEuclideanDistance() {
        System.out.println("getEuclideanDistance");
        DTLZ1 p = new DTLZ1(1, 2);
        Solution solution1 = p.createSolution();
        Solution solution2 = p.createSolution();
        solution1.setObjective(0, 1);
        solution1.setObjective(1, 4);
        solution2.setObjective(0, 5);
        solution2.setObjective(1, 1);
        LpNormDistanceBetweenSolutionsInObjectiveSpace instance = new LpNormDistanceBetweenSolutionsInObjectiveSpace(2, 2);
        double expResult = 5.0;
        double result = instance.getDistance(solution1, solution2);
        assertEquals(expResult, result, 10e-6);
    }

    /**
     * Test of getDistance method, of class
     * LpNormDistanceBetweenSolutionsInObjectiveSpace.
     */
    @org.junit.jupiter.api.Test
    public void testGetManhattanDistance() {
        System.out.println("getManhattanDistance");
        DTLZ1 p = new DTLZ1(1, 2);
        Solution solution1 = p.createSolution();
        Solution solution2 = p.createSolution();
        solution1.setObjective(0, 1);
        solution1.setObjective(1, 4);
        solution2.setObjective(0, 5);
        solution2.setObjective(1, 1);
        LpNormDistanceBetweenSolutionsInObjectiveSpace instance = new LpNormDistanceBetweenSolutionsInObjectiveSpace(1, 2);
        double expResult = 7.0;
        double result = instance.getDistance(solution1, solution2);
        assertEquals(expResult, result, 10e-6);
    }

}
