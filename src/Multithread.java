public class Multithread {
    public static void main(String[] args) {
        /*
        MyThread thread1 = new MyThread("�������");
        MyThread thread2 = new MyThread("���� ������");
        MyThread thread3 = new MyThread("����������");

        Thread thread1 = new Thread(new MyRunnableClass("������"));
        Thread thread2 = new Thread(new MyRunnableClass("����������"));
        Thread thread3 = new Thread(new MyRunnableClass("���� ������"));
         */
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println("�������" + "��������� �� " + i + "%");
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println("���� ������" + "��������� �� " + i + "%");
                }
            }
        });

        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println("����������" + "��������� �� " + i + "%");
                }
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
    }
}

class MyRunnableClass implements Runnable {
    String taskName;

    public MyRunnableClass(String taskName, int port) {
        this.taskName = taskName;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(this.taskName + "��������� �� " + i + "%");
        }
    }
}


class MyThread extends Thread {
    String taskName;

    public MyThread(String taskName) {
        this.taskName = taskName;
    }

    public void run() {
        super.run();
        for (int i = 0; i < 100; i++) {
            System.out.println(this.taskName + "��������� �� " + i + "%");
        }
    }
}