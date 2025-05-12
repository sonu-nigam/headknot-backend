package org.workflow.Config.Pebble.InlineSvg;

import io.pebbletemplates.pebble.extension.AbstractExtension;
import io.pebbletemplates.pebble.tokenParser.TokenParser;

import java.util.List;

public class InlineSvg extends AbstractExtension {

    @Override
    public List<TokenParser> getTokenParsers() {
        return List.of(new InlineSvgTag());
    }
}