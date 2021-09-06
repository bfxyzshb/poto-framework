package com.weibo.poto.repository.objectDiff.diff;

import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.node.DiffNode;
import de.danielbechler.diff.node.DiffNode.State;
import de.danielbechler.diff.node.Visit;

import java.util.List;

public class DiffUtils {

    public static EntityDiff diff(Object snapshot, Object obj) {
        final DiffNode diff = ObjectDifferBuilder.buildDefault().compare(obj, snapshot);

        if (!diff.hasChanges()) {
            return EntityDiff.EMPTY;
        }

        final EntityDiff entityDiff = new EntityDiff(false, false, diff,snapshot,obj);
        diff.visit((DiffNode node, Visit visit) -> {
            if (!List.class.isAssignableFrom(node.getValueType()) && !node.isRootNode() && node.getState() == State.CHANGED && !entityDiff.isSelfModified()) {
                entityDiff.setSelfModified(true);
            }
            /*final Object snapshotValue = node.canonicalGet(snapshot);
            final String message = node.getState() + ", " + snapshotValue;
            System.out.println("======="+message);*/
        });

        return entityDiff;
    }

}
