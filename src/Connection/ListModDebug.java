package Connection;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
//magical class that prevents a ConcurrentModifcationException on the List in registrator because it is iteratored at the same time that objects are added.
public class ListModDebug {
    public static <E> List<E> getProxy(List<E> source) {
        Object proxy = Proxy.newProxyInstance(ListModDebug.class.getClassLoader(), new Class[]{List.class}, new DebugInvocationHandler(source));
        return (List<E>) proxy;
    }

    static class DebugInvocationHandler implements InvocationHandler {
        private boolean isIterated = false;
        private final List list;

        DebugInvocationHandler(List list) {
            this.list = list;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (isIterated) {
                throw new ConcurrentModificationException("DEBUG: " + method);
            }
            switch (method.getName()) {
                case "iterator":
                    isIterated = true;
                    Iterator iterator = (Iterator) method.invoke(list, args);
                    return new Iterator() {
                        @Override
                        public boolean hasNext() {
                            boolean b = iterator.hasNext();
                            if (!b) isIterated = false;
                            return b;
                        }
                        @Override
                        public Object next() {
                            return iterator.next();
                        }
                    };
                default:
                    return method.invoke(list, args);
            }
        }
    }
}
