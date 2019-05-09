package chielong.study.first;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by chielong on 2019-05-08.
 */
public class PrintProcessor extends Thread implements IRequestProcessor {
    LinkedBlockingQueue<Request> requests = new LinkedBlockingQueue<Request>();

    private IRequestProcessor nextProcessor;

    private volatile boolean isFinish = false;

    public PrintProcessor() { }

    public PrintProcessor(IRequestProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    @Override
    public void process(Request request) {
        requests.add(request);
    }

    @Override
    public void run() {
        // 意思到了
        while(!isFinish) {
            try {
                Request request = requests.take();
                // save to mysql
                nextProcessor.process(request);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutDown() {
        isFinish = true;
    }
}
