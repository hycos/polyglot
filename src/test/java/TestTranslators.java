/*
 * polyglot - translate constraints in between different formats
 * Copyright (C) 2017 Julian Thome <julian.thome.de@gmail.com>
 *
 * polyglot is licensed under the EUPL, Version 1.1 or – as soon
 * they will be approved by the European Commission - subsequent versions of the
 * EUPL (the "Licence"); You may not use this work except in compliance with the
 * Licence. You may obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/sites/default/files/eupl1.1.-licence-en_0.pdf
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the Licence is distributed on an "AS IS" basis, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied.  See the Licence for the
 * specific language governing permissions and limitations under the Licence.
 */


import com.github.hycos.cnetwork.core.graph.ConstraintNetwork;
import com.github.hycos.cnetworkparser.core.InputFormat;
import com.github.hycos.cnetworktrans.core.OutputFormat;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        //LOGGER.info(trans1);
    }

    @Test
    public void testZ32CVC() {
        //String trans0 = Polyglot.translate(getPath("kaluza1.z3"), "/tmp/kaluza1.sol", InputFormat.Z3STR2, OutputFormat.SOL);
        String trans1 = Polyglot.translate(getPath("beasties10.z3"),
                "/tmp/kaluza3.cvc", InputFormat.Z3STR2, OutputFormat.CVC4);
        //LOGGER.info(trans1);
    }


}


