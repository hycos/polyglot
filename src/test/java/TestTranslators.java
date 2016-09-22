import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snt.cnetwork.core.ConstraintNetwork;
import org.snt.cnetworkparser.core.InputFormat;
import org.snt.cnetworktrans.core.OutputFormat;

import java.io.File;

public class TestTranslators {

    final static Logger logger = LoggerFactory.getLogger(TestTranslators.class);


    private static ConstraintNetwork cn = null;

    private String getPath(String f){
        ClassLoader classLoader = getClass().getClassLoader();
        File sfile = new File(classLoader.getResource(f).getFile());
        return sfile.getAbsolutePath();
    }

    @Test
    public void testKaluza() {

        String trans = Polyglot.translate(getPath("kaluza1.z3"), "/tmp/kaluza1.sol", InputFormat.Z3STR2, OutputFormat.SOL);

        logger.info(trans);
    }

}


