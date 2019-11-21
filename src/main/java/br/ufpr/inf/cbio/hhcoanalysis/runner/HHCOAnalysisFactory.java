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
package br.ufpr.inf.cbio.hhcoanalysis.runner;

import br.ufpr.inf.cbio.hhco.config.AlgorithmConfiguration;
import br.ufpr.inf.cbio.hhco.config.AlgorithmConfigurationFactory;
import br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.HHcMOEA;
import br.ufpr.inf.cbio.hhcoanalysis.hyperheuristic.HHcMOEAConfiguration;

/**
 *
 * @author Gian Fritsche <gmfritsche at inf.ufpr.br>
 */
public class HHCOAnalysisFactory extends AlgorithmConfigurationFactory {

    @Override
    public AlgorithmConfiguration getAlgorithmConfiguration(String algorithm) {
        if (algorithm.equals(HHcMOEA.class.getSimpleName())) {
            return new HHcMOEAConfiguration(algorithm);
        }
        return super.getAlgorithmConfiguration(algorithm);
    }

}
