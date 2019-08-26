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
package br.ufpr.inf.cbio.hhcoanalysis.problem;

import br.ufpr.inf.cbio.hhco.problem.MaF.MaF01;
import br.ufpr.inf.cbio.hhco.utils.MockRandomNumberGenerator;
import br.ufpr.inf.cbio.hhcoanalysis.util.SolutionListUtilsTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

/**
 *
 * @author Gian Fritsche <gmfritsche at inf.ufpr.br>
 */
public class HypervolumeImprovementTest {

    double[] samples;

    public HypervolumeImprovementTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        samples = new double[]{0.1, 0.1, 0.1, 0.1, 0.1,
            0.1, 0.1, 0.1, 0.1, 0.5,
            0.1, 0.1, 0.1, 0.1, 1.0,
            0.1, 0.1, 0.1, 0.5, 0.1,
            0.1, 0.1, 0.1, 0.5, 0.5,
            0.1, 0.1, 0.1, 0.5, 1.0,
            0.1, 0.1, 0.1, 1.0, 0.1,
            0.1, 0.1, 0.1, 1.0, 0.5,
            0.1, 0.1, 0.1, 1.0, 1.0,
            0.1, 0.1, 0.5, 0.1, 0.1,
            0.1, 0.1, 0.5, 0.1, 0.5,
            0.1, 0.1, 0.5, 0.1, 1.0,
            0.1, 0.1, 0.5, 0.5, 0.1,
            0.1, 0.1, 0.5, 0.5, 0.5,
            0.1, 0.1, 0.5, 0.5, 1.0,
            0.1, 0.1, 0.5, 1.0, 0.1,
            0.1, 0.1, 0.5, 1.0, 0.5,
            0.1, 0.1, 0.5, 1.0, 1.0,
            0.1, 0.1, 1.0, 0.1, 0.1,
            0.1, 0.1, 1.0, 0.1, 0.5,
            0.1, 0.1, 1.0, 0.1, 1.0,
            0.1, 0.1, 1.0, 0.5, 0.1,
            0.1, 0.1, 1.0, 0.5, 0.5,
            0.1, 0.1, 1.0, 0.5, 1.0,
            0.1, 0.1, 1.0, 1.0, 0.1,
            0.1, 0.1, 1.0, 1.0, 0.5,
            0.1, 0.1, 1.0, 1.0, 1.0,
            0.1, 0.5, 0.1, 0.1, 0.1,
            0.1, 0.5, 0.1, 0.1, 0.5,
            0.1, 0.5, 0.1, 0.1, 1.0,
            0.1, 0.5, 0.1, 0.5, 0.1,
            0.1, 0.5, 0.1, 0.5, 0.5,
            0.1, 0.5, 0.1, 0.5, 1.0,
            0.1, 0.5, 0.1, 1.0, 0.1,
            0.1, 0.5, 0.1, 1.0, 0.5,
            0.1, 0.5, 0.1, 1.0, 1.0,
            0.1, 0.5, 0.5, 0.1, 0.1,
            0.1, 0.5, 0.5, 0.1, 0.5,
            0.1, 0.5, 0.5, 0.1, 1.0,
            0.1, 0.5, 0.5, 0.5, 0.1,
            0.1, 0.5, 0.5, 0.5, 0.5,
            0.1, 0.5, 0.5, 0.5, 1.0,
            0.1, 0.5, 0.5, 1.0, 0.1,
            0.1, 0.5, 0.5, 1.0, 0.5,
            0.1, 0.5, 0.5, 1.0, 1.0,
            0.1, 0.5, 1.0, 0.1, 0.1,
            0.1, 0.5, 1.0, 0.1, 0.5,
            0.1, 0.5, 1.0, 0.1, 1.0,
            0.1, 0.5, 1.0, 0.5, 0.1,
            0.1, 0.5, 1.0, 0.5, 0.5,
            0.1, 0.5, 1.0, 0.5, 1.0,
            0.1, 0.5, 1.0, 1.0, 0.1,
            0.1, 0.5, 1.0, 1.0, 0.5,
            0.1, 0.5, 1.0, 1.0, 1.0,
            0.1, 1.0, 0.1, 0.1, 0.1,
            0.1, 1.0, 0.1, 0.1, 0.5,
            0.1, 1.0, 0.1, 0.1, 1.0,
            0.1, 1.0, 0.1, 0.5, 0.1,
            0.1, 1.0, 0.1, 0.5, 0.5,
            0.1, 1.0, 0.1, 0.5, 1.0,
            0.1, 1.0, 0.1, 1.0, 0.1,
            0.1, 1.0, 0.1, 1.0, 0.5,
            0.1, 1.0, 0.1, 1.0, 1.0,
            0.1, 1.0, 0.5, 0.1, 0.1,
            0.1, 1.0, 0.5, 0.1, 0.5,
            0.1, 1.0, 0.5, 0.1, 1.0,
            0.1, 1.0, 0.5, 0.5, 0.1,
            0.1, 1.0, 0.5, 0.5, 0.5,
            0.1, 1.0, 0.5, 0.5, 1.0,
            0.1, 1.0, 0.5, 1.0, 0.1,
            0.1, 1.0, 0.5, 1.0, 0.5,
            0.1, 1.0, 0.5, 1.0, 1.0,
            0.1, 1.0, 1.0, 0.1, 0.1,
            0.1, 1.0, 1.0, 0.1, 0.5,
            0.1, 1.0, 1.0, 0.1, 1.0,
            0.1, 1.0, 1.0, 0.5, 0.1,
            0.1, 1.0, 1.0, 0.5, 0.5,
            0.1, 1.0, 1.0, 0.5, 1.0,
            0.1, 1.0, 1.0, 1.0, 0.1,
            0.1, 1.0, 1.0, 1.0, 0.5,
            0.1, 1.0, 1.0, 1.0, 1.0,
            0.5, 0.1, 0.1, 0.1, 0.1,
            0.5, 0.1, 0.1, 0.1, 0.5,
            0.5, 0.1, 0.1, 0.1, 1.0,
            0.5, 0.1, 0.1, 0.5, 0.1,
            0.5, 0.1, 0.1, 0.5, 0.5,
            0.5, 0.1, 0.1, 0.5, 1.0,
            0.5, 0.1, 0.1, 1.0, 0.1,
            0.5, 0.1, 0.1, 1.0, 0.5,
            0.5, 0.1, 0.1, 1.0, 1.0,
            0.5, 0.1, 0.5, 0.1, 0.1,
            0.5, 0.1, 0.5, 0.1, 0.5,
            0.5, 0.1, 0.5, 0.1, 1.0,
            0.5, 0.1, 0.5, 0.5, 0.1,
            0.5, 0.1, 0.5, 0.5, 0.5,
            0.5, 0.1, 0.5, 0.5, 1.0,
            0.5, 0.1, 0.5, 1.0, 0.1,
            0.5, 0.1, 0.5, 1.0, 0.5,
            0.5, 0.1, 0.5, 1.0, 1.0,
            0.5, 0.1, 1.0, 0.1, 0.1,
            0.5, 0.1, 1.0, 0.1, 0.5,
            0.5, 0.1, 1.0, 0.1, 1.0,
            0.5, 0.1, 1.0, 0.5, 0.1,
            0.5, 0.1, 1.0, 0.5, 0.5,
            0.5, 0.1, 1.0, 0.5, 1.0,
            0.5, 0.1, 1.0, 1.0, 0.1,
            0.5, 0.1, 1.0, 1.0, 0.5,
            0.5, 0.1, 1.0, 1.0, 1.0,
            0.5, 0.5, 0.1, 0.1, 0.1,
            0.5, 0.5, 0.1, 0.1, 0.5,
            0.5, 0.5, 0.1, 0.1, 1.0,
            0.5, 0.5, 0.1, 0.5, 0.1,
            0.5, 0.5, 0.1, 0.5, 0.5,
            0.5, 0.5, 0.1, 0.5, 1.0,
            0.5, 0.5, 0.1, 1.0, 0.1,
            0.5, 0.5, 0.1, 1.0, 0.5,
            0.5, 0.5, 0.1, 1.0, 1.0,
            0.5, 0.5, 0.5, 0.1, 0.1,
            0.5, 0.5, 0.5, 0.1, 0.5,
            0.5, 0.5, 0.5, 0.1, 1.0,
            0.5, 0.5, 0.5, 0.5, 0.1,
            0.5, 0.5, 0.5, 0.5, 0.5,
            0.5, 0.5, 0.5, 0.5, 1.0,
            0.5, 0.5, 0.5, 1.0, 0.1,
            0.5, 0.5, 0.5, 1.0, 0.5,
            0.5, 0.5, 0.5, 1.0, 1.0,
            0.5, 0.5, 1.0, 0.1, 0.1,
            0.5, 0.5, 1.0, 0.1, 0.5,
            0.5, 0.5, 1.0, 0.1, 1.0,
            0.5, 0.5, 1.0, 0.5, 0.1,
            0.5, 0.5, 1.0, 0.5, 0.5,
            0.5, 0.5, 1.0, 0.5, 1.0,
            0.5, 0.5, 1.0, 1.0, 0.1,
            0.5, 0.5, 1.0, 1.0, 0.5,
            0.5, 0.5, 1.0, 1.0, 1.0,
            0.5, 1.0, 0.1, 0.1, 0.1,
            0.5, 1.0, 0.1, 0.1, 0.5,
            0.5, 1.0, 0.1, 0.1, 1.0,
            0.5, 1.0, 0.1, 0.5, 0.1,
            0.5, 1.0, 0.1, 0.5, 0.5,
            0.5, 1.0, 0.1, 0.5, 1.0,
            0.5, 1.0, 0.1, 1.0, 0.1,
            0.5, 1.0, 0.1, 1.0, 0.5,
            0.5, 1.0, 0.1, 1.0, 1.0,
            0.5, 1.0, 0.5, 0.1, 0.1,
            0.5, 1.0, 0.5, 0.1, 0.5,
            0.5, 1.0, 0.5, 0.1, 1.0,
            0.5, 1.0, 0.5, 0.5, 0.1,
            0.5, 1.0, 0.5, 0.5, 0.5,
            0.5, 1.0, 0.5, 0.5, 1.0,
            0.5, 1.0, 0.5, 1.0, 0.1,
            0.5, 1.0, 0.5, 1.0, 0.5,
            0.5, 1.0, 0.5, 1.0, 1.0,
            0.5, 1.0, 1.0, 0.1, 0.1,
            0.5, 1.0, 1.0, 0.1, 0.5,
            0.5, 1.0, 1.0, 0.1, 1.0,
            0.5, 1.0, 1.0, 0.5, 0.1,
            0.5, 1.0, 1.0, 0.5, 0.5,
            0.5, 1.0, 1.0, 0.5, 1.0,
            0.5, 1.0, 1.0, 1.0, 0.1,
            0.5, 1.0, 1.0, 1.0, 0.5,
            0.5, 1.0, 1.0, 1.0, 1.0,
            1.0, 0.1, 0.1, 0.1, 0.1,
            1.0, 0.1, 0.1, 0.1, 0.5,
            1.0, 0.1, 0.1, 0.1, 1.0,
            1.0, 0.1, 0.1, 0.5, 0.1,
            1.0, 0.1, 0.1, 0.5, 0.5,
            1.0, 0.1, 0.1, 0.5, 1.0,
            1.0, 0.1, 0.1, 1.0, 0.1,
            1.0, 0.1, 0.1, 1.0, 0.5,
            1.0, 0.1, 0.1, 1.0, 1.0,
            1.0, 0.1, 0.5, 0.1, 0.1,
            1.0, 0.1, 0.5, 0.1, 0.5,
            1.0, 0.1, 0.5, 0.1, 1.0,
            1.0, 0.1, 0.5, 0.5, 0.1,
            1.0, 0.1, 0.5, 0.5, 0.5,
            1.0, 0.1, 0.5, 0.5, 1.0,
            1.0, 0.1, 0.5, 1.0, 0.1,
            1.0, 0.1, 0.5, 1.0, 0.5,
            1.0, 0.1, 0.5, 1.0, 1.0,
            1.0, 0.1, 1.0, 0.1, 0.1,
            1.0, 0.1, 1.0, 0.1, 0.5,
            1.0, 0.1, 1.0, 0.1, 1.0,
            1.0, 0.1, 1.0, 0.5, 0.1,
            1.0, 0.1, 1.0, 0.5, 0.5,
            1.0, 0.1, 1.0, 0.5, 1.0,
            1.0, 0.1, 1.0, 1.0, 0.1,
            1.0, 0.1, 1.0, 1.0, 0.5,
            1.0, 0.1, 1.0, 1.0, 1.0,
            1.0, 0.5, 0.1, 0.1, 0.1,
            1.0, 0.5, 0.1, 0.1, 0.5,
            1.0, 0.5, 0.1, 0.1, 1.0,
            1.0, 0.5, 0.1, 0.5, 0.1,
            1.0, 0.5, 0.1, 0.5, 0.5,
            1.0, 0.5, 0.1, 0.5, 1.0,
            1.0, 0.5, 0.1, 1.0, 0.1,
            1.0, 0.5, 0.1, 1.0, 0.5,
            1.0, 0.5, 0.1, 1.0, 1.0,
            1.0, 0.5, 0.5, 0.1, 0.1,
            1.0, 0.5, 0.5, 0.1, 0.5,
            1.0, 0.5, 0.5, 0.1, 1.0,
            1.0, 0.5, 0.5, 0.5, 0.1,
            1.0, 0.5, 0.5, 0.5, 0.5,
            1.0, 0.5, 0.5, 0.5, 1.0,
            1.0, 0.5, 0.5, 1.0, 0.1,
            1.0, 0.5, 0.5, 1.0, 0.5,
            1.0, 0.5, 0.5, 1.0, 1.0,
            1.0, 0.5, 1.0, 0.1, 0.1,
            1.0, 0.5, 1.0, 0.1, 0.5,
            1.0, 0.5, 1.0, 0.1, 1.0,
            1.0, 0.5, 1.0, 0.5, 0.1,
            1.0, 0.5, 1.0, 0.5, 0.5,
            1.0, 0.5, 1.0, 0.5, 1.0,
            1.0, 0.5, 1.0, 1.0, 0.1,
            1.0, 0.5, 1.0, 1.0, 0.5,
            1.0, 0.5, 1.0, 1.0, 1.0,
            1.0, 1.0, 0.1, 0.1, 0.1,
            1.0, 1.0, 0.1, 0.1, 0.5,
            1.0, 1.0, 0.1, 0.1, 1.0,
            1.0, 1.0, 0.1, 0.5, 0.1,
            1.0, 1.0, 0.1, 0.5, 0.5,
            1.0, 1.0, 0.1, 0.5, 1.0,
            1.0, 1.0, 0.1, 1.0, 0.1,
            1.0, 1.0, 0.1, 1.0, 0.5,
            1.0, 1.0, 0.1, 1.0, 1.0,
            1.0, 1.0, 0.5, 0.1, 0.1,
            1.0, 1.0, 0.5, 0.1, 0.5,
            1.0, 1.0, 0.5, 0.1, 1.0,
            1.0, 1.0, 0.5, 0.5, 0.1,
            1.0, 1.0, 0.5, 0.5, 0.5,
            1.0, 1.0, 0.5, 0.5, 1.0,
            1.0, 1.0, 0.5, 1.0, 0.1,
            1.0, 1.0, 0.5, 1.0, 0.5,
            1.0, 1.0, 0.5, 1.0, 1.0,
            1.0, 1.0, 1.0, 0.1, 0.1,
            1.0, 1.0, 1.0, 0.1, 0.5,
            1.0, 1.0, 1.0, 0.1, 1.0,
            1.0, 1.0, 1.0, 0.5, 0.1,
            1.0, 1.0, 1.0, 0.5, 0.5,
            1.0, 1.0, 1.0, 0.5, 1.0,
            1.0, 1.0, 1.0, 1.0, 0.1,
            1.0, 1.0, 1.0, 1.0, 0.5,
            1.0, 1.0, 1.0, 1.0, 1.0};
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testUpdateHVSingleSolution() {
        System.out.println("evaluate a single solution");
        JMetalRandom.getInstance().setRandomGenerator(new MockRandomNumberGenerator(samples));
        int dimensions = 5;
        HypervolumeImprovement instance = new HypervolumeImprovement(new MaF01(1, dimensions), null, samples.length / dimensions);
        DoubleSolution solution = (DoubleSolution) SolutionListUtilsTest.createSolution(0.5, 0.5, 0.5, 0.5, 0.5);
        double actual = instance.updateHV(solution);
        double expected = Math.pow(2, dimensions) / ((double) samples.length / dimensions);
        assertEquals(expected, actual, 10e-6);
    }

    @Test
    public void testUpdateHVMultipleSolutions() {
        System.out.println("evaluate a single solution");
        JMetalRandom.getInstance().setRandomGenerator(new MockRandomNumberGenerator(samples));
        int dimensions = 5;
        HypervolumeImprovement instance = new HypervolumeImprovement(new MaF01(1, dimensions), null, samples.length / dimensions);

        DoubleSolution solution = (DoubleSolution) SolutionListUtilsTest.createSolution(0.5, 0.5, 0.5, 0.5, 0.5);
        double actual = instance.updateHV(solution);
        double expected = Math.pow(2, dimensions) / ((double) samples.length / dimensions);
        assertEquals(expected, actual, 10e-6);

        // dominated solution does not change hypervolume
        solution = (DoubleSolution) SolutionListUtilsTest.createSolution(0.9, 0.9, 0.9, 0.9, 0.9);
        actual = instance.updateHV(solution);
        assertEquals(expected, actual, 10e-6);
        
        // ideal solution dominates all samples
        solution = (DoubleSolution) SolutionListUtilsTest.createSolution(0.0, 0.0, 0.0, 0.0, 0.0);
        actual = instance.updateHV(solution);
        expected = 1.0;
        assertEquals(expected, actual, 10e-6);
        
    }

}
