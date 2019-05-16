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

import java.util.logging.Level;
import org.uma.jmetal.util.JMetalLogger;

/**
 *
 * @author Gian Fritsche <gmfritsche at inf.ufpr.br>
 */
public class Main extends br.ufpr.inf.cbio.hhco.runner.Main {

    public static void main(String[] args) {
        JMetalLogger.logger.setLevel(Level.CONFIG);
        Runner runner = (Runner) getRunner(parse(args));
        runner.setFactory(new HHCOFactory());
        runner.run();
        runner.printResult();
    }
}
