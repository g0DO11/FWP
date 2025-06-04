package com.inhyung.fwp;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class StageMapLoader {

    public static List<StageMap> load(Context context) {
        List<StageMap> stageMaps = new ArrayList<>();

        try {
            InputStream is = context.getAssets().open("stagemap.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();

            String jsonStr = new String(buffer, StandardCharsets.UTF_8);
            JSONArray stageArray = new JSONArray(jsonStr);

            for (int i = 0; i < stageArray.length(); i++) {
                JSONObject stageObj = stageArray.getJSONObject(i);
                int stage = stageObj.getInt("stage");

                List<StageNode> nodes = new ArrayList<>();
                JSONArray nodeArray = stageObj.getJSONArray("nodes");
                for (int j = 0; j < nodeArray.length(); j++) {
                    JSONObject nodeObj = nodeArray.getJSONObject(j);
                    int id = nodeObj.getInt("id");
                    String typeStr = nodeObj.getString("type");
                    int x = nodeObj.getInt("x");
                    int y = nodeObj.getInt("y");

                    StageNode.Type type = StageNode.Type.valueOf(typeStr);
                    StageNode node = new StageNode(id, x, y, stage, type);
                    nodes.add(node);
                }

                JSONArray linkArray = stageObj.getJSONArray("links");
                for (int j = 0; j < linkArray.length(); j++) {
                    JSONObject linkObj = linkArray.getJSONObject(j);
                    int fromId = linkObj.getInt("from");
                    int toId = linkObj.getInt("to");

                    StageNode fromNode = findNodeById(nodes, fromId);
                    if (fromNode != null) {
                        fromNode.addNext(toId);  // 수정: ID 기반 연결
                    }
                }

                int startNodeId = stageObj.getInt("startNodeId");
                StageMap stageMap = new StageMap(nodes, startNodeId);
                stageMaps.add(stageMap);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return stageMaps;
    }

    private static StageNode findNodeById(List<StageNode> nodes, int id) {
        for (StageNode node : nodes) {
            if (node.getId() == id) return node;
        }
        return null;
    }
}
