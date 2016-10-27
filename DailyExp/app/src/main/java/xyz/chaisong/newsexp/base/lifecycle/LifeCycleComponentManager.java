package xyz.chaisong.newsexp.base.lifecycle;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class LifeCycleComponentManager implements IComponentContainer, LifeCycleComponent {

    private HashMap<String, LifeCycleComponent> mComponentList;

    public LifeCycleComponentManager() {
    }

    /**
     * Try to add component to container
     *
     * @param component
     * @param matrixContainer
     */
    public static void tryAddComponentToContainer(LifeCycleComponent component, Object matrixContainer) {
        tryAddComponentToContainer(component, matrixContainer, true);
    }

    public static boolean tryAddComponentToContainer(LifeCycleComponent component, Object matrixContainer, boolean throwEx) {
        if (matrixContainer instanceof IComponentContainer) {
            ((IComponentContainer) matrixContainer).addComponent(component);
            return true;
        } else {
            if (throwEx) {
                throw new IllegalArgumentException("componentContainerContext should implements IComponentContainer");
            }
            return false;
        }
    }

    @Override
    public void addComponent(LifeCycleComponent component) {
        if (component != null) {
            if (mComponentList == null) {
                mComponentList = new HashMap<String, LifeCycleComponent>();
            }
            mComponentList.put(component.toString(), component);
        }
    }

    @Override
    public void onBecomeInvisible() {
        if (mComponentList == null) {
            return;
        }
        Iterator<Entry<String, LifeCycleComponent>> it = mComponentList.entrySet().iterator();
        while (it.hasNext()) {
            LifeCycleComponent component = it.next().getValue();
            if (component != null) {
                component.onBecomeInvisible();
            }
        }
    }

    @Override
    public void onBecomeVisible() {
        if (mComponentList == null) {
            return;
        }
        Iterator<Entry<String, LifeCycleComponent>> it = mComponentList.entrySet().iterator();
        while (it.hasNext()) {
            LifeCycleComponent component = it.next().getValue();
            if (component != null) {
                component.onBecomeVisible();
            }
        }
    }

    @Override
    public void onDestroy() {
        if (mComponentList == null) {
            return;
        }
        Iterator<Entry<String, LifeCycleComponent>> it = mComponentList.entrySet().iterator();
        while (it.hasNext()) {
            LifeCycleComponent component = it.next().getValue();
            if (component != null) {
                component.onDestroy();
            }
        }
    }
}
