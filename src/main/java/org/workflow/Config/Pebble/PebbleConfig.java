package org.workflow.Config.Pebble;

import io.pebbletemplates.pebble.extension.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.workflow.Config.Pebble.InlineSvg.InlineSvg;

@Configuration
public class PebbleConfig {
    @Bean
    public Extension myPebbleExtension1() {
        return new MyCustomFunction();
    }
    @Bean
    public Extension myPebbleExtension2() {
        return new InlineSvg();
    }
}
