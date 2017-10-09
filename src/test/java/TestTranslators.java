import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.hycos.cnetwork.core.graph.ConstraintNetwork;
import com.github.hycos.cnetworkparser.core.InputFormat;
import com.github.hycos.cnetworktrans.core.OutputFormat;

import java.io.File;

public class TestTranslators {

    final static Logger LOGGER = LoggerFactory.getLogger(TestTranslators.class);


    private static ConstraintNetwork cn = null;

    private String getPath(String f){
        ClassLoader classLoader = getClass().getClassLoader();
        File sfile = new File(classLoader.getResource(f).getFile());
        return sfile.getAbsolutePath();
    }

    @Test
    public void testKaluza() {
        //String trans0 = Polyglot.translate(getPath("kaluza1.z3"), "/tmp/kaluza1.sol", InputFormat.Z3STR2, OutputFormat.SOL);
        String trans1 = Polyglot.translate(getPath("kaluza3.z3"), "/tmp/kaluza3.sol", InputFormat.Z3STR2, OutputFormat.SOL);
    }

    @Test
    public void testCVC() {
        //String trans0 = Polyglot.translate(getPath("kaluza1.z3"), "/tmp/kaluza1.sol", InputFormat.Z3STR2, OutputFormat.SOL);
        String trans1 = Polyglot.translate(getPath("kaluza3.z3"), "/tmp/kaluza3.cvc", InputFormat.Z3STR2, OutputFormat.CVC4);
        LOGGER.info(trans1);
    }

    @Test
    public void testZ32CVC() {
        //String trans0 = Polyglot.translate(getPath("kaluza1.z3"), "/tmp/kaluza1.sol", InputFormat.Z3STR2, OutputFormat.SOL);
        String trans1 = Polyglot.translate(getPath("beasties10.z3"),
                "/tmp/kaluza3.cvc", InputFormat.Z3STR2, OutputFormat.CVC4);
        LOGGER.info(trans1);
    }


}


