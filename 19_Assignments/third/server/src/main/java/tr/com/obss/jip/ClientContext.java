package tr.com.obss.jip;

public class ClientContext {
    public int randNum;
    public int tries;

    public ClientContext(int randNum, int tries) {
        this.randNum = randNum;
        this.tries = tries;
    }

    public int getRandNum() {
        return randNum;
    }

    public void setRandNum(int randNum) {
        this.randNum = randNum;
    }

    public int getTries() {
        return tries;
    }

    public void setTries(int tries) {
        this.tries = tries;
    }
}
