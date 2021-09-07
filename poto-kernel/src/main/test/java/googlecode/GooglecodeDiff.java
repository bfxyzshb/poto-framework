package googlecode;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import difflib.*;
import model.*;

import java.util.Collections;
import java.util.List;

public class GooglecodeDiff {

    public static void main(String[] args) {
        Model working = new Model(new ModelId(1L), "name1", new Vo(new VoId(1L), "desc"), Lists.newArrayList(new Item(new ItemId(1L), "name")));
        Model base = new Model(new ModelId(1L), "name", new Vo(new VoId(1L),"desc"), Lists.newArrayList(new Item(new ItemId(1L), "name")));


        Patch<String> patch = DiffUtils.diff(Collections.singletonList(JSON.toJSONString(base)), Collections.singletonList(JSON.toJSONString(working)));

        DiffRowGenerator.Builder builder = new DiffRowGenerator.Builder().showInlineDiffs(false);
        DiffRowGenerator generator = builder.build();

        for (Object d : patch.getDeltas()) {
            Delta delta = (Delta) d;
            List<?> list = delta.getRevised().getLines();
            for (Object object : list) {
                System.out.println(object);
            }

            List<DiffRow> generateDiffRows = generator.generateDiffRows((List<String>) delta.getOriginal().getLines(), (List<String>) delta
                    .getRevised().getLines());
            int leftPos = delta.getOriginal().getPosition();
            int rightPos = delta.getRevised().getPosition();
            for (DiffRow row : generateDiffRows) {
                DiffRow.Tag tag = row.getTag();
                if (tag == DiffRow.Tag.INSERT) {
                    System.out.println("Insert: ");
                    System.out.println("new-> " + row.getNewLine());
                    System.out.println("");
                } else if (tag == DiffRow.Tag.CHANGE) {
                    System.out.println("change: ");
                    System.out.println("old-> " + row.getOldLine());
                    System.out.println("new-> " + row.getNewLine());
                    System.out.println("");
                } else if (tag == DiffRow.Tag.DELETE) {
                    System.out.println("delete: ");
                    System.out.println("old-> " + row.getOldLine());
                    System.out.println("");
                } else if (tag == DiffRow.Tag.EQUAL) {
                    System.out.println("equal: ");
                    System.out.println("old-> " + row.getOldLine());
                    System.out.println("new-> " + row.getNewLine());
                    System.out.println("");
                } else {
                    throw new IllegalStateException("Unknown pattern tag: " + tag);
                }
            }
        }
    }

}
