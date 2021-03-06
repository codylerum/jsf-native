package com.outjected.jsf.renderers;

import java.io.IOException;
import java.util.Objects;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.outjected.jsf.components.Famlies;
import com.outjected.jsf.utils.RendererTools;
import com.sun.faces.facelets.compiler.UIInstructions;

@SuppressWarnings("resource")
@FacesRenderer(componentFamily = Famlies.OUTPUT_COMPONENT_FAMILY, rendererType = CardRenderer.RENDERER_TYPE)
public class CardRenderer extends RendererBase {

    public static final String RENDERER_TYPE = "com.outjected.jsf.renderers.CardRenderer";

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        component.getAttributes().put(WRITE_CLOSING_KEY, true);
        final UIComponent headerFacet = component.getFacet("header");
        final UIComponent footerFacet = component.getFacet("footer");

        final String header = (String) component.getAttributes().get("header");
        final String title = (String) component.getAttributes().get("title");
        final String subtitle = (String) component.getAttributes().get("subtitle");

        // Write Outer Div
        final String style = (String) component.getAttributes().get("style");
        final String styleClass = (String) component.getAttributes().get("styleClass");
        final String divComputedStyleClass = RendererTools.spaceSeperateStrings("card", styleClass);
        writer.startElement("div", component); // Outer Div
        writeId(context, component);
        writeAttribute("class", divComputedStyleClass, context);
        writeAttribute("style", style, context);

        if (Objects.nonNull(header) && Objects.nonNull(headerFacet)) {
            throw new IllegalArgumentException("Cannot define both a top facet and a header");
        }

        if (Objects.nonNull(headerFacet) && headerFacet.isRendered()) {
            if (headerFacet instanceof UIInstructions || headerFacet.getChildren().stream().anyMatch(UIComponent::isRendered)) {
                writer.startElement("div", component);
                writer.writeAttribute("class", "card-header", null);
                headerFacet.encodeAll(context);
                writer.endElement("div");
            }
        }
        else if (Objects.nonNull(header)) {
            writer.startElement("h5", component);
            writer.writeAttribute("class", "card-header", null);
            writer.write(header);
            writer.endElement("h5");
        }

        writer.startElement("div", component); //
        writer.writeAttribute("class", "card-body", null);

        if (Objects.nonNull(title)) {
            writer.startElement("h6", component);
            writer.writeAttribute("class", "card-title", null);
            writer.write(title);
            writer.endElement("h6");
        }
        if (Objects.nonNull(subtitle)) {
            writer.startElement("h6", component);
            writer.writeAttribute("class", "card-subtitle mb-2 text-muted", null);
            writer.write(subtitle);
            writer.endElement("h6");
        }

        for (UIComponent child : component.getChildren()) {
            child.encodeAll(context);
        }

        writer.endElement("div");

        if (Objects.nonNull(footerFacet) && footerFacet.isRendered()) {
            if (footerFacet instanceof UIInstructions || footerFacet.getChildren().stream().anyMatch(UIComponent::isRendered)) {
                writer.startElement("div", component);
                writer.writeAttribute("class", "card-footer", null);
                footerFacet.encodeAll(context);
                writer.endElement("div");
            }
        }
    }

    @Override
    public void encodeChildren(FacesContext context, UIComponent component) {
        // Children are rendered manually in the encodeBegin so we don't want to render them twice
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        final boolean writeClosingDiv = (boolean) component.getAttributes().getOrDefault(WRITE_CLOSING_KEY, false);
        if (writeClosingDiv) {
            ResponseWriter writer = context.getResponseWriter();
            writer.endElement("div"); // Outer Div
        }
    }
}
