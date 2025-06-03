package com.inhyung.fwp;

import java.util.List;

import java.util.List;

public class StageMap {
    private List<StageNode> nodes;
    private int currentNodeId;

    public StageMap(List<StageNode> nodes, int startNodeId) {
        this.nodes = nodes;
        this.currentNodeId = startNodeId;
    }

    public StageNode getCurrentNode() {
        for (StageNode node : nodes)
            if (node.getId() == currentNodeId)
                return node;
        return null;
    }

    public List<StageNode> getNodes() { return nodes; }

    public void moveTo(int nodeId) {
        this.currentNodeId = nodeId;
    }

    public boolean isBossCleared() {
        for (StageNode node : nodes)
            if (node.getType() == StageNode.Type.BOSS && node.isCleared())
                return true;
        return false;
    }
}

