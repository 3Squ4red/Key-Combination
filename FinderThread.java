public class FinderThread implements Runnable {
    Thread thread;
    private String letterToBeFound, combination;
    private int position;

    FinderThread(String name, String letterToBeFound, String combination, int position) {
        thread = new Thread(this, name);
        this.letterToBeFound = letterToBeFound;
        this.combination = combination;
        this.position = position;
    }

    static FinderThread createAndStart(String name, String letterToBeFound, String combination, int position) {
        FinderThread finderThread = new FinderThread(name, letterToBeFound, combination, position);
        finderThread.thread.start();
        return finderThread;
    }

    @Override
    public void run() {
        //System.out.println("\n" + thread.getName() + " starting-----------------");
        for (int i = 0; i < combination.length(); i++) {
            String charAtI = combination.charAt(i) + "";
            if (charAtI.equals(letterToBeFound)) {
                Finder.indexes[position] = i;
//                System.out.println(letterToBeFound + " was found at " + i + " in the given combination");
//                System.out.println(thread.getName() + " terminating-----------------\n");
                return;
            }
        }
        Finder.indexes[position] = -1;
//        System.out.println(thread.getName() + " couldn't find " + letterToBeFound);
    }
}
