package top.yalexin.rblog.util.markdown;


import org.commonmark.node.Node;
import org.commonmark.renderer.NodeRenderer;

import java.util.Collections;
import java.util.Set;

abstract class HighlightNodeRenderer implements NodeRenderer {
    HighlightNodeRenderer(){}
    public Set<Class<? extends Node>> getNodeTypes() {
        return Collections.singleton(Highlight.class);
    }


}
