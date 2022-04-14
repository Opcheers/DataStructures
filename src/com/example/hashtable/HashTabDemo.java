package com.example.hashtable;

import com.sun.org.apache.xpath.internal.WhitespaceStrippingElementMatcher;
import com.sun.xml.internal.org.jvnet.mimepull.CleanUpExecutorFactory;

import java.io.PipedReader;
import java.util.Scanner;

public class HashTabDemo {

    public static void main(String[] args) {
        //创建哈希表
        HashTab hashTab = new HashTab(7);

        //写一个简单的菜单
        String key = "/";
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("add:添加雇员");
            System.out.println("list:显示雇员");
            System.out.println("find:查找雇员");
            System.out.println("exit:退出系统");

            key = scanner.next();
            switch (key) {
                case "add":
                    System.out.println("输入id");
                    int id = scanner.nextInt();
                    System.out.println("输入姓名");
                    String name = scanner.next();
                    Employee employee = new Employee(id, name, null);
                    hashTab.add(employee);
                    break;
                case "list":
                    hashTab.list();
                    break;
                case "find":
                    System.out.println("请输入要查找的id");
                    int no = scanner.nextInt();
                    hashTab.search(no);
                    break;
                case "exit":
                    scanner.close();
                    System.exit(0);
            }
        }
    }
}

//哈希表
class HashTab{
    private EmpLinkedList[] empLinkedLists;
    private int size;

    //构造器
    public HashTab(int size){
        //初始化哈希表
        this.size = size;
        empLinkedLists = new EmpLinkedList[size];
        //初始化每一条链表
        for (int i = 0; i < size; i++) {
            empLinkedLists[i] = new EmpLinkedList();
        }
    }

    //添加数据：根据id得到该员工添加到哪条链表
    public void add(Employee employee){
        int empLinkedListNo = hashFun(employee.id);
        empLinkedLists[empLinkedListNo].add(employee);
    }
    
    //遍历哈希表
    public void list(){
        for (int i = 0; i < size; i++) {
            empLinkedLists[i].list(i);
        }
    }

    //查找
    public void search(int id){
        int no = hashFun(id);
        Employee employee = empLinkedLists[no].search(id);
        if (employee != null) {
            System.out.println("找到了！");
        } else {
            System.out.println("没有找到！");
        }
    }

    //散列函数：简单取模
    public int hashFun(int id){
        return id % size;
    }
}



//雇员实体类
class Employee{
    public int id;
    public String name;
    public Employee next;

    public Employee(int id, String name, Employee next) {
        this.id = id;
        this.name = name;
        this.next = next;
    }
}

//链表
class EmpLinkedList{
    //头指针指向第一个
    private Employee head;

    //添加雇员到链表尾部
    public void add(Employee employee){
        //first one
        if (head == null) {
            head = employee;
            return;
        }
        //not first one
        Employee cur = head;
        while (true){
            if (cur.next == null){
                //the last one
                cur.next = employee;
                break;
            }
            cur = cur.next;
        }
    }

    //遍历信息
    public void list(int no){
        if (head == null) {
            System.out.println("第"+no+"条链表为空！");
            return;
        }
        Employee cur = head;
        while (true){
            System.out.printf("id=%d, name=%s \t", cur.id, cur.name);
            if (cur.next == null) {
                break;
            } else {
                cur = cur.next;
            }
        }
        System.out.println();
    }

    //根据id查找雇员，找到返回emplpyee，找不到返回null
    public Employee search(int id){
        if (head == null) {
            return null;
        }
        Employee cur = head;
        while (cur != null){
            if (cur.id == id) {
                System.out.println("id为" + id + "的雇员叫" + cur.name);
                return cur;
            }
            cur = cur.next;
        }
        return null;
    }


}