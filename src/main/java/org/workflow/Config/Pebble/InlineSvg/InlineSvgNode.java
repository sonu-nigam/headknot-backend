package org.workflow.Config.Pebble.InlineSvg;

import io.pebbletemplates.pebble.extension.NodeVisitor;
import io.pebbletemplates.pebble.node.AbstractRenderableNode;
import io.pebbletemplates.pebble.node.expression.Expression;
import io.pebbletemplates.pebble.template.EvaluationContextImpl;
import io.pebbletemplates.pebble.template.PebbleTemplateImpl;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class InlineSvgNode extends AbstractRenderableNode {

    private final Expression<?> inlineSvgExpression;

    public InlineSvgNode(int lineNumber, Expression inlineSvgExpression) {
        super(lineNumber);
        this.inlineSvgExpression = inlineSvgExpression;
    }

    @Override
    public void render(PebbleTemplateImpl self, Writer writer, EvaluationContextImpl context) throws IOException {
        String filePath = this.inlineSvgExpression.evaluate(self, context).toString();
        try {
            String svgContent = this.readFile("/static/icons/" + filePath + ".svg");
            writer.write(svgContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readFile(String fileName) throws IOException {
        ClassPathResource resource = new ClassPathResource(fileName);
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public Expression<?> getInlineSvgExpression() {
        return this.inlineSvgExpression;
    }
}
