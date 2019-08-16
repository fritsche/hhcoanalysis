
import br.ufpr.inf.cbio.hhco.hyperheuristic.HHCO.HHCO;
import br.ufpr.inf.cbio.hhco.hyperheuristic.HHCO.observer.HHCOLogger;
import br.ufpr.inf.cbio.hhcoanalysis.util.SolutionListUtils;
import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.solution.Solution;

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
/**
 *
 * @author Gian Fritsche <gmfritsche at inf.ufpr.br>
 * @param <S>
 */
public class HVLogger<S extends Solution<?>> extends HHCOLogger {

    private List<S> repository;

    public HVLogger(String folder, String file) {
        super(folder, file);
        repository = new ArrayList<>();
    }

    @Override
    public void update(HHCO hhco) {
        updateRepository(hhco.getSelected().getOffspring());
        /**
         * @TODO Compute hypervolume
         */
        /**
         * @TODO Print to file
         */
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void updateRepository(List<S> offspring) {
        offspring.forEach((s) -> {
            repository.add((S) s.copy());
        });
        repository = SolutionListUtils.removeRepeatedSolutions(repository);
        repository = SolutionListUtils.getNondominatedSolutions(repository);
    }

}
