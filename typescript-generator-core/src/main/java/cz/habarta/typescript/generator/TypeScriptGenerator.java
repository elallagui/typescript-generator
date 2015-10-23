
package cz.habarta.typescript.generator;

import cz.habarta.typescript.generator.emitter.*;
import cz.habarta.typescript.generator.parser.*;
import java.io.File;
import java.util.*;
import java.util.logging.Logger;


public class TypeScriptGenerator {

    public static void generateTypeScript(List<? extends Class<?>> classes, Settings settings, File outputDeclarationFile) {
        final Logger logger = Logger.getGlobal();

        final ModelParser modelParser;
        if (settings.jsonLibrary == JsonLibrary.jackson2) {
            modelParser = new Jackson2Parser(logger, settings);
        } else {
            modelParser = new Jackson1Parser(logger, settings);
        }
        final Model model = modelParser.parseModel(classes);

        final ModelCompiler compiler = new ModelCompiler(settings);
        final TsModel tsModel = compiler.javaToTypescript(model);
        
        Emitter.emit(logger, settings, outputDeclarationFile, tsModel);
    }

}
