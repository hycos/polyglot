import org.apache.commons.cli.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snt.cnetwork.core.ConstraintNetwork;
import org.snt.cnetworkparser.core.ConstraintNetworkParser;
import org.snt.cnetworkparser.core.InputFormat;
import org.snt.cnetworktrans.core.OutputFormat;
import org.snt.cnetworktrans.exceptions.NotSupportedException;
import org.snt.cnetworktrans.lang.SmtTranslator;

import java.io.File;
import java.io.IOException;

public class Polyglot {

    final static Logger LOGGER = LoggerFactory.getLogger(Polyglot.class);

    public static void main (String [] args) {

        HelpFormatter hformatter = new HelpFormatter();


        Options options = new Options();

        // Binary arguments
        options.addOption("h", "print this message");

        // Option arguments
        Option ifrm = Option.builder("ifrm")
                .longOpt("input-format")
                .numberOfArgs(1)
                .valueSeparator(' ')
                .desc("input format " + InputFormat.getAvailableFormats())
                .argName("ifrm").valueSeparator()
                .required(true)
                .build();

        Option ofrm  = Option.builder("ofrm")
                .longOpt("output-format")
                .numberOfArgs(1)
                .valueSeparator(' ')
                .desc("output format " + OutputFormat.getAvailableFormats())
                .argName("ofrm").valueSeparator()
                .required(true)
                .build();

        Option ifil = Option.builder("ifil")
                .longOpt("input-file")
                .numberOfArgs(1)
                .valueSeparator(' ')
                .desc("input file")
                .argName("ifil").valueSeparator()
                .required(true)
                .build();


        Option ofil = Option.builder("ofil")
                .longOpt("output-file")
                .numberOfArgs(1)
                .valueSeparator(' ')
                .desc("output file")
                .argName("ofil").valueSeparator()
                .required(false)
                .build();

        options.addOption(ifrm);
        options.addOption(ofrm);
        options.addOption(ifil);
        options.addOption(ofil);

        CommandLineParser parser = new DefaultParser();

        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption('h')) {
                hformatter.printHelp("java -jar org.snt.polyglott.Polyglott.jar", options);
                System.exit(0);
            }
        } catch (ParseException e) {
            hformatter.printHelp("java -jar org.snt.polyglott.Polyglott.jar", options);
            System.err.println("Wrong command line argument combination " + e.getMessage());
            System.exit(-1);
        }


        String inputFile = cmd.getOptionValue("ifil");
        String outputFile = cmd.getOptionValue("ofil");
        String inputFormat = cmd.getOptionValue("ifrm");
        String outputFormat = cmd.getOptionValue("ofrm");

        if(!new File(inputFile).exists()) {
            System.err.println("Input file " + inputFile + " does not exist");
            System.exit(-1);
        }

        if(cmd.hasOption("ofil") && !new File(outputFile).exists()) {
            System.err.println("Output Ffile " + outputFile + " does already exist");
            System.exit(-1);
        }

        if(InputFormat.getFormat(inputFormat) == null || OutputFormat.getFormat(outputFormat) == null) {
            System.err.println("Wrong inut or output format specified. Supported input f ormats are " +
                    InputFormat.getAvailableFormats() + " supported output formats are " + OutputFormat.getAvailableFormats());
            System.exit(-1);
        }

        InputFormat informat = InputFormat.getFormat(inputFormat);
        OutputFormat oformat = OutputFormat.getFormat(outputFormat);


        String out = translate(inputFile, outputFile, informat, oformat);

        assert(out != null);

        System.out.println(out);

    }

    public static String translate(String inputFile, String outputFile, InputFormat informat, OutputFormat oformat) {


        System.out.println("Get constraint network parser for " + informat.getName() + " ...");
        ConstraintNetworkParser cparser = new ConstraintNetworkParser(informat);


        if(cparser == null) {
            System.err.print("Could not get parser for " + informat.toString());
            System.exit(-1);
        }
        System.out.println(" ... done");

        System.out.println("Get constraint network for file " + inputFile + " ...");

        ConstraintNetwork cn = cparser.getConstraintNetworkFromFile(inputFile);

        System.out.println("... done");


        SmtTranslator translator = oformat.getTranslator();

        assert(translator != null);

        try {
            translator.setConstraintNetworkBuilder(cn);
        } catch (NotSupportedException e) {
            System.err.print(e.getMessage());
            System.exit(-1);
        }

        String outputString = null;
        try {
            outputString = translator.translate();
        } catch (NotSupportedException e) {
            System.err.print(e.getMessage());
            System.exit(-1);
        }

        assert(outputString != null);
        if(outputFile != null && !outputFile.isEmpty()) {
            try {
                FileUtils.writeStringToFile(new File(outputFile), outputString,
                        "UTF-8", true);
            } catch (IOException e) {
                System.err.print("Could not write output to " + outputFile);
                System.exit(-1);
            }
        }

        if(outputString != null)
            return outputString;
        else
            return null;
    }

}
