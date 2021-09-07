package javer;

import com.google.common.collect.Lists;
import model.*;
import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.changetype.InitialValueChange;
import org.javers.core.diff.changetype.ObjectRemoved;
import org.javers.core.diff.changetype.PropertyChange;
import org.javers.core.diff.changetype.ValueChange;
import org.javers.core.diff.changetype.container.ListChange;
import org.javers.core.diff.changetype.container.ValueRemoved;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

import static org.javers.core.diff.ListCompareAlgorithm.*;

public class Diff {

    public static void main(String[] args) {
        Model working = new Model(new ModelId(1L), "name1111", new Vo(new VoId(1L), "desc"), Lists.newArrayList(new Item(new ItemId(1L), "name1")));
        Model base = new Model(new ModelId(1L), "name", new Vo(new VoId(1L), "desc"), Lists.newArrayList(new Item(new ItemId(1L), "name")));

        Javers javers = JaversBuilder.javers()
                //.withListCompareAlgorithm(LEVENSHTEIN_DISTANCE)
                .build();
        org.javers.core.diff.Diff diff = javers.compare(base, working);



        final Changes changes = diff.getChanges();
        //System.out.println(changes);
        final List<ObjectRemoved> objectRemoveds = diff.getChangesByType(ObjectRemoved.class);
        List<ValueChange> valueChangeList = diff.getChangesByType(ValueChange.class);
        List<InitialValueChange> initialValueChangeList = diff.getChangesByType(InitialValueChange.class);
        if (!CollectionUtils.isEmpty(initialValueChangeList)) {
            initialValueChangeList.forEach(v -> {
                Object obj = v.getAffectedObject().get();
                if (obj instanceof Item) {
                    Item item=(Item)obj;
                    System.out.println(item.getId());
                }
            });
        }


        if (!CollectionUtils.isEmpty(valueChangeList)) {
            ValueChange valueChange = valueChangeList.get(0);
            Object obj = valueChangeList.get(0).getAffectedObject().get();
            if (obj instanceof Item) {
                Item item=(Item) obj;
                System.out.println(item.getId());
                System.out.println(item.getItemName());
                System.out.println(valueChange.getLeft());
                System.out.println(valueChange.getRight());
            }
        }


        final List<ListChange> changesByType = diff.getChangesByType(ListChange.class);
        if (!CollectionUtils.isEmpty(changesByType)) {
            final ListChange listChange = changesByType.get(0);
            final List<?> addedValues = listChange.getAddedValues();
            final List<ValueRemoved> valueRemovedChanges = listChange.getValueRemovedChanges();
            final List<?> removedValues = listChange.getRemovedValues();
        }
        final List<PropertyChange> propertyChanges = diff.getPropertyChanges("items");
        final PropertyChange propertyChange = propertyChanges.get(0);
        System.err.println(propertyChange.getChangeType());
        System.err.println(propertyChange.isPropertyRemoved());
        System.err.println(propertyChange.isPropertyAdded());
        System.err.println(propertyChange.isPropertyValueChanged());
        final Optional<Object> affectedObject = propertyChange.getAffectedObject();
        System.err.println(diff);
    }

}
