package dataStructure.list;

import java.util.List;

public class SeqList<type> {

    protected Object[] element = null;
    //数组长度
    protected int l = 0;

    public SeqList(int length) {
        this.element = new Object[length];
        this.l = length;
    }

    public SeqList() {
        this(64); //调用上面的构造方法创建一个初始化为64长度的数组
    }

    public SeqList(type[] values) {
//       this.element = new Object[values.length];
        this(values.length);
       if (values.length>0){
           for (int i=0; i<values.length; i++)
               this.element[i] = values[i];
           this.l = values.length;
       }
    }

    /**
     * 判断线性表是否为空
     * @return
     */
    public boolean isEmpty() {
        return this.l > 0;
    }

    /**
     * 返回线性表的长度（元素个数)
     * @return
     */
    public int size() {
        int count = 0;
        if (element == null)
            return 0;
        for (Object o : element) {
            if (o != null)
                count++;
        }
        return count;
    }

    /**
     * 返回指定索引位置的元素
     * @param i
     * @return
     */
    public type get(int i) {
        if (i>0&&i<l)
            return (type) element[i];
        else
            return null;
    }

    /**
     * 设置第i个元素为x
     * @param i
     * @param x
     */
    public void set(int i, type x) {
        if(i>0 && i<l){
            element[i] = x;
            l++;
        }
    }

    /**
     * 在线性表最后插入一个元素，返回插入位置的索引
     * @param x
     * @return
     */
    public int insert(type x) {
        element[element.length-1] = x;
        return element.length-1;
    }

    /**
     * 在指定位置插入一个元素
     * @param i 插入的位置
     * @param x 插入的元素
     * @return
     */
    public int insert(int i, type x) {
        Object temp;
        l ++;
        if(size()<i){
            element[i] = x;
            return i;
        } else {
            for (int j=size(); j>i; j--) {
                element[j] = element[j-1];
            }
            element[i] = x;
            return i;
        }
    }

    /**
     * 删除指定位置的元素，并返回删除的元素
     * @param i
     * @return
     */
    public type remove(int i) {
        Object temp = null;
        if (i<l){
            temp = element[i];
            l--;
            for(; i<element.length-1; i++) {
                element[i] = element[i+1];
            }
        }
        return (type) temp;
    }

    /**
     * 删除线性表所有元素
     */
    public void clear() {
        this.element = null;
        this.l = 0;
    }

    /**
     *查找首次出现的于key相同的元素并返回索引
     * @param key
     * @return
     */
    public int search(type key) {
        if (contains(key)){
            for(int i=0; i<element.length; i++) {
                if(element[i].equals(key))
                    return i;
            }
        }
        return 0;
    }

    /**
     *查找是否包含于key相同的元素
     * @param key
     * @return
     */
    public boolean contains(type key) {
        if (size()<=0)
            return false;
        for (Object o : element) {
            if(o!=null)
                if (o.equals(key))
                    return true;
        }
        return false;
    }

    /**
     * 插入不重复元素
     * @param key
     * @return
     */
    public int insertDifferent(type key) {
        if (!this.contains(key)){
            insert(key);
            l++;
            return element.length-1;
        }
        return -1;
    }

    /**
     * 删除首次出现的于key相同的元素并返回被删除元素
     * @param key 要删除的元素
     * @return 被删除元素
     */
    public type remove(type key) {
        if (contains(key)){
            int index = search(key);
            type temp = (type) element[index];
            for(int i=index; i<element.length-1; i++) {
                element[i] = element[++i];
            }
            l--;
            return temp;
        }
        return null;
    }

    /**
     * 判断两个线性表元素是否都相等
     * @param obj
     * @return
     */
    public boolean equals(Object obj){
        type[] objArr = (type[]) obj;
        if(element.length != objArr.length)
            return false;
        for(int i=0; i<element.length; i++){
            if(!element[i].equals(objArr[i]))
                return false;
        }
        return true;
    }

    /**
     * 添加list中的所有元素
     * @param list
     */
    public void addAll(List<type> list) {
        if (list.size()<=0)
            return;
        for (int i=0; i<list.size(); i++)
            insert(i, list.get(i));
    }

    public String toString() {
        String str = "[";
        for (Object o : element) {
            str += o.toString() + "\t";
        }

        return str + "]";
    }



}
