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
package br.ufpr.inf.cbio.hhcoanalysis;

import br.ufpr.inf.cbio.hhco.hyperheuristic.HHCO.HHCOConfiguration;
import br.ufpr.inf.cbio.hhco.hyperheuristic.selection.ArgMaxSelection;
import br.ufpr.inf.cbio.hhco.metrics.fir.R2TchebycheffFIR;
import java.util.logging.Level;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalLogger;

/**
 *
 * @author Gian Fritsche <gmfritsche at inf.ufpr.br>
 */
public class GenericHHCOConfiguration extends HHCOConfiguration<Solution> {

    public enum FIRType {
        R2, EPSILON
    };
    private FIRType firtype;

    public GenericHHCOConfiguration(String name) {
        this(name, FIRType.R2);
    }

    public GenericHHCOConfiguration(String name, FIRType firtype) {
        super(name);
        this.firtype = firtype;
    }

    @Override
    public void setup() {
        this.selection = new ArgMaxSelection<>();
        switch (firtype) {
            case R2:
                this.fir = new R2TchebycheffFIR(problem, popSize);
                break;
            case EPSILON:
                this.fir = new EpsilonFIR(problem);
                break;
        }
        JMetalLogger.logger.log(Level.CONFIG, "FIR: {0}", fir.getClass().getSimpleName());
    }

}
