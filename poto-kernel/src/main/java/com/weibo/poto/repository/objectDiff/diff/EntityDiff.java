package com.weibo.poto.repository.objectDiff.diff;

import com.google.common.base.Splitter;
import com.weibo.poto.repository.objectDiff.domain.Entity;
import de.danielbechler.diff.node.DiffNode;
import de.danielbechler.diff.node.Visit;
import de.danielbechler.diff.path.NodePath;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EntityDiff {

    public static final EntityDiff EMPTY = new EntityDiff();
    /**
     * no changes
     */
    private boolean empty;
    private boolean selfModified;
    private DiffNode diff;
    private Object snapshot;
    private Object obj;

    public Diff getDiff(String propertyName) {
        final NodePath itemPath = NodePath.startBuilding().propertyName(propertyName).build();
        final DiffNode node = diff.getChild(itemPath);
        final Diff diffResult;
        if (List.class.isAssignableFrom(node.getValueType())) {
            final DiffList diff = new DiffList();
            node.visitChildren((DiffNode cnode, Visit cvisit) -> {
                if (!Entity.class.isAssignableFrom(cnode.getValueType())) {
                    return;
                }

                //cnode.getValueType().isPrimitive();
                //Primitives.isWrapperType()
                if (Splitter.on("/").trimResults().omitEmptyStrings().splitToList(cnode.getPath().toString()).size() > 1) {
                    return;
                }
                switch (cnode.getState()) {
                    case REMOVED:
                        diff.add(new DiffObject(DiffType.Removed, null, cnode.canonicalGet(snapshot)));
                        break;
                    case ADDED:
                        diff.add(new DiffObject(DiffType.Added, cnode.canonicalGet(obj), null));
                        break;
                    default:
                        break;
                }
            });

            final Map<Long, List<Diff>> collect = diff.stream()
                .collect(Collectors.groupingBy(d -> Objects.nonNull(d.getNewValue()) ? ((Entity) d.getNewValue()).getId() : ((Entity) d.getOldVlaue()).getId()));

            collect.forEach((k,v) -> {
                if (v.size() > 1) {
                    final Map<DiffType, List<Diff>> typeListMap = v.stream().collect(Collectors.groupingBy(Diff::getType));
                    diff.add(new DiffObject(DiffType.Modified, typeListMap.get(DiffType.Added).get(0).getNewValue(),
                        typeListMap.get(DiffType.Removed).get(0).getOldVlaue()));
                    diff.removeAll(v);
                }
            });
            diffResult = diff;
        } else {
            diffResult = new DiffObject();
        }
        return diffResult;
    }

}
