package jp.pigumer.monitor;

public class StdoutReporter extends Reporter {

    @Override
    protected void report(int count) {
       System.out.println(count);
    }
}
