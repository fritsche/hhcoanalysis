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

import static br.ufpr.inf.cbio.hhco.runner.Main.help;
import br.ufpr.inf.cbio.hhco.runner.methodology.MaFMethodology;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.uma.jmetal.util.JMetalLogger;

/**
 *
 * @author Gian Fritsche <gmfritsche at inf.ufpr.br>
 */
public class Main extends br.ufpr.inf.cbio.hhco.runner.Main {

    public static CommandLine parse(String[] args) {

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        Options options = new Options();

        try {
            options.addOption(Option.builder("h").longOpt("help").desc("print this message and exits.").build());
            options.addOption(Option.builder("P").longOpt("output-path").hasArg().argName("path")
                    .desc("directory path for output (if no path is given experiment/ will be used.)").build());
            options.addOption(Option.builder("M").longOpt("methodology").hasArg().argName("methodology")
                    .desc("set the methodology to be used: NSGAIIIMethodology (default), MaFMethodology, ArionMethodology.").build());
            options.addOption(Option.builder("id").hasArg().argName("id")
                    .desc("set the independent run id, default 0.").build());
            options.addOption(Option.builder("s").longOpt("seed").hasArg().argName("seed")
                    .desc("set the seed for JMetalRandom, default System.currentTimeMillis()").build());
            options.addOption(Option.builder("a").longOpt("algorithm").hasArg().argName("algorithm")
                    .desc("set the algorithm to be executed: [HHCOR2|HHCOEpsilon|HHCORandom]").build());
            options.addOption(Option.builder("p").longOpt("problem").hasArg().argName("problem")
                    .desc("set the problem instance: DTLZ[1-7], WFG[1-9], MinusDTLZ[1-7], MinusWFG[1-9], MaF[01-15]; default is WFG1."
                            + "<methodology> must be set accordingly.").build());
            options.addOption(Option.builder("m").longOpt("objectives").hasArg().argName("objectives")
                    .desc("set the number of objectives to <objectives> (default value is 3). <problem> and <methodology> must be set acordingly.").build());
            options.addOption(Option.builder("A").longOpt("analysis").hasArg().argName("flag")
                    .desc("toggle analysis [ON|OFF] (default OFF)").build());

            // parse command line
            cmd = parser.parse(options, args);
            // print help and exit
            if (cmd.hasOption("h") || args.length == 0) {
                help(options);
                System.exit(0);
            }
            return cmd;
        } catch (ParseException ex) {
            Logger.getLogger(br.ufpr.inf.cbio.hhco.runner.Main.class.getName()).log(Level.SEVERE,
                    "Failed to parse command line arguments. Execute with -h for usage help.", ex);
        }
        return null;
    }

    public static Runner getRunner(CommandLine cmd) {
        String problemName = "MaF02", aux;
        int m = 5;
        long seed = System.currentTimeMillis();
        String experimentBaseDirectory = "experiment/";
        String methodologyName = MaFMethodology.class.getSimpleName();
        int id = 0;
        String algorithmName = "HHCO";
        String analysis = "OFF";

        Runner runner = new Runner(false);

        if ((aux = cmd.getOptionValue("a")) != null) {
            algorithmName = aux;
        }
        if ((aux = cmd.getOptionValue("analysis")) != null) {
            analysis = aux;
        }
        if ((aux = cmd.getOptionValue("p")) != null) {
            problemName = aux;
        }
        if ((aux = cmd.getOptionValue("P")) != null) {
            experimentBaseDirectory = aux;
        }
        if ((aux = cmd.getOptionValue("M")) != null) {
            methodologyName = aux;
        }
        if ((aux = cmd.getOptionValue("m")) != null) {
            m = Integer.parseInt(aux);
        }
        if ((aux = cmd.getOptionValue("id")) != null) {
            id = Integer.parseInt(aux);
        }
        if ((aux = cmd.getOptionValue("s")) != null) {
            seed = Long.parseLong(aux);
        }

        runner.setAlgorithmName(algorithmName);
        runner.setProblemName(problemName);
        runner.setObjectives(m);
        runner.setExperimentBaseDirectory(experimentBaseDirectory);
        runner.setId(id);
        runner.setMethodologyName(methodologyName);
        runner.setSeed(seed);
        runner.toggleAnalysis(analysis);
        return runner;

    }

    public static void main(String[] args) {
        JMetalLogger.logger.setLevel(Level.CONFIG);
        Runner runner = getRunner(parse(args));
        runner.setFactory(new HHCOFactory());
        runner.run();
        runner.printResult();
    }
}
