package no.hials.page.replacement;

import java.util.List;

/**
 * Optimal Replacement algorithm
 * Fill in your code in this class!
 */
public class OptimalReplacement extends ReplacementAlgorithm {
    
    // TODO - add some state variables here, if you need any
    private int currentFrame;
    private int currentPageProcess;


    @Override
    protected void reset() {
        // TODO - do preparation/initialization here, if needed
        currentFrame = 0;
        currentPageProcess = 0;


    }
    
    @Override
    public int process(String referenceString) {
        List<Integer> pageReferences = Tools.stringToArray(referenceString);
        if (pageReferences == null) return 0;
        
        int replacements = 0; // How many page replacements made
        
        // TODO - process the reference string here. You can see FIFOReplacement
        // as an example. But remember, that FIFO uses a different algorithm.
        // This class should use Optimal Replacement algorithm, described
        // in Section 9.4.
        // Get the reference list as an array
        for (int page: pageReferences){
            if (!isLoaded(page) && !checkFrameFull()){
                if(pageIn(currentFrame, page)) {
                    replacements++;
                }
                moveCurrentPointer();
                System.out.println(getFrameStatus());
            } else if (!isLoaded(page)) {
                System.out.println(getFrameStatus() + "\nChecking distance on " + page);
                if(optimalPageIn(page, pageReferences, currentPageProcess)) {
                    replacements++;
                }
            } else {
                System.out.println("Page " + page + " is already loaded.");
            }
            System.out.println("Current replacement = " + replacements);
            currentPageProcess++;
            System.out.println();
        }
        return replacements;
    }

    /**
     * Loads a page into a physical RAM frame
     * Checks the progress/length of each frame
     * @param page represents the page to be loaded
     * @param list Represents the list of pages
     * @param pageProgress Shows how far the pointer has progressed
     * @return Returns either true of false depending if page was replaced or not
     */
    protected boolean optimalPageIn(int page, List<Integer> list, int pageProgress) {

        boolean replaced = false;
        int lengthToOne = 100;
        int lengthToTwo = 100;
        int lengthToThree = 100;
        for(int i = pageProgress; i < list.size(); i++) {
            if(frames[0] == list.get(i) && lengthToOne == 100) {
                lengthToOne = i;
            }
            if(frames[1] == list.get(i) && lengthToTwo == 100) {
                lengthToTwo = i;
            }
            if(frames[2] == list.get(i) && lengthToThree == 100) {
                lengthToThree = i;
            }
        }
        if(lengthToOne > lengthToTwo && lengthToOne > lengthToThree) {
            insertPage(page, 0);
            replaced = true;
        } else if (lengthToTwo > lengthToOne && lengthToTwo > lengthToThree) {
            insertPage(page, 1);
            replaced = true;
        } else if (lengthToThree > lengthToOne && lengthToThree > lengthToTwo) {
            insertPage(page, 2);
            replaced = true;
        }
        return replaced;
    }



    // TODO - create any helper methods here if you need any

    /**
     * Move the current pointer - next page to be replaced
     */
    private void moveCurrentPointer() {
        currentFrame++;
        // If it overflows, go to first frame again
        if (currentFrame == frames.length) {
            currentFrame = 0;
        }
    }


    /**
     * Inserts a page into a frame
     * @param page Represents the page
     * @param pageNumber is the page number in which the page is inserted
     */
    private void insertPage(int page, int pageNumber) {
        frames[pageNumber] = page;
        System.out.println("Replacing frame " + pageNumber + " with " + page);
    }
}
