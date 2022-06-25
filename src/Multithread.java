public class Multithread {
    public static void main(String[] args) {
        /*
        MyThread thread1 = new MyThread("Стирать");
        MyThread thread2 = new MyThread("Мыть посуду");
        MyThread thread3 = new MyThread("Пылесосить");

        Thread thread1 = new Thread(new MyRunnableClass("Стирка"));
        Thread thread2 = new Thread(new MyRunnableClass("Пылесосить"));
        Thread thread3 = new Thread(new MyRunnableClass("Мыть посуду"));
         */
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println("Стирать" + "завершено на " + i + "%");
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println("Мыть посуду" + "завершено на " + i + "%");
                }
            }
        });

        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println("Пылесосить" + "завершено на " + i + "%");
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
            System.out.println(this.taskName + "завершено на " + i + "%");
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
            System.out.println(this.taskName + "завершено на " + i + "%");
        }
    }
}