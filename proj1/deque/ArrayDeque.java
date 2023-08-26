package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] items;
    private int size;
    private int nextFirst = 0;
    private int nextLast = 1;

    public ArrayDeque() {
        items = (T []) new Object[8];
        size = 0;
    }

    public ArrayDeque(int capacity) {
        items = (T []) new Object[capacity];
        size = 0;
    }

    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
            nextFirst = items.length - 1;
            nextLast = size;
        }
        items[nextFirst] = item;
        size++;
        if (nextFirst - 1 >= 0) {
            nextFirst--;
        } else {
            nextFirst = items.length - 1;
        }
    }

    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
            nextFirst = items.length - 1;
            nextLast = size;
        }
        items[nextLast] = item;
        size++;
        if (nextLast + 1 < items.length) {
            nextLast++;
        } else {
            nextLast = 0;
        }
    }

    @Override
    public T get(int pos) {
        if (nextFirst + pos  + 1 < items.length) {
            return items[nextFirst + pos  + 1];
        } else {
            return items[nextFirst + pos  + 1 - items.length];
        }

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        if ((float)size / items.length < 0.25) {
            resize(size / 2);
            nextFirst = items.length - 1;
            nextLast = size;
        }
        if (nextFirst + 1 >= items.length) {
            T first = items[0];
            size--;
            nextFirst = 0;
            return first;
        } else {
            T first = items[nextFirst + 1];
            size--;
            nextFirst++;
            return first;
        }
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        if ((float)size / items.length < 0.25) {
            resize(size / 2);
            nextFirst = items.length - 1;
            nextLast = size;
        }
        if (nextLast - 1 < 0) {
            T last = items[items.length - 1];
            size--;
            nextLast = items.length - 1;
            return last;
        } else {
            T last = items[nextLast - 1];
            size--;
            nextLast--;
            return last;
        }
    }

    @Override
    public void printDeque() {
        int index = nextFirst + 1;
        int printed = 0;
        while (printed < size) {
            if (nextFirst + 1 >= items.length) {
                T first = items[0];
                System.out.println(first);
                printed++;
                nextFirst = 0;
            } else {
                T first = items[nextFirst + 1];
                System.out.println(first);
                printed++;
                nextFirst++;
            }
        }
    }

    private void resize(int capacity) {
        T[] array = (T []) new Object[capacity];
        System.arraycopy(items, nextFirst + 1, array, 0, items.length - nextFirst - 1);
        System.arraycopy(items, 0, array, items.length - nextFirst - 1, nextLast);
        items = array;
    }

    private class ArrayIterator implements Iterator<T> {
        private int pos;
        public ArrayIterator() {
            pos = 0;
        }
        @Override
        public boolean hasNext() {
            return pos < size;
        }

        @Override
        public T next() {
            T returnItem = items[pos];
            pos += 1;
            return returnItem;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        ArrayDeque<T> other = (ArrayDeque<T>) o;
        if (this.size() != other.size()) {
            return false;
        }
        for (int i = 0; i < this.size; i += 1) {
            if (!this.items[i].equals(other.items[i])) {
                return false;
            }
        }
        return true;
    }
}
