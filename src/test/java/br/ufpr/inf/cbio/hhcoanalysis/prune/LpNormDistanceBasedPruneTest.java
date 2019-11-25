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

import br.ufpr.inf.cbio.hhco.prune.LpNormDistanceBasedPrune;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ1;
import org.uma.jmetal.solution.Solution;

/**
 *
 * @author Gian Fritsche <gmfritsche at inf.ufpr.br>
 */
public class LpNormDistanceBasedPruneTest {
    
    public LpNormDistanceBasedPruneTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getSubsetOfEvenlyDistributedSolutions method, of class LpNormDistanceBasedPrune.
     */
    @Test
    public void testGetSubsetOfEvenlyDistributedSolutions() {
        System.out.println("getSubsetOfEvenlyDistributedSolutions");
        DTLZ1 p = new DTLZ1(1, 2);
        
        Solution solution1 = p.createSolution();
        Solution solution2 = p.createSolution();
        Solution solution3 = p.createSolution();
        Solution solution4 = p.createSolution();
        
        solution1.setObjective(0, 1); solution1.setObjective(1, 9);
        solution2.setObjective(0, 4); solution2.setObjective(1, 6);
        solution3.setObjective(0, 5); solution3.setObjective(1, 4);
        solution4.setObjective(0, 6); solution4.setObjective(1, 1);
        
        List<Solution> solutionList = new ArrayList<>(4);
        solutionList.add(solution1);
        solutionList.add(solution2);
        solutionList.add(solution3);
        solutionList.add(solution4);
        
        List<Solution> expResult = new ArrayList<>(3);
        expResult.add(solution1); // extreme obj1
        expResult.add(solution4); // extreme obj2
        expResult.add(solution2); // middle solution
        
        List result = LpNormDistanceBasedPrune.getSubsetOfEvenlyDistributedSolutions(solutionList, 3);
        assertEquals(expResult, result);
    }
    
}
