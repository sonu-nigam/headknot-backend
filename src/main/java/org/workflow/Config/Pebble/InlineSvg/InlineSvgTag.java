package org.workflow.Config.Pebble.InlineSvg;

import io.pebbletemplates.pebble.lexer.Token;
import io.pebbletemplates.pebble.lexer.TokenStream;
import io.pebbletemplates.pebble.node.RenderableNode;
import io.pebbletemplates.pebble.node.expression.Expression;
import io.pebbletemplates.pebble.tokenParser.TokenParser;
import io.pebbletemplates.pebble.parser.Parser;

public class InlineSvgTag implements TokenParser {

    @Override
    public String getTag() {
        return "inlineSvg";
    }

    @Override
    public RenderableNode parse(Token token, Parser parser) {
        TokenStream stream = parser.getStream();
        int lineNumber = token.getLineNumber();

        // skip over the 'inlineSvg' token
        stream.next();
        Expression<?> inlineSvgExpression = parser.getExpressionParser().parseExpression();
        // Parse the arguments for the tag, e.g., file path

        stream.expect(Token.Type.EXECUTE_END);
        return new InlineSvgNode(lineNumber, inlineSvgExpression);
    }
}
