package org.workflow.Config.Pebble;

import io.pebbletemplates.pebble.extension.AbstractExtension;
import io.pebbletemplates.pebble.extension.Function;
import io.pebbletemplates.pebble.template.EvaluationContext;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MyCustomFunction extends AbstractExtension {
    @Override
    public Map<String, Object> getGlobalVariables() {
        return Collections.singletonMap("now", new Date());
    }

    @Override
    public Map<String, Function> getFunctions() {
        return Collections.singletonMap("capitalize", new Function() {
            @Autowired
            private ResourceLoader resourceLoader;

            @Override
            public List<String> getArgumentNames() {
                return List.of("path");
            }

            @Override
            public Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
                if (!args.containsKey("path")) {
                    throw new IllegalArgumentException("Missing argument 'path'");
                }
                String path = "static/icons/" + (String) args.get("path") + ".svg";

                try {
                    String file = this.readFile(path);
                    return file;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String readFile(String fileName) throws IOException {
                ClassPathResource resource = new ClassPathResource(fileName);
                return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            }
        });
    }
}