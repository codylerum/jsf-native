package com.outjected.jsf.foo.renderers;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.outjected.jsf.foo.components.Famlies;

@FacesRenderer(componentFamily = Famlies.OUTPUT_COMPONENT_FAMILY, rendererType = ButtonRenderer.RENDERER_TYPE)
public class ButtonRenderer extends RendererBase {

    public static final String RENDERER_TYPE = "com.outjected.jsf.renderers.ButtonRenderer";

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("button", component);
        writeAttributeIfExists("clientId", "id", context, component);
        writeAttributeIfExistsOrDefault("type", "type", "button", context, component);
        writeAttributeIfExists("style", "style", context, component);
        writeAttributeIfExists("styleClass", "class", context, component);
        writeAttributeIfExists("onclick", "onclick", context, component);

    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement("button");
    }

}
